package com.red.verb.auth.shiro;

import com.red.verb.auth.filter.JWTToken;
import com.red.verb.auth.service.UserService;
import com.red.verb.auth.utils.JWTUtil;
import com.red.verb.cache.utils.JedisUtil;
import com.red.verb.commom.Constant;
import com.red.verb.utils.StringUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

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
@Service
public class MyRealm extends AuthorizingRealm {
	private final UserService userService;

	public MyRealm(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JWTToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)
			throws AuthenticationException {
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		String account = JWTUtil.getClaim(principalCollection.toString(), Constant.ACCOUNT);
//		UserDto userDto = new UserDto();
//		userDto.setAccount(account);
//		// 查询用户角色
//		List<RoleDto> roleDtos = roleDao.findRoleByUser(userDto);
//		for (RoleDto roleDto : roleDtos) {
//			if (roleDto != null) {
//				// 添加角色
//				simpleAuthorizationInfo.addRole(roleDto.getName());
//				// 根据用户角色查询权限
//				List<PermissionDto> permissionDtos = permissionDao.findPermissionByRole(roleDto);
//				for (PermissionDto permissionDto : permissionDtos) {
//					if (permissionDto != null) {
//						// 添加权限
//						simpleAuthorizationInfo.addStringPermission(permissionDto.getPerCode());
//					}
//				}
//			}
//		}
		simpleAuthorizationInfo.addStringPermission(account);
		return simpleAuthorizationInfo;
	}

	/**
	 * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		String token = (String) authenticationToken.getCredentials();
		// 解密获得account，用于和数据库进行对比
		String account = JWTUtil.getClaim(token, Constant.ACCOUNT);
		// 帐号为空
		if (StringUtil.isBlank(account)) {
			throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
		}
//		// 查询用户是否存在
//		UserDto userDto = new UserDto();
//		userDto.setAccount(account);
//		userDto = userDao.selectOne(userDto);
//		if (userDto == null) {
//			throw new AuthenticationException("该帐号不存在(The account does not exist.)");
//		}
		// 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
		if (JWTUtil.verify(token) && JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
			// 获取RefreshToken的时间戳
			String currentTimeMillisRedis = JedisUtil.getObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account)
					.toString();
			// 获取AccessToken时间戳，与RefreshToken的时间戳对比
			if (JWTUtil.getClaim(token, Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
				return new SimpleAuthenticationInfo(token, token, "userRealm");
			}
		}
		throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
	}
}
