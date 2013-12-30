package com.alphatek.tylt.service;

import com.alphatek.tylt.authority.UserContext;
import com.alphatek.tylt.repository.UserManagerDao;
import com.alphatek.tylt.web.servlet.mvc.model.Address;
import com.alphatek.tylt.web.servlet.mvc.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Default Registration Service
 * @author jason.dimeo
 * Date: 2013-03-31
 * Time: 5:56 PM
 */
@Service
public final class DefaultRegistrationService implements RegistrationService {
	@Resource private UserManagerDao userManagerDao;
	@Resource private AddressService addressService;
	@Resource private UserContext userContext;
	@Resource private PasswordEncoder passwordEncoder;

	@Transactional(readOnly = false)
	@Override	public User registerUser(User.Builder userBuilder) {
		if (userBuilder.getAddress().getCountry() == null) {
			userBuilder.getAddress().setCountry(addressService.getCountryById(1L));
		}

		Address address = addressService.addAddress(userBuilder.getAddress());

		userBuilder.address(address).password(passwordEncoder.encode(userBuilder.getPassword()));

		User newUser = userManagerDao.createUser(userBuilder);
		newUser.eraseCredentials();

		userManagerDao.addUserToGroup(newUser);
		userContext.setCurrentUser(newUser);

		return newUser;
	}

	@Override public boolean userExists(String username) {
		return userManagerDao.userExists(username);
	}

	@Override	public boolean deleteUser() {
		return false;
	}
}