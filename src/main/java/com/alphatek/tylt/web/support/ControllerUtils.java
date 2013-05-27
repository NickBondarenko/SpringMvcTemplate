package com.alphatek.tylt.web.support;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.RequestDispatcher;

/**
 * @author jason.dimeo
 *         Date: 5/27/13
 *         Time: 11:07 AM
 */
public final class ControllerUtils {
	private ControllerUtils() {}

	public static HttpStatus findHttpStatus(WebRequest request) {
		Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE, WebRequest.SCOPE_REQUEST);
		if (statusCode == null) {
			statusCode = 500;
		}
		return HttpStatus.valueOf(statusCode);
	}

	public static HttpHeaders generateHttpHeaders(HttpStatus httpStatus) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("status", String.valueOf(httpStatus.value()));
		responseHeaders.set("reason", String.valueOf(httpStatus.getReasonPhrase()));
		return responseHeaders;
	}
}
