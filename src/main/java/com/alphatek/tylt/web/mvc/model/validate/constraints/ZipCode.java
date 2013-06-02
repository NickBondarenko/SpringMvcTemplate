package com.alphatek.tylt.web.mvc.model.validate.constraints;

import com.alphatek.tylt.web.mvc.model.validate.validators.ZipCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

/**
 * @author jason.dimeo
 *         Date: 5/2/13
 *         Time: 11:20 AM
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ZipCodeValidator.class})
@ReportAsSingleViolation
public @interface ZipCode {
	String message() default "{com.alphatek.tylt.web.mvc.model.validate.constraints.ZipCode.message}";

	Class[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}