package com.alphatek.tylt.web.mvc.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;

public interface ExceptionHandlerStrategy<T> {
	public static final String DEFAULT_ERROR_MESSAGE = "An error occurred processing your request";

	Class<? extends Exception> getExceptionClass();
	HttpStatus getHttpStatus();
	String getErrorDescription();
	boolean isServerError();
	T handle(ServletWebRequest request, Exception exception);
}