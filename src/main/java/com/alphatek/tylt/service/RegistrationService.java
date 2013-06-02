package com.alphatek.tylt.service;

import com.alphatek.tylt.web.mvc.model.User;

public interface RegistrationService {
	User registerUser(User user);
	boolean deleteUser();
}
