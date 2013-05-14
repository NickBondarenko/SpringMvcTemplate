package com.alphatek.tylt.domain.validate.constraints;

import com.alphatek.tylt.domain.validate.validators.StaticDigitsValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @author jason.dimeo
 *         Date: 5/2/13
 *         Time: 3:32 PM
 */
@Documented
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.LOCAL_VARIABLE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StaticDigitsValidator.class)
public @interface StaticDigits {
	int integer();

	int fraction();

	String message() default "{com.alphatek.tylt.domain.validate.constraints.StaticDigits.message}";

	Class[] groups() default {};

	Class[] payload() default {};
}