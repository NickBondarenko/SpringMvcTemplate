package com.alphatek.tylt.error;

import com.alphatek.tylt.web.support.ResponseEntityBuilder;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * @author jason.dimeo
 *         Date: 5/5/13
 *         Time: 8:48 AM
 */
public enum ResponseEntityExceptionHandlerStrategy {
	NO_SUCH_REQUEST_HANDLING_METHOD_EXCEPTION(NoSuchRequestHandlingMethodException.class, HttpStatus.NOT_FOUND, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			LOGGER.warn(ex.getMessage());
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(ex.getMessage()).build();
		}
	},
	HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION(HttpRequestMethodNotSupportedException.class, HttpStatus.METHOD_NOT_ALLOWED, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			LOGGER.warn(ex.getMessage());

			HttpHeaders httpHeaders = ResponseEntityBuilder.generateHttpHeaders(getHttpStatus());
			Set<HttpMethod> supportedMethods = ((HttpRequestMethodNotSupportedException) ex).getSupportedHttpMethods();
			if (!supportedMethods.isEmpty()) {
				httpHeaders.setAllow(supportedMethods);
			}
			return ResponseEntityBuilder.newInstance().withHttpStatus(getHttpStatus()).withHttpHeaders(httpHeaders).withBody(ex.getMessage()).build();
		}
	},
	HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION(HttpMediaTypeNotSupportedException.class, HttpStatus.UNSUPPORTED_MEDIA_TYPE, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			HttpHeaders httpHeaders = ResponseEntityBuilder.generateHttpHeaders(getHttpStatus());
			List<MediaType> mediaTypes = ((HttpMediaTypeNotSupportedException) ex).getSupportedMediaTypes();
			if (!CollectionUtils.isEmpty(mediaTypes)) {
				httpHeaders.setAccept(mediaTypes);
			}
			return ResponseEntityBuilder.newInstance().withHttpStatus(getHttpStatus()).withHttpHeaders(httpHeaders).withBody(ex.getMessage()).build();
		}
	},
	HTTP_MEDIA_TYPE_NOT_ACCEPTABLE_EXCEPTION(HttpMediaTypeNotAcceptableException.class, HttpStatus.NOT_ACCEPTABLE, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(ex.getMessage()).build();
		}
	},
	MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION(MissingServletRequestParameterException.class, HttpStatus.BAD_REQUEST, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(ex.getMessage()).build();
		}
	},
	SERVLET_REQUEST_BINDING_EXCEPTION(ServletRequestBindingException.class, HttpStatus.BAD_REQUEST, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(ex.getMessage()).build();
		}
	},
	CONVERSION_NOT_SUPPORTED_EXCEPTION(ConversionNotSupportedException.class, HttpStatus.INTERNAL_SERVER_ERROR, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(ex.getMessage()).build();
		}
	},
	TYPE_MISMATCH_EXCEPTION(org.springframework.beans.TypeMismatchException.class, HttpStatus.BAD_REQUEST, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(ex.getMessage()).build();
		}
	},
	HTTP_MESSAGE_NOT_READABLE_EXCEPTION(HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(ex.getMessage()).build();
		}
	},
	HTTP_MESSAGE_NOT_WRITABLE_EXCEPTION(HttpMessageNotWritableException.class, HttpStatus.INTERNAL_SERVER_ERROR, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(ex.getMessage()).build();
		}
	},
	METHOD_ARGUMENT_NOT_VALID_EXCEPTION(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(ex.getMessage()).build();
		}
	},
	MISSING_SERVLET_REQUEST_PART_EXCEPTION(MissingServletRequestPartException.class, HttpStatus.BAD_REQUEST, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(ex.getMessage()).build();
		}
	},
	BIND_EXCEPTION(BindException.class, HttpStatus.BAD_REQUEST, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(ex.getMessage()).build();
		}
	},
	METHOD_CONSTRAINT_VIOLATION(MethodConstraintViolationException.class, HttpStatus.INTERNAL_SERVER_ERROR, true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			Set<MethodConstraintViolation<?>> violations = ((MethodConstraintViolationException) ex).getConstraintViolations();
			List<String> messages = Lists.newArrayListWithExpectedSize(violations.size());
			for (MethodConstraintViolation<?> violation : violations) {
				messages.add(violation.getMessage());
			}
			//request.setAttribute("error.message", messages.toString());
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(messages).build();
		}
	};

	private final Class<?> exceptionClass;
	private final HttpStatus httpStatus;
	private final boolean serverError;
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseEntityExceptionHandlerStrategy.class);

	private ResponseEntityExceptionHandlerStrategy(Class<?> exceptionClass, HttpStatus httpStatus, boolean serverError) {
		this.exceptionClass = exceptionClass;
		this.httpStatus = httpStatus;
		this.serverError = serverError;
	}

	public static ResponseEntityExceptionHandlerStrategy findByException(final Exception exception) {
		return Iterables.find(EnumSet.allOf(ResponseEntityExceptionHandlerStrategy.class), new Predicate<ResponseEntityExceptionHandlerStrategy>() {
			@Override public boolean apply(ResponseEntityExceptionHandlerStrategy input) {
				return input.exceptionClass.isInstance(exception);
			}
		}, null);
	}

	public Class<?> getExceptionClass() {
		return exceptionClass;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public boolean isServerError() {
		return serverError;
	}

	public abstract ResponseEntity<Object> handle(ServletWebRequest request, Exception ex);
}
