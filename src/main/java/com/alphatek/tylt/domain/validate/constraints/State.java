package com.alphatek.tylt.domain.validate.constraints;

import com.alphatek.tylt.domain.validate.constraints.validators.StateValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @author jason.dimeo
 *         Date: 5/3/13
 *         Time: 3:57 PM
 */
@Documented
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.LOCAL_VARIABLE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StateValidator.class})
public @interface State {
	String message() default "{com.alphatek.tylt.domain.validate.constraints.State.message}";

	Class[] groups() default {};

	Class[] payload() default {};
}
