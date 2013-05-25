package com.alphatek.tylt.web.mvc.controller;

import com.alphatek.tylt.web.mvc.controller.error.ExceptionHandlerStrategy;
import com.alphatek.tylt.web.mvc.controller.error.ResponseEntityBuilder;
import com.alphatek.tylt.web.support.HttpServletResponseCopier;
import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public abstract class AbstractController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private static final Object[] EMPTY_OBJECT = new Object[] {};

	public ResponseEntity<Object> getExceptionResponseEntity(ServletWebRequest request, Throwable throwable) {
		// If ResponseEntity exists in request, return it and remove from response. If not, generate new ResponseEntity.
		@SuppressWarnings("unchecked")
		ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) request.getAttribute(RequestAttribute.RESPONSE_ENTITY.getName(), WebRequest.SCOPE_REQUEST);
		if (responseEntity == null) {
			String message = throwable.getMessage();
			responseEntity = ResponseEntityBuilder.fromWebRequest(request).withBody(message == null ? ExceptionHandlerStrategy.DEFAULT_ERROR_MESSAGE : message).build();
		} else {
			if (responseEntity.getBody() == null) {
				responseEntity = ResponseEntityBuilder.newInstance(responseEntity).withBody(ExceptionHandlerStrategy.DEFAULT_ERROR_MESSAGE).build();
			}
			request.removeAttribute(RequestAttribute.RESPONSE_ENTITY.getName(), WebRequest.SCOPE_REQUEST);
		}
		return responseEntity;
	}

	public void sendError(ServletWebRequest request, Exception exception) throws IOException {
		request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, exception, WebRequest.SCOPE_REQUEST);
		request.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
	}

	/**
	 * Method isValidSession.
	 * @param request HttpServletRequest
	 * @return boolean
	 */
	public boolean isValidSession(HttpServletRequest request) {
		return request.getSession(false) != null;
	}

	/**
	 * Method isAjaxRequest.
	 * @param requestedWith String
	 * @return boolean
	 */
	public boolean isAjaxRequest(String requestedWith) {
		return "XMLHttpRequest".equals(requestedWith);
	}

	/**
	 * Method clearCookies.
	 * Need to test this method!
	 * @param request  HttpServletRequest
	 * @param session HttpSession
	 */
	public void clearCookies(HttpServletRequest request, HttpSession session) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && session != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID") && !cookie.getValue().equals(session.getId())) {
					cookie = null;
				}
			}
		}
	}

	public String getHtmlOutput(HttpServletRequest request, HttpServletResponse response, String source) {
		String htmlOutput = "";
		try {
			HttpSession session = request.getSession(false);
			HttpServletResponseCopier respWrapper = new HttpServletResponseCopier(response);
			String sURL = respWrapper.encodeRedirectURL(source);

			if (response.getCharacterEncoding() == null) {
				response.setCharacterEncoding(Charsets.UTF_8.name());
			}

			HttpServletResponseCopier responseCopier = new HttpServletResponseCopier(response);

			try {
				RequestDispatcher requestDispatcher = session.getServletContext().getRequestDispatcher(sURL);
				requestDispatcher.include(request, respWrapper);
				responseCopier.flushBuffer();
			} finally {
				byte[] copy = responseCopier.getCopy();
				htmlOutput = new String(copy, response.getCharacterEncoding());
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return htmlOutput;
	}

	public WebApplicationContext getWebApplicationContext(HttpServletRequest request) {
		return WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
	}

	public String getContextMessage(HttpServletRequest request, String code) {
		return getWebApplicationContext(request).getMessage(code, EMPTY_OBJECT, Locale.getDefault());
	}

	public boolean springBeanExists(HttpServletRequest request, String id) {
		return getWebApplicationContext(request).containsBean(id);
	}

	public <T> T getSpringBean(HttpServletRequest request, Class<T> clazz) {
		return getWebApplicationContext(request).getBean(clazz);
	}

	public <T> T getSpringBean(HttpServletRequest request, String id, Class<T> clazz) {
		return getWebApplicationContext(request).getBean(id, clazz);
	}

	public Object getSpringBean(HttpServletRequest request, String id) {
		return getWebApplicationContext(request).getBean(id);
	}

	public Object getSpringBean(HttpServletRequest request, String id, Object... args) {
		return getWebApplicationContext(request).getBean(id, args);
	}

	public String getBaseURL(HttpServletRequest request) {
		return request.getRequestURI().substring(0, request.getQueryString().length());
	}

	public ServletContext getServletContext(HttpServletRequest request) {
		return request.getServletContext();
	}
}