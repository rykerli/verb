package com.red.verb.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

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
public class JWTUtil {
	// 过期时间设置为24h
	private static final long EXPRIE_TIME = 24 * 60 * 60 * 1000;

	public static boolean verify(String token, String userName, String secret) {
		try {
			Algorithm algorithm = Algorithm.HMAC512(secret);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("userName", userName).build();
			verifier.verify(token);
			return true;
		} catch (Exception e) {
			log.error("鉴权失败");
			return false;
		}
	}

	/**
	 *  
	 * 获取token中的信息无需secret解密也能获得
	 *
	 * @param token token
	 * @return null or userName
	 */
	public static String getUserName(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("userName").toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static String sign(String userName, String secret) throws UnsupportedEncodingException {
		Date date = new Date(System.currentTimeMillis() + EXPRIE_TIME);
		Algorithm algorithm = Algorithm.HMAC512(secret);
		// 附带userName信息
		return JWT.create().withClaim("userName", userName).withExpiresAt(date).sign(algorithm);
	}
}
