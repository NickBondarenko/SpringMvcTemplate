package com.alphatek.tylt.web.mvc.controller;

import com.alphatek.tylt.error.AjaxControllerException;
import com.alphatek.tylt.error.ControllerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;

@Controller
public final class ErrorController extends AbstractController {
	private static final String ERROR_MESSAGE = "Error handling request";

	@RequestMapping(value="/error")
	public void handleError(@RequestHeader(required = false, value = "X-Requested-With") String requestedWith, ServletWebRequest servletWebRequest, Exception exception) throws ServletException {
		Boolean exceptionLogged = (Boolean) servletWebRequest.getAttribute(RequestAttribute.EXCEPTION_LOGGED.getName(), WebRequest.SCOPE_REQUEST);
		if (exceptionLogged == null || !exceptionLogged) {
			logger.error("Handling exception...", exception);
		}

		if (isAjaxRequest(requestedWith)) {
			throw new AjaxControllerException(ERROR_MESSAGE, exception);
		} else {
			throw new ControllerException(ERROR_MESSAGE, exception);
		}
	}
}