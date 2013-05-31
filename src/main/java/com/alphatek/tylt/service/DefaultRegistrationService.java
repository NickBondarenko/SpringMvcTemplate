package com.alphatek.tylt.service;

import com.alphatek.tylt.authority.UserContext;
import com.alphatek.tylt.repository.AddressDao;
import com.alphatek.tylt.repository.UserDetailsManagerDao;
import com.alphatek.tylt.web.mvc.model.Address;
import com.alphatek.tylt.web.mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Default Registration Service
 * Date: 3/31/13
 * Time: 5:56 PM
 * @author jason.dimeo
 */
@Service
public class DefaultRegistrationService implements RegistrationService {
	private final UserDetailsManagerDao userDetailsManagerDao;
	private final AddressDao addressDao;
	private final UserContext userContext;
	private final PasswordEncoder passwordEncoder;

	@Autowired public DefaultRegistrationService(UserDetailsManagerDao userDetailsManagerDao, AddressDao addressDao, UserContext userContext, PasswordEncoder passwordEncoder) {
		this.userDetailsManagerDao = userDetailsManagerDao;
		this.addressDao = addressDao;
		this.userContext = userContext;
		this.passwordEncoder = passwordEncoder;
	}

	@Override	public User registerUser(User.Builder userBuilder) {
		int addressId = addAddress(userBuilder.getAddress().build());

		userBuilder.password(passwordEncoder.encode(userBuilder.getPassword()))
			.enabled(true)
			.accountNonLocked(true)
			.accountNonExpired(true)
			.credentialsNonExpired(true)
			.getAddress().id(addressId);

		long userId = userDetailsManagerDao.createUser(userBuilder.build());
		User user = userBuilder.id(userId).build();
		userDetailsManagerDao.addUserToGroup(user, 1L);

		userContext.setCurrentUser(user);

		return user;
	}

	private int addAddress(Address address) {
		return addressDao.insertAddress(address);
	}

	@Override	public boolean deleteUser() {
		return false;
	}
}