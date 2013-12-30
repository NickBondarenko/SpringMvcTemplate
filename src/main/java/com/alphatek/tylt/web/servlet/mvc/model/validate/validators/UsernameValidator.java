package com.alphatek.tylt.web.servlet.mvc.model.validate.validators;

import com.alphatek.tylt.repository.UserManagerDao;
import com.alphatek.tylt.web.servlet.mvc.model.validate.constraints.Username;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author jason.dimeo
 * Created on 2013-12-29:2:21 PM.
 */
@Component
public final class UsernameValidator implements ConstraintValidator<Username, String> {
	@Resource private UserManagerDao userManagerDao;

	@Override public void initialize(Username constraintAnnotation) {}

	@Override public boolean isValid(String username, ConstraintValidatorContext context) {
		return !userManagerDao.userExists(username);
	}
}