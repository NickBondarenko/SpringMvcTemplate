package com.alphatek.tylt.web.mvc.controller;

import com.alphatek.tylt.web.mvc.controller.error.AjaxControllerException;
import com.alphatek.tylt.web.mvc.controller.error.ControllerException;
import com.google.common.base.Throwables;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Controller
public final class ErrorController extends AbstractController {
	private static final String ERROR_MESSAGE = "Error handling request...";

	@RequestMapping(value="/error")
	public void handleError(@RequestHeader(required = false, value = "X-Requested-With") String requestedWith, ServletWebRequest servletWebRequest, Exception exception) {
		Boolean exceptionLogged = (Boolean) servletWebRequest.getAttribute(RequestAttribute.EXCEPTION_LOGGED.getName(), WebRequest.SCOPE_REQUEST);
		if (exceptionLogged == null || !exceptionLogged) {
			logger.error("Handling exception...", Throwables.getRootCause(exception));
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