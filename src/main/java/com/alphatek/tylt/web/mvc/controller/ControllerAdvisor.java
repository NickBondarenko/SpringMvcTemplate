package com.alphatek.tylt.web.mvc.controller;

import static com.alphatek.tylt.web.mvc.view.View.ERROR;

import com.alphatek.tylt.error.AjaxControllerException;
import com.alphatek.tylt.error.ControllerException;
import com.alphatek.tylt.error.ResponseEntityExceptionHandlerStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;

/**
 * Controller Advisor - Global Controller Advisor
 * Date: 3/30/13
 * Time: 10:36 AM
 * @author jason.dimeo
 */
@ControllerAdvice
public class ControllerAdvisor extends AbstractController {

	@InitBinder	public void initBinder(WebDataBinder webDataBinder) {
		logger.debug("Running InitBinder...");
	}

	@ExceptionHandler public ResponseEntity<Object> handleException(ServletWebRequest request, Exception exception) {
		ResponseEntity<Object> responseEntity = null;

		logger.error("Handling controller error", exception);
		// Set flag in request that the exception has been logged.
		// This is read by ErrorController so it doesn't log the exception once again.
		request.setAttribute(RequestAttribute.EXCEPTION_LOGGED.getName(), true, WebRequest.SCOPE_REQUEST);

		try {
			ResponseEntityExceptionHandlerStrategy responseEntityExceptionHandlerStrategy = ResponseEntityExceptionHandlerStrategy.findByException(exception);

			if (responseEntityExceptionHandlerStrategy != null) {
				if (responseEntityExceptionHandlerStrategy.getHttpStatus() == HttpStatus.INTERNAL_SERVER_ERROR) {
					request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, exception, WebRequest.SCOPE_REQUEST);
				}

				responseEntity = responseEntityExceptionHandlerStrategy.handle(request, exception);

				if (responseEntityExceptionHandlerStrategy.isServerError()) {
					sendError(request, exception);
				}
			} else {
				logger.warn("No ResponseEntityExceptionHandlerStrategy found for " + exception.getClass().getName());
				sendError(request, exception);
				responseEntity = getExceptionResponseEntity(request, exception);
			}
		} catch (Exception e) {
			logger.warn("Handling of [" + exception.getClass().getName() + "] resulted in Exception", e);
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