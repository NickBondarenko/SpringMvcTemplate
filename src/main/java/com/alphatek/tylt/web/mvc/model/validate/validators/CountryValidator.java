package com.alphatek.tylt.web.mvc.model.validate.validators;

import com.alphatek.tylt.repository.CountryDao;
import com.alphatek.tylt.web.mvc.model.Country;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * User: jason.dimeo
 * Date: 2013-06-04 : 2:20 PM
 */
@Component
public class CountryValidator implements ConstraintValidator<com.alphatek.tylt.web.mvc.model.validate.constraints.Country, Country> {
	@Resource private CountryDao countryDao;

	@Override public void initialize(com.alphatek.tylt.web.mvc.model.validate.constraints.Country constraintAnnotation) {}

	@Override public boolean isValid(Country country, ConstraintValidatorContext context) {
		return countryDao.isValidCountryCode(country.getCode());
	}
}
