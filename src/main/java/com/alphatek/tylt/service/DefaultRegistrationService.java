package com.alphatek.tylt.service;

import com.alphatek.tylt.authority.UserContext;
import com.alphatek.tylt.repository.AddressDao;
import com.alphatek.tylt.repository.CountryDao;
import com.alphatek.tylt.repository.UserDetailsManagerDao;
import com.alphatek.tylt.web.mvc.model.Address;
import com.alphatek.tylt.web.mvc.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Default Registration Service
 * Date: 3/31/13
 * Time: 5:56 PM
 * @author jason.dimeo
 */
@Service
public class DefaultRegistrationService implements RegistrationService {
	@Resource private UserDetailsManagerDao userDetailsManager;
	@Resource private AddressDao addressDao;
	@Resource private CountryDao countryDao;
	@Resource private UserContext userContext;
	@Resource private PasswordEncoder passwordEncoder;

	@Override	public User registerUser(User user) {
		if (user.getAddress().getCountry() == null) {
			user.getAddress().setCountry(countryDao.retrieveById(1L));
		}

		long addressId = addAddress(user.getAddress());

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setConfirmPassword("");
		user.setEnabled(true);
		user.setAccountNonLocked(true);
		user.setAccountNonExpired(true);
		user.setCredentialsNonExpired(true);
		user.getAddress().setId(addressId);

		long userId = userDetailsManager.createUser(user);
		user.setId(userId);
		userDetailsManager.addUserToGroup(user, 1L);

		userContext.setCurrentUser(user);

		return user;
	}

	private long addAddress(Address address) {
		return addressDao.insertAddress(address);
	}

	@Override	public boolean deleteUser() {
		return false;
	}
}