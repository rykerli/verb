package com.red.verb.auth.service;

import com.red.verb.auth.model.User;

import java.util.List;

/**
 * userService
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/25     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-25 13:53
 * @since 1.0.0
 */
public interface UserService {
	User getUser(String username);

	List<User> listUser();
}
