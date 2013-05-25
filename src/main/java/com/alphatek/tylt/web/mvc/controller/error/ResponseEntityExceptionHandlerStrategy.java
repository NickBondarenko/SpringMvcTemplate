package com.alphatek.tylt.web.mvc.controller.error;

import com.alphatek.tylt.domain.ApplicationError;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
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
public enum ResponseEntityExceptionHandlerStrategy implements ExceptionHandlerStrategy<ResponseEntity<Object>> {
	NO_SUCH_REQUEST_HANDLING_METHOD_EXCEPTION(NoSuchRequestHandlingMethodException.class, HttpStatus.NOT_FOUND, "No Such Request", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			LOGGER.warn(ex.getMessage());
			ApplicationError applicationError = new ApplicationError(getErrorDescription(), ImmutableList.of(ex.getMessage()));
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(applicationError).build();
		}
	},
	HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION(HttpRequestMethodNotSupportedException.class, HttpStatus.METHOD_NOT_ALLOWED, "Request Method Not Supported", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			LOGGER.warn(ex.getMessage());

			HttpHeaders httpHeaders = ResponseEntityBuilder.generateHttpHeaders(getHttpStatus());
			Set<HttpMethod> supportedMethods = ((HttpRequestMethodNotSupportedException) ex).getSupportedHttpMethods();
			if (!supportedMethods.isEmpty()) {
				httpHeaders.setAllow(supportedMethods);
			}

			ApplicationError applicationError = new ApplicationError(getErrorDescription(), ImmutableList.of(ex.getMessage()));
			return ResponseEntityBuilder.newInstance().withHttpStatus(getHttpStatus()).withHttpHeaders(httpHeaders).withBody(applicationError).build();
		}
	},
	HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION(HttpMediaTypeNotSupportedException.class, HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			HttpHeaders httpHeaders = ResponseEntityBuilder.generateHttpHeaders(getHttpStatus());
			List<MediaType> mediaTypes = ((HttpMediaTypeNotSupportedException) ex).getSupportedMediaTypes();
			if (!CollectionUtils.isEmpty(mediaTypes)) {
				httpHeaders.setAccept(mediaTypes);
			}

			ApplicationError applicationError = new ApplicationError(getErrorDescription(), ImmutableList.of(ex.getMessage()));
			return ResponseEntityBuilder.newInstance().withHttpStatus(getHttpStatus()).withHttpHeaders(httpHeaders).withBody(applicationError).build();
		}
	},
	HTTP_MEDIA_TYPE_NOT_ACCEPTABLE_EXCEPTION(HttpMediaTypeNotAcceptableException.class, HttpStatus.NOT_ACCEPTABLE, "Unacceptable Media Type", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			ApplicationError applicationError = new ApplicationError(getErrorDescription(), ImmutableList.of(ex.getMessage()));
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(applicationError).build();
		}
	},
	MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION(MissingServletRequestParameterException.class, HttpStatus.BAD_REQUEST, "Servlet Request Parameter Missing", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			ApplicationError applicationError = new ApplicationError(getErrorDescription(), ImmutableList.of(ex.getMessage()));
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(applicationError).build();
		}
	},
	SERVLET_REQUEST_BINDING_EXCEPTION(ServletRequestBindingException.class, HttpStatus.BAD_REQUEST, "Servlet Request Binding Error", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			ApplicationError applicationError = new ApplicationError(getErrorDescription(), ImmutableList.of(ex.getMessage()));
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(applicationError).build();
		}
	},
	CONVERSION_NOT_SUPPORTED_EXCEPTION(ConversionNotSupportedException.class, HttpStatus.INTERNAL_SERVER_ERROR, "Unsupported Conversion", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			ApplicationError applicationError = new ApplicationError(getErrorDescription(), ImmutableList.of(ex.getMessage()));
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(applicationError).build();
		}
	},
	TYPE_MISMATCH_EXCEPTION(TypeMismatchException.class, HttpStatus.BAD_REQUEST, "Type Conversion Error", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			ApplicationError applicationError = new ApplicationError(getErrorDescription(), ImmutableList.of(getRootCauseMessage(ex)));
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(applicationError).build();
		}
	},
	HTTP_MESSAGE_NOT_READABLE_EXCEPTION(HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST, "Message Not Readable", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			ApplicationError applicationError = new ApplicationError(getErrorDescription(), ImmutableList.of(ex.getMessage()));
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(applicationError).build();
		}
	},
	HTTP_MESSAGE_NOT_WRITABLE_EXCEPTION(HttpMessageNotWritableException.class, HttpStatus.INTERNAL_SERVER_ERROR, "Message Not Writable", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			ApplicationError applicationError = new ApplicationError(getErrorDescription(), ImmutableList.of(ex.getMessage()));
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(applicationError).build();
		}
	},
	METHOD_ARGUMENT_NOT_VALID_EXCEPTION(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST, "Invalid Method Argument", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			ApplicationError applicationError = new ApplicationError(getErrorDescription(), ImmutableList.of(ex.getMessage()));
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(applicationError).build();
		}
	},
	MISSING_SERVLET_REQUEST_PART_EXCEPTION(MissingServletRequestPartException.class, HttpStatus.BAD_REQUEST, "Servlet Request Part Missing", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			ApplicationError applicationError = new ApplicationError(getErrorDescription(), ImmutableList.of(ex.getMessage()));
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(applicationError).build();
		}
	},
	BIND_EXCEPTION(BindException.class, HttpStatus.BAD_REQUEST, "Bind Error", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			List<FieldError> fieldErrors = ((BindException) ex).getFieldErrors();
			List<String> errorMessages = Lists.newArrayListWithExpectedSize(fieldErrors.size());
			for (FieldError fieldError : fieldErrors) {
				errorMessages.add(String.format(fieldError.getDefaultMessage(), fieldError.getRejectedValue()));
			}

			ApplicationError applicationError = new ApplicationError(getErrorDescription(), errorMessages);
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(applicationError).build();
		}
	},
	METHOD_CONSTRAINT_VIOLATION(MethodConstraintViolationException.class, HttpStatus.INTERNAL_SERVER_ERROR, "Method Constraint Violation", true) {
		@Override public ResponseEntity<Object> handle(ServletWebRequest request, Exception ex) {
			Set<MethodConstraintViolation<?>> violations = ((MethodConstraintViolationException) ex).getConstraintViolations();
			List<String> violationMessages = Lists.newArrayListWithExpectedSize(violations.size());
			for (MethodConstraintViolation<?> violation : violations) {
				violationMessages.add(String.format(violation.getMessage(), violation.getInvalidValue()));
			}

			ApplicationError applicationError = new ApplicationError(getErrorDescription(), violationMessages);
			return ResponseEntityBuilder.fromHttpStatus(getHttpStatus()).withBody(applicationError).build();
		}
	};

	private final Class<? extends Exception> exceptionClass;
	private final HttpStatus httpStatus;
	private final String errorDescription;
	private final boolean serverError;
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseEntityExceptionHandlerStrategy.class);

	private static final class ExceptionHandlerStrategyHolder {
		static final Set<ResponseEntityExceptionHandlerStrategy> EXCEPTION_HANDLER_STRATEGIES = Sets.immutableEnumSet(EnumSet.allOf(ResponseEntityExceptionHandlerStrategy.class));
	}

	private ResponseEntityExceptionHandlerStrategy(Class<? extends Exception> exceptionClass, HttpStatus httpStatus, String errorDescription, boolean serverError) {
		this.exceptionClass = exceptionClass;
		this.httpStatus = httpStatus;
		this.errorDescription = errorDescription;
		this.serverError = serverError;
	}

	public static ResponseEntityExceptionHandlerStrategy findByException(final Exception exception) {
		return Iterables.find(ExceptionHandlerStrategyHolder.EXCEPTION_HANDLER_STRATEGIES, new Predicate<ResponseEntityExceptionHandlerStrategy>() {
			@Override public boolean apply(ResponseEntityExceptionHandlerStrategy input) {
				return input.exceptionClass.isInstance(exception);
			}
		}, null);
	}

	private static String getRootCauseMessage(Throwable throwable) {
		return Throwables.getRootCause(throwable).getMessage();
	}

	public static Set<ResponseEntityExceptionHandlerStrategy> getExceptionHandlerStrategies() {
		return ExceptionHandlerStrategyHolder.EXCEPTION_HANDLER_STRATEGIES;
	}

	@Override public Class<? extends Exception> getExceptionClass() {
		return exceptionClass;
	}

	@Override public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override public String getErrorDescription() {
		return errorDescription;
	}

	@Override public boolean isServerError() {
		return serverError;
	}

	@Override public abstract ResponseEntity<Object> handle(ServletWebRequest request, Exception exception);
}
