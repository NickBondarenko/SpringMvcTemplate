package com.alphatek.tylt.support.util;

import com.google.common.collect.Maps;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;


/**
 * @author jason.dimeo
 *         Date: 5/1/13
 *         Time: 11:51 AM
 */
public class ValidationUtils {
	private ValidationUtils() {}

	public static Errors validate(Validator validator, Object object) {
		Errors errors = new MapBindingResult(Maps.newHashMap(), object.getClass().getName());
		validator.validate(object, errors);
		return errors;
	}
}