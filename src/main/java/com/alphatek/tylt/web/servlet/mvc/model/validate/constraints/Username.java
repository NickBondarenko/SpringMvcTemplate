package com.alphatek.tylt.web.servlet.mvc.model.validate.constraints;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.alphatek.tylt.web.servlet.mvc.model.validate.validators.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jason.dimeo
 * Created on 2013-12-29:2:22 PM.
 */
@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UsernameValidator.class})
@ReportAsSingleViolation
public @interface Username {
	String message() default "{com.alphatek.tylt.web.servlet.mvc.model.validate.constraints.Username.message}";

	Class[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({TYPE, ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		Username[] value();
	}
}