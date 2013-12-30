package com.alphatek.tylt.service;

import com.alphatek.tylt.web.servlet.mvc.model.User;

public interface RegistrationService {
	User registerUser(User.Builder userBuilder);
	boolean userExists(String username);
	boolean deleteUser();
}
