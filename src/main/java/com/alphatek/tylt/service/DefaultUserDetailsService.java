package com.alphatek.tylt.service;

import com.alphatek.tylt.repository.UserDao;
import com.alphatek.tylt.web.mvc.model.User;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author jason.dimeo
 *         Date: 4/27/13
 *         Time: 5:49 PM
 */
@Service
public class DefaultUserDetailsService implements UserDetailsService {
	private final UserDao userDao;

	@Autowired public DefaultUserDetailsService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Preconditions.checkNotNull(username, "Username cannot be null");
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username/password");
		}

		return user;
	}
}