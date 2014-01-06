package com.alphatek.tylt.service;

import com.alphatek.tylt.web.servlet.mvc.model.User;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated public interface RegistrationService {
	User registerUser(User.Builder userBuilder);
	boolean userExists(@NotBlank(message = "{username[NotEmpty.message]}") String username);
	boolean deleteUser();
}