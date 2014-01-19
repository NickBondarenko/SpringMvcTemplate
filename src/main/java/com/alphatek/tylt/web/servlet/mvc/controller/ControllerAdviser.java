package com.alphatek.tylt.web.servlet.mvc.controller;

import static com.alphatek.tylt.web.servlet.mvc.view.View.ERROR;

import com.alphatek.tylt.web.servlet.mvc.controller.error.AjaxControllerException;
import com.alphatek.tylt.web.servlet.mvc.controller.error.ControllerException;
import com.alphatek.tylt.web.servlet.mvc.controller.error.ExceptionHandlerStrategy;
import com.alphatek.tylt.web.servlet.mvc.controller.error.ResponseEntityExceptionHandlerStrategy;
import com.alphatek.tylt.web.support.ControllerUtils;
import com.alphatek.tylt.web.support.RequestAttribute;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;

/**
 * Controller Adviser - Global Controller Adviser
 * @author jason.dimeo
 * Date: 2013-03-30
 * Time: 10:36 AM
 */
@ControllerAdvice
public final class ControllerAdviser extends AbstractController {

	@ExceptionHandler public ResponseEntity<Object> handleException(ServletWebRequest request, Exception exception) {
		ResponseEntity<Object> responseEntity = null;

		ControllerUtils.logControllerError("Handling controller error", exception, request);

		try {
			ExceptionHandlerStrategy<ResponseEntity<Object>> exceptionHandlerStrategy = ResponseEntityExceptionHandlerStrategy.findByException(exception);

			if (exceptionHandlerStrategy != null) {
				if (exceptionHandlerStrategy.getHttpStatus() == HttpStatus.INTERNAL_SERVER_ERROR) {
					request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, exception, WebRequest.SCOPE_REQUEST);
				}

				responseEntity = exceptionHandlerStrategy.handle(request, exception);

				if (exceptionHandlerStrategy.isServerError()) {
					sendError(request, exception);
				}
			} else {
				logger.warn("No ExceptionHandlerStrategy found for " + exception.getClass().getName());
				sendError(request, exception);
				responseEntity = getExceptionResponseEntity(request, exception);
			}
		} catch (Exception e) {
			logger.warn("Handling of [" + exception.getClass().getName() + "] resulted in an Exception", e);
		}

		request.setAttribute(RequestAttribute.RESPONSE_ENTITY.getName(), responseEntity, WebRequest.SCOPE_REQUEST);

		return responseEntity;
	}

	@ExceptionHandler(AjaxControllerException.class)
	public ResponseEntity<Object> handleAjaxServletException(ServletWebRequest request, AjaxControllerException exception) {
		return getExceptionResponseEntity(request, exception);
	}

	@ExceptionHandler(ControllerException.class)
	public ModelAndView handleStandardServletException(ServletWebRequest request, ControllerException exception) {
		ModelAndView modelAndView = new ModelAndView(ERROR.getName());
		modelAndView.addObject(RequestAttribute.RESPONSE_ENTITY.getName(), getExceptionResponseEntity(request, exception));
		return modelAndView;
	}
}