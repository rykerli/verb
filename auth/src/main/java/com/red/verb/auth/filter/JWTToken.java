package com.red.verb.auth.filter;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JWTToken
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/25     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-25 13:18
 * @since 1.0.0
 */
public class JWTToken implements AuthenticationToken {
	private static final long serialVersionUID = 1L;
	private String token;

	public JWTToken(String token) {
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}
}
