package com.red.verb.auth.shiro;

import com.red.verb.auth.filter.JWTToken;
import com.red.verb.auth.utils.JWTUtil;
import com.red.verb.model.User;
import com.red.verb.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * my realm
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/25     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-25 13:31
 * @since 1.0.0
 */
public class MyRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JWTToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
			throws AuthenticationException {
		String username = JWTUtil.getUserName(principals.toString());
		User user = userService.getUser(username);
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRole(user.getRole());
		Set<String> permission = new HashSet<String>(Arrays.asList(user.getPerms().split(",")));
		simpleAuthorizationInfo.addStringPermissions(permission);

		return simpleAuthorizationInfo;
	}

	/**
	 * 默认使用此方法进行用户正确与否验证，错误抛出异常即可
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String token = (String) authenticationToken.getCredentials();
		// 解密获得username，用于和数据库进行对比
		String username = JWTUtil.getUserName(token);
		if (username == null) {
			throw new AuthenticationException("token 无效！");
		}

		User user = userService.getUser(username);
		if (user == null) {
			throw new AuthenticationException("用户"+username+"不存在") ;
		}

		if (!JWTUtil.verify(token, username, user.getPassWord())) {
			throw new AuthenticationException("账户密码错误!");
		}
		return new SimpleAuthenticationInfo(token, token, "my_realm");
	}
}
