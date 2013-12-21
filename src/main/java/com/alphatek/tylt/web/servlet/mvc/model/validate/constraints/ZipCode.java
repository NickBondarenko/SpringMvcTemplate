package com.alphatek.tylt.web.servlet.mvc.model.validate.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.alphatek.tylt.web.servlet.mvc.model.validate.validators.ZipCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jason.dimeo
 *         Date: 5/2/13
 *         Time: 11:20 AM
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ZipCodeValidator.class})
@ReportAsSingleViolation
public @interface ZipCode {
	String message() default "{com.alphatek.tylt.web.servlet.mvc.model.validate.constraints.ZipCode.message}";

	Class[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({TYPE, ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		ZipCode[] value();
	}
}