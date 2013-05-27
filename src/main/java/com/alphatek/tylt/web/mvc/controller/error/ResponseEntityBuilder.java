package com.alphatek.tylt.web.mvc.controller.error;

import com.alphatek.tylt.domain.construct.Builder;
import com.alphatek.tylt.web.support.ControllerUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

/**
 * @author jason.dimeo
 *         Date: 5/5/13
 *         Time: 4:30 PM
 */
public class ResponseEntityBuilder {

	public static interface SourceStep {
		BodyStep fromWebRequest(WebRequest webRequest);
		BodyStep fromHttpStatus(HttpStatus httpStatus);
	}

	public static interface BodyStep<T> {
		BuildStep<T> withBody(T body);
	}

	public static interface WithStep<T> extends BodyStep<T> {
		WithStep<T> withHttpStatus(HttpStatus httpStatus);
		WithStep<T> withHttpHeaders(HttpHeaders httpHeaders);
	}

	public static interface BuildStep<T> {
		ResponseEntity<T> build();
	}

	public static <T> WithStep<T> newInstance() {
		return new Steps<>();
	}

	public static <T> WithStep<T> newInstance(ResponseEntity<T> responseEntity) {
		return new Steps<>(responseEntity);
	}

	public static <T> BodyStep<T> fromWebRequest(WebRequest webRequest) {
		return new Steps<T>().fromWebRequest(webRequest);
	}

	public static <T> BodyStep<T> fromHttpStatus(HttpStatus httpStatus) {
		return new Steps<T>().fromHttpStatus(httpStatus);
	}

	public static class Steps<T> implements Builder<ResponseEntity<T>>, SourceStep, BodyStep<T>, WithStep<T>, BuildStep<T> {
		private T body;
		private HttpStatus httpStatus;
		private HttpHeaders httpHeaders;

		private Steps() {}

		private Steps(ResponseEntity<T> responseEntity) {
			body = responseEntity.getBody();
			httpStatus = responseEntity.getStatusCode();
			httpHeaders = responseEntity.getHeaders();
		}

		@Override public BodyStep<T> fromWebRequest(WebRequest webRequest) {
			httpStatus = ControllerUtils.findHttpStatus(webRequest);
			httpHeaders = ControllerUtils.generateHttpHeaders(httpStatus);
			return this;
		}

		@Override public BodyStep<T> fromHttpStatus(HttpStatus httpStatus) {
			this.httpStatus = httpStatus;
			httpHeaders = ControllerUtils.generateHttpHeaders(httpStatus);
			return this;
		}

		@Override public WithStep<T> withHttpStatus(HttpStatus httpStatus) {
			this.httpStatus = httpStatus;
			return this;
		}

		@Override public WithStep<T> withHttpHeaders(HttpHeaders httpHeaders) {
			this.httpHeaders = httpHeaders;
			return this;
		}

		@Override public BuildStep<T> withBody(T body) {
			this.body = body;
			return this;
		}

		@Override public ResponseEntity<T> build() {
			return new ResponseEntity<>(body, httpHeaders, httpStatus);
		}
	}
}