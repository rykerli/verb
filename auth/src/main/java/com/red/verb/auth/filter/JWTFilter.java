package com.red.verb.auth.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.red.verb.auth.utils.JWTUtil;
import com.red.verb.cache.utils.JedisUtil;
import com.red.verb.commom.Constant;
import com.red.verb.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * JWTFilter
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/25     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-25 13:06
 * @since 1.0.0
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {
	/**
	 * 判断用户是否要登录
	 *
	 * @param request  请求
	 * @param response 响应
	 * @return 是否登录
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		String authorization = req.getHeader("Authorization");
		log.info("判断用户是否要登录：{}", authorization);
		return authorization != null;
	}

	/**
	 * @param request  请求
	 * @param response 响应
	 * @return 是否登录
	 * @throws Exception 异常
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authorization = httpServletRequest.getHeader("Authorization");
		log.info("执行登录：{}", authorization);
		JWTToken token = new JWTToken(authorization);
		// 提交到realm登录
		getSubject(request, response).login(token);
		// 如果没有抛出异常则代表登录成功
		return true;
	}

	/**
	 * 此处为AccessToken刷新，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
	 */
	private boolean refreshToken(ServletRequest request, ServletResponse response) {
		// 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
		String token = this.getAuthzHeader(request);
		// 获取当前Token的帐号信息
		String account = JWTUtil.getClaim(token, Constant.ACCOUNT);
		// 判断Redis中RefreshToken是否存在
		if (JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
			// Redis中RefreshToken还存在，获取RefreshToken的时间戳
			String currentTimeMillisRedis = JedisUtil.getObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
			// 获取当前AccessToken中的时间戳，与RefreshToken的时间戳对比，如果当前时间戳一致，进行AccessToken刷新
			if (JWTUtil.getClaim(token, Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
				// 获取当前最新时间戳
				String currentTimeMillis = String.valueOf(System.currentTimeMillis());
				// 读取配置文件，获取refreshTokenExpireTime属性
				String refreshTokenExpireTime = PropertiesUtil.getProperty("refreshTokenExpireTime");
				// 设置RefreshToken中的时间戳为当前最新时间戳，且刷新过期时间重新为30分钟过期(配置文件可配置refreshTokenExpireTime属性)
				JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account, currentTimeMillis,
						Integer.parseInt(Objects.requireNonNull(refreshTokenExpireTime)));
				// 刷新AccessToken，设置时间戳为当前最新时间戳
				token = JWTUtil.sign(account, currentTimeMillis);
				// 将新刷新的AccessToken再次进行Shiro的登录
				JWTToken jwtToken = new JWTToken(token);
				// 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获，如果没有抛出异常则代表登入成功，返回true
				this.getSubject(request, response).login(jwtToken);
				// 最后将刷新的AccessToken存放在Response的Header中的Authorization字段返回
				HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
				httpServletResponse.setHeader("Authorization", token);
				httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
				return true;
			}
		}
		return false;
	}

	/**
	 * 这里我们详细说明下为什么最终返回的都是true，即允许访问
	 * 例如我们提供一个地址 GET /article
	 * 登入用户和游客看到的内容是不同的
	 * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
	 * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
	 * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
	 * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
	 *
	 * @param request     请求
	 * @param response    响应
	 * @param mappedValue 值
	 * @return true
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginAttempt(request, response)) {
			try {
				executeLogin(request, response);
			} catch (Exception e) {
				// 认证出现异常，传递错误信息msg
				String msg = e.getMessage();
				// 获取应用异常(该Cause是导致抛出此throwable(异常)的throwable(异常))
				Throwable throwable = e.getCause();
				if (throwable instanceof SignatureVerificationException) {
					// 该异常为JWT的AccessToken认证失败(Token或者密钥不正确)
					msg = "Token或者密钥不正确(" + throwable.getMessage() + ")";
				} else if (throwable instanceof TokenExpiredException) {
					// 该异常为JWT的AccessToken已过期，判断RefreshToken未过期就进行AccessToken刷新
					if (this.refreshToken(request, response)) {
						return true;
					} else {
						msg = "Token已过期(" + throwable.getMessage() + ")";
					}
				} else {
					// 应用异常不为空
					if (throwable != null) {
						// 获取应用异常msg
						msg = throwable.getMessage();
					}
				}
				this.response401(request, response);
				return false;
			}
		} else {
			// 没有携带Token
			HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
			// 获取当前请求类型
			String httpMethod = httpServletRequest.getMethod();
			// 获取当前请求URI
			String requestURI = httpServletRequest.getRequestURI();
			log.info("当前请求 {} Authorization属性(Token)为空 请求类型 {}", requestURI, httpMethod);
			// mustLoginFlag = true 开启任何请求必须登录才可访问
			Boolean mustLoginFlag = false;
			if (mustLoginFlag) {
				this.response401(request, response);
				return false;
			}
		}
		return true;
	}

	/**
	 * 这里我们详细说明下为什么重写
	 * 可以对比父类方法，只是将executeLogin方法调用去除了
	 * 如果没有去除将会循环调用doGetAuthenticationInfo方法
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		this.sendChallenge(request, response);
		return false;
	}


	/**
	 * 跨域支持
	 *
	 * @param request  请求
	 * @param response 响应
	 * @return true or false
	 * @throws Exception 异常
	 */
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				httpServletRequest.getHeader("Access-Control-Request-Headers"));
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}

	/**
	 * 将非法请求跳转到 /401
	 */
	private void response401(ServletRequest req, ServletResponse resp) {
		try {
			HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
			httpServletResponse.sendRedirect("/401");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
