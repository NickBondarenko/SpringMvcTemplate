package com.alphatek.tylt.web.servlet.mvc.controller.error;

import com.alphatek.tylt.web.servlet.mvc.controller.AbstractController;
import com.alphatek.tylt.web.support.ControllerUtils;
import com.alphatek.tylt.web.support.RequestAttribute;
import com.google.common.base.Throwables;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.RequestDispatcher;

@Controller
public final class ErrorController extends AbstractController {
	private static final String ERROR_MESSAGE = "Error handling request...";

	@RequestMapping("/error")
	public void handleError(@RequestHeader(required = false, value = "X-Requested-With") String requestedWith, ServletWebRequest servletWebRequest, Exception exception) {
		Boolean exceptionLogged = (Boolean) servletWebRequest.getAttribute(RequestAttribute.EXCEPTION_LOGGED.getName(), WebRequest.SCOPE_REQUEST);
		if (exceptionLogged == null || !exceptionLogged) {
			Exception errorException = (Exception) servletWebRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION, WebRequest.SCOPE_REQUEST);
			if (errorException == null) {
				HttpStatus httpStatus = ControllerUtils.findHttpStatus(servletWebRequest);
				String errorMessage = "Request for " + servletWebRequest.getAttribute(RequestDispatcher.ERROR_REQUEST_URI, WebRequest.SCOPE_REQUEST) + " resulted in a status of: " + httpStatus.getReasonPhrase() + "[" + httpStatus.value() + "]";
				logger.error(errorMessage, exception);
			} else {
				logger.error("Handling exception...", Throwables.getRootCause(errorException));
			}
		}

		if (isAjaxRequest(requestedWith)) {
			throw new AjaxControllerException(generateErrorMessage(servletWebRequest), exception);
		} else {
			throw new ControllerException(generateErrorMessage(servletWebRequest), exception);
		}
	}

	private static String generateErrorMessage(ServletWebRequest servletWebRequest) {
		return ERROR_MESSAGE + servletWebRequest.getRequest().getRequestURI();
	}
}