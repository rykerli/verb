package com.red.verb.auth.service.impl;

import com.red.verb.auth.repository.UserRepository;
import com.red.verb.auth.service.UserService;
import com.red.verb.auth.model.User;
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
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User getUser(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	public List<User> listUser() {
		return null;
	}
}
