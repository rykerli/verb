package com.red.verb.service.impl;

import com.red.verb.auth.dao.UserDao;
import com.red.verb.commom.base.BaseServiceImpl;
import com.red.verb.model.User;
import com.red.verb.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserServiceImpl
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/25     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-25 15:05
 * @since 1.0.0
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {
	@Override
	public User getUser(String username) {
		return null;
	}

	@Override
	public List<User> listUser() {
		return null;
	}
}
