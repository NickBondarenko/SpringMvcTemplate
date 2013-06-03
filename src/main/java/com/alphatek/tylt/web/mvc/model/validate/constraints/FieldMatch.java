package com.alphatek.tylt.web.mvc.model.validate.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.alphatek.tylt.web.mvc.model.validate.validators.FieldMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Class-level constraint denoting that a field value should be confirmed by matching the value of another field.
 * @author Keith Donald
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy=FieldMatchValidator.class)
@Documented
public @interface FieldMatch {

	String message() default "{com.alphatek.tylt.web.mvc.model.validate.constraints.FieldMatch.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * The name of the field to be confirmed. e.g. password
	 */
	String field();

	/**
	 * The name of the field whose value must match the value of {@link #field()} to satisfy this constraint.
	 * Optional. If not specified, defaults to "confirm${field}" e.g. confirmPassword.
	 */
	String matches() default "";

	/**
	 * Used to specify multiple confirm fields per class.
	 */
	@Target({TYPE, ANNOTATION_TYPE})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		FieldMatch[] value();
	}
}