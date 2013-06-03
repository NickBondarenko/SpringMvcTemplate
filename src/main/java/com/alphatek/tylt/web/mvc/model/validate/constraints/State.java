package com.alphatek.tylt.web.mvc.model.validate.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.alphatek.tylt.web.mvc.model.validate.validators.StateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: jason.dimeo
 * Date: 2013-06-03 : 1:51 PM
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {StateValidator.class})
@ReportAsSingleViolation
public @interface State {
	String message() default "{com.alphatek.tylt.web.mvc.model.validate.constraints.State.message}";

	Class[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({TYPE, ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		State[] value();
	}
}