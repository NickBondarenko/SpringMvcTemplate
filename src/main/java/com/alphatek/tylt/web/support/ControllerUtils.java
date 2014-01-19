package com.alphatek.tylt.web.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

/**
 * @author jason.dimeo
 *         Date: 5/27/13
 *         Time: 11:07 AM
 */
public final class ControllerUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerUtils.class);

	private ControllerUtils() {}

	public static void logControllerError(String message, Exception exception, RequestAttributes request) {
		LOGGER.error(message, exception);
		// Set flag in request that the exception has been logged.
		// This is read by ErrorController so it doesn't log the exception once again.
		request.setAttribute(RequestAttribute.EXCEPTION_LOGGED.getName(), true, WebRequest.SCOPE_REQUEST);
	}

	public static HttpHeaders generateHttpHeaders(HttpStatus httpStatus) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("status", String.valueOf(httpStatus.value()));
		responseHeaders.set("reason", String.valueOf(httpStatus.getReasonPhrase()));
		return responseHeaders;
	}
}