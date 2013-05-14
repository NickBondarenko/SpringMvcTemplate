package com.alphatek.tylt.web.mvc.model.validate.validators;

import com.alphatek.tylt.web.mvc.model.validate.constraints.State;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author jason.dimeo
 *         Date: 5/3/13
 *         Time: 3:59 PM
 */
public class StateValidator implements ConstraintValidator<State, String> {
	@Override public void initialize(State state) {}

	@Override public boolean isValid(String stateAbbreviation, ConstraintValidatorContext constraintValidatorContext) {
		return com.alphatek.tylt.web.mvc.model.State.findByCode(stateAbbreviation) != null;
	}
}