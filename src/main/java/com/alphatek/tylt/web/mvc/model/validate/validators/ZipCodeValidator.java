package com.alphatek.tylt.web.mvc.model.validate.validators;

import com.alphatek.tylt.web.mvc.model.validate.constraints.ZipCode;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author jason.dimeo
 *         Date: 6/1/13
 *         Time: 4:25 PM
 */
public final class ZipCodeValidator implements ConstraintValidator<ZipCode, Object> {
	private static final String REG_EXP = "\\d{5}(-\\d{4})?";

	@Override public void initialize(ZipCode constraintAnnotation) {}

	@Override public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		String formattedZipCode = (String) beanWrapper.getPropertyValue("formattedZipCode");
		return formattedZipCode.matches(REG_EXP);
	}
}