package com.alphatek.tylt.web.servlet.mvc.model.validate.validators;

import com.alphatek.tylt.repository.StateDao;
import com.alphatek.tylt.web.servlet.mvc.model.State;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * User: jason.dimeo
 * Date: 2013-06-03 : 1:55 PM
 */
@Component
public final class StateValidator implements ConstraintValidator<com.alphatek.tylt.web.servlet.mvc.model.validate.constraints.State, State> {
	@Resource private StateDao stateDao;

	@Override public void initialize(com.alphatek.tylt.web.servlet.mvc.model.validate.constraints.State constraintAnnotation) {}

	@Override public boolean isValid(State value, ConstraintValidatorContext context) {
		return stateDao.isValidStateAbbreviation(value.getAbbreviation());
	}
}