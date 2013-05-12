package com.alphatek.tylt.web.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/23/13
 * Time: 12:37 AM
 * @author jason.dimeo
 */
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

	String getHtmlOutput(HttpServletRequest request, HttpServletResponse response, String source) throws ServletException, IOException;

	WebApplicationContext getWebApplicationContext(HttpServletRequest request);

	String getContextMessage(HttpServletRequest request, String code);

	boolean springBeanExists(HttpServletRequest request, String id);

	<T> T getSpringBean(HttpServletRequest request, Class<T> clazz);

	<T> T getSpringBean(HttpServletRequest request, String id, Class<T> clazz);

	Object getSpringBean(HttpServletRequest request, String id);

	Object getSpringBean(HttpServletRequest request, String id, Object... args);

	String getBaseURL(HttpServletRequest request);

	ServletContext getServletContext(HttpServletRequest request);
}
