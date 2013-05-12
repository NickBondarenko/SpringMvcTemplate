package com.alphatek.tylt.web.support;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionAttribute {
	/**
   * The name of the Session attribute to bind to.
   */
  String value() default "";

  /**
   * Whether the attribute is required.
   * Default is true, leading to an exception thrown in case
   * of the attribute missing in the session.
   * Switch this to false if you prefer a
   * null in case of the parameter missing.
   * Alternatively, provide a {@link #defaultValue() defaultValue},
   * which implicitely sets this flag to false.
   */
  boolean required() default true;

  /**
   * The default value to use as a fallback. Supplying a default value
   * implicitely sets {@link #required()} to false.
   */
  String defaultValue() default "";
}