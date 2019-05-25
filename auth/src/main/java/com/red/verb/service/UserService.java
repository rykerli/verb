package com.red.verb.service;

import com.red.verb.commom.base.BaseService;
import com.red.verb.model.User;

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
public interface UserService extends BaseService<User> {
	User getUser(String username);

	List<User> listUser();
}
