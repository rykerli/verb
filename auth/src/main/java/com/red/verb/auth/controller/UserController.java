package com.red.verb.auth.controller;

import com.red.verb.auth.utils.JWTUtil;
import com.red.verb.commom.ServerResponse;
import com.red.verb.commom.base.BaseController;
import com.red.verb.model.User;
import com.red.verb.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * UserController
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/25     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-25 14:51
 * @since 1.0.0
 */
@RestController
public class UserController extends BaseController<UserService, User> {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	@ApiOperation(value = "登录功能", notes = "用户密码登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
	})
	public ServerResponse login(@RequestParam("username") String username,
								@RequestParam("password") String password,
								HttpServletResponse response) throws UnsupportedEncodingException {
		User user = userService.getUser(username);
		if (user.getPassWord().equals(password)) {
			String token = JWTUtil.sign(username, password);
			response.setHeader("token", token);
			return ServerResponse.createBySuccess("登录成功", token);
		} else {
			throw new UnauthorizedException();
		}
	}

	@GetMapping("/listUser")
	@ApiOperation(value = "获取用户列表", notes = "获取用户列表")
	public ServerResponse listUser() throws UnsupportedEncodingException {
		List<User> listUser = userService.listUser();
		return ServerResponse.createBySuccess("用户列表", listUser);
	}

	@RequestMapping("/401")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ServerResponse unauthorized() {
		return ServerResponse.createByErrorCodeMsg(401, "未授权");
	}
}
