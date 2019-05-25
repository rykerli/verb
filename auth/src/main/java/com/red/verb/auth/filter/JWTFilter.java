package com.red.verb.auth.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
				response401(request, response);
			}
		}
		return true;
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
