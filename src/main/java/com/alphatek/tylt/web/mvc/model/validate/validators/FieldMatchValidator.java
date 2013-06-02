package com.alphatek.tylt.web.mvc.model.validate.validators;

import com.alphatek.tylt.web.mvc.model.validate.constraints.FieldMatch;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates @FieldMatch fields.
 * @author Keith Donald
 */
public final class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	private String field;
	private String matches;
	private String message;

	public void initialize(FieldMatch constraintAnnotation) {
		field = constraintAnnotation.field();
		matches = constraintAnnotation.matches();
		if (matches == null || matches.length() == 0) {
			matches = "confirm" + StringUtils.capitalize(field);
		}
		message = constraintAnnotation.message();
	}

	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		Object fieldValue = beanWrapper.getPropertyValue(field);
		Object matchesValue = beanWrapper.getPropertyValue(matches);
		boolean matched = ObjectUtils.nullSafeEquals(fieldValue, matchesValue);
		if (matched) {
			return true;
		} else {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addNode(field).addConstraintViolation();
			return false;
		}
	}

}