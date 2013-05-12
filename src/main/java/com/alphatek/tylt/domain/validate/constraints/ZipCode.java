package com.alphatek.tylt.domain.validate.constraints;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.*;

/**
 * @author jason.dimeo
 *         Date: 5/2/13
 *         Time: 11:20 AM
 */
@NotBlank
@Size(min = 5, max = 10)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.LOCAL_VARIABLE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Pattern(regexp="\\d{5}(-\\d{4})?")
public @interface ZipCode {
	String message() default "{com.alphatek.tylt.domain.validate.constraints.ZipCode.message}";

	Class[] groups() default {};

	Class[] payload() default {};
}