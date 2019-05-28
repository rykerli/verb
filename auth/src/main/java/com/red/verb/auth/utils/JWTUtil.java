package com.red.verb.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.red.verb.commom.Constant;
import com.red.verb.exception.MyException;
import com.red.verb.utils.Base64ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * jwt util
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/25     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-25 13:34
 * @since 1.0.0
 */
@Slf4j
@Component
public class JWTUtil {
	/**
	 * 过期时间改为从配置文件获取
	 */
	private static String accessTokenExpireTime;
	/**
	 * JWT认证加密私钥(Base64加密)
	 */
	private static String encryptJWTKey;

	@Value("${accessTokenExpireTime}")
	public void setAccessTokenExpireTime(String accessTokenExpireTime) {
		JWTUtil.accessTokenExpireTime = accessTokenExpireTime;
	}

	@Value("${encryptJWTKey}")
	public void setEncryptJWTKey(String encryptJWTKey) {
		JWTUtil.encryptJWTKey = encryptJWTKey;
	}

	/**
	 * 校验token是否正确
	 *
	 * @param token token
	 * @return
	 */
	public static boolean verify(String token) {
		try {
			// 帐号加JWT私钥解密
			String secret = getClaim(token, Constant.ACCOUNT) + Base64ConvertUtil.decode(encryptJWTKey);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm)
					.build();
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (UnsupportedEncodingException e) {
			log.error("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
			throw new MyException("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
		}
	}

	/**
	 * 获得Token中的信息无需secret解密也能获得
	 *
	 * @param token token
	 * @param claim claim
	 * @return java.lang.String
	 * @author red
	 * @date 2018/9/7 16:54
	 */
	public static String getClaim(String token, String claim) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			// 只能输出String类型，如果是其他类型返回null
			return jwt.getClaim(claim).asString();
		} catch (JWTDecodeException e) {
			log.error("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
			throw new MyException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
		}
	}

	/**
	 * 生成签名
	 *
	 * @param account 帐号
	 * @return java.lang.String 返回加密的Token
	 * @author red
	 * @date 2018/8/31 9:07
	 */
	public static String sign(String account, String currentTimeMillis) {
		try {
			// 帐号加JWT私钥加密
			String secret = account + Base64ConvertUtil.decode(encryptJWTKey);
			// 此处过期时间是以毫秒为单位，所以乘以1000
			Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			// 附带account帐号信息
			return JWT.create()
					.withClaim("account", account)
					.withClaim("currentTimeMillis", currentTimeMillis)
					.withExpiresAt(date)
					.sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			log.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
			throw new MyException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
		}
	}

}
