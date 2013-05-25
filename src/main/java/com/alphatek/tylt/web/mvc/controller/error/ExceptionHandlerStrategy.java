package com.alphatek.tylt.web.mvc.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created with IntelliJ IDEA.
 * User: jason.dimeo
 * Date: 5/16/13
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ExceptionHandlerStrategy<T> {
	public static final String DEFAULT_ERROR_MESSAGE = "An error occurred processing your request";

	Class<? extends Exception> getExceptionClass();
	HttpStatus getHttpStatus();
	String getErrorDescription();
	boolean isServerError();
	T handle(ServletWebRequest request, Exception exception);
}
