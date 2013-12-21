package com.alphatek.tylt.domain.validate.validators;

import com.alphatek.tylt.domain.validate.constraints.StaticDigits;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * @author jason.dimeo
 *         Date: 5/2/13
 *         Time: 3:37 PM
 */
public class StaticDigitsValidator implements ConstraintValidator<StaticDigits, Number> {
	private int maxIntegerLength;
	private int maxFractionLength;

	@Override public void initialize(StaticDigits staticDigits) {
		this.maxIntegerLength = staticDigits.integer();
		this.maxFractionLength = staticDigits.fraction();
	}

	@Override public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
		//null values are valid
		if (number == null) {
			return true;
		}

		BigDecimal bigNum = number instanceof BigDecimal ? (BigDecimal) number : new BigDecimal(number.toString()).stripTrailingZeros();

		int integerPartLength = bigNum.precision() - bigNum.scale();
		int fractionPartLength = bigNum.scale() < 0 ? 0 : bigNum.scale();

		return (maxIntegerLength == integerPartLength && maxFractionLength == fractionPartLength);
	}
}
