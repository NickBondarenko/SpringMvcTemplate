package com.alphatek.tylt.web.support;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.RequestDispatcher;

/**
 * @author jason.dimeo
 *         Date: 5/5/13
 *         Time: 4:30 PM
 */
public class ResponseEntityBuilder<T> {
	private WebRequest webRequest;
	private T body;
	private HttpStatus httpStatus;
	private HttpHeaders httpHeaders;

	private ResponseEntityBuilder() {}

	private ResponseEntityBuilder(WebRequest webRequest) {
		httpStatus = findHttpStatus(webRequest);
		httpHeaders = generateHttpHeaders(httpStatus);
	}

	private ResponseEntityBuilder(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
		httpHeaders = generateHttpHeaders(httpStatus);
	}

	public static <T> ResponseEntityBuilder<T> newInstance() {
		return new ResponseEntityBuilder<>();
	}

	public static <T> ResponseEntityBuilder<T> fromWebRequest(WebRequest webRequest) {
		return new ResponseEntityBuilder<>(webRequest);
	}

	public static <T> ResponseEntityBuilder<T> fromHttpStatus(HttpStatus httpStatus) {
		return new ResponseEntityBuilder<>(httpStatus);
	}

	public ResponseEntityBuilder<T> withBody(T body) {
		this.body = body;
		return this;
	}

	public ResponseEntityBuilder<T> withHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
		return this;
	}

	public ResponseEntityBuilder<T> withHttpHeaders(HttpHeaders httpHeaders) {
		this.httpHeaders = httpHeaders;
		return this;
	}

	private static HttpStatus findHttpStatus(WebRequest request) {
		return HttpStatus.valueOf((Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE, WebRequest.SCOPE_REQUEST));
	}

	public static HttpHeaders generateHttpHeaders(HttpStatus httpStatus) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("status", String.valueOf(httpStatus.value()));
		responseHeaders.set("reason", String.valueOf(httpStatus.getReasonPhrase()));
		return responseHeaders;
	}

	public ResponseEntity<T> build() {
		return new ResponseEntity<>(body, httpHeaders, httpStatus);
	}
}