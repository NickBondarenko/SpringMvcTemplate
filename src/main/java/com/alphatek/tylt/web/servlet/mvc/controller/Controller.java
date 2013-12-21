package com.alphatek.tylt.web.servlet.mvc.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public interface Controller {

	ResponseEntity<Object> getExceptionResponseEntity(ServletWebRequest request, Throwable throwable);

	void sendError(ServletWebRequest request, Exception exception) throws IOException;

	boolean isValidSession(HttpServletRequest request);

	boolean isAjaxRequest(String requestHeader);

	/**
	 * Method clearCookies.
	 * @param req HttpServletRequest
	 * @param sess HttpSession
	 */
	void clearCookies(HttpServletRequest req, HttpSession sess);

	String getHtmlOutput(HttpServletRequest request, HttpServletResponse response, String source);

	WebApplicationContext getWebApplicationContext(HttpServletRequest request);

	String getMessage(HttpServletRequest request, String code);

	String getMessage(HttpServletRequest request, String code, Object[] args);

	String getMessage(HttpServletRequest request, String code, Object[] args, Locale locale);

	boolean beanExists(HttpServletRequest request, String id);

	<T> T getBean(HttpServletRequest request, Class<T> clazz);

	<T> T getBean(HttpServletRequest request, String id, Class<T> clazz);

	Object getBean(HttpServletRequest request, String id);

	Object getBean(HttpServletRequest request, String id, Object... args);

	Resource getResource(HttpServletRequest request, String location);

	String getBaseURL(HttpServletRequest request);
}
