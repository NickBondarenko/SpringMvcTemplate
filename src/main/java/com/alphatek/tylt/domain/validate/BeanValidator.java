package com.alphatek.tylt.domain.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author jason.dimeo
 *         Date: 5/1/13
 *         Time: 9:50 AM
 */
@Component
public class BeanValidator implements org.springframework.validation.Validator {
	private final Validator validator;

	@Autowired public BeanValidator(ValidatorFactory validatorFactory) {
		validator = validatorFactory.usingContext().getValidator();
	}

	@Override public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override public void validate(Object object, Errors errors) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
			String propertyPath = constraintViolation.getPropertyPath().toString();
			errors.rejectValue(propertyPath, "", constraintViolation.getMessage());
		}
	}
}