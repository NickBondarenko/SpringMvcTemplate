package com.alphatek.tylt.web.servlet.mvc.controller;

import com.alphatek.tylt.web.servlet.mvc.controller.error.ExceptionHandlerStrategy;
import com.alphatek.tylt.web.support.HttpServletResponseCopier;
import com.alphatek.tylt.web.support.RequestAttribute;
import com.alphatek.tylt.web.support.ResponseEntityBuilder;
import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
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
import java.util.Objects;

public abstract class AbstractController implements Controller {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override public ResponseEntity<Object> getExceptionResponseEntity(ServletWebRequest servletWebRequest, Throwable throwable) {
		// If ResponseEntity exists in request, return it and remove from response. If not, generate new ResponseEntity.
		@SuppressWarnings("unchecked")
		ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) servletWebRequest.getAttribute(RequestAttribute.RESPONSE_ENTITY.getName(), WebRequest.SCOPE_REQUEST);
		if (responseEntity == null) {
			String message = throwable.getCause().getMessage();
			responseEntity = ResponseEntityBuilder.fromWebRequest(servletWebRequest).withBody(message == null ? ExceptionHandlerStrategy.DEFAULT_ERROR_MESSAGE : message).build();
		} else {
			if (responseEntity.getBody() == null) {
				responseEntity = ResponseEntityBuilder.newInstance(responseEntity).withBody(ExceptionHandlerStrategy.DEFAULT_ERROR_MESSAGE).build();
			}
			servletWebRequest.removeAttribute(RequestAttribute.RESPONSE_ENTITY.getName(), WebRequest.SCOPE_REQUEST);
		}
		return responseEntity;
	}

	@Override public void sendError(ServletWebRequest servletWebRequest, Exception exception) throws IOException {
		servletWebRequest.setAttribute(RequestDispatcher.ERROR_EXCEPTION, exception, WebRequest.SCOPE_REQUEST);
		servletWebRequest.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
	}

	/**
	 * Method isValidSession.
	 * @param request HttpServletRequest
	 * @return boolean
	 */
	@Override public boolean isValidSession(HttpServletRequest request) {
		return request.getSession(false) != null;
	}

	/**
	 * Method isAjaxRequest.
	 * @param requestedWith String
	 * @return boolean
	 */
	@Override public boolean isAjaxRequest(String requestedWith) {
		return Objects.equals("XMLHttpRequest", requestedWith);
	}

	/**
	 * Method clearCookies.
	 * Need to test this method!
	 * @param request  HttpServletRequest
	 * @param session HttpSession
	 */
	@Override public void clearCookies(HttpServletRequest request, HttpSession session) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && session != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID") && !cookie.getValue().equals(session.getId())) {
					cookie = null;
				}
			}
		}
	}

	@Override public String getHtmlOutput(HttpServletRequest request, HttpServletResponse response, String source) {
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

	public WebApplicationContext getWebApplicationContext(ServletContext servletContext) {
		return WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}

	@Override public WebApplicationContext getWebApplicationContext(HttpServletRequest request) {
		return getWebApplicationContext(request.getServletContext());
	}

	public String getMessage(ServletContext servletContext, String code) {
		return getWebApplicationContext(servletContext).getMessage(code, null, Locale.getDefault());
	}

	@Override public String getMessage(HttpServletRequest request, String code) {
		return getMessage(request.getServletContext(), code);
	}

	public String getMessage(ServletContext servletContext, String code, Object[] args) {
		return getWebApplicationContext(servletContext).getMessage(code, args, Locale.getDefault());
	}

	@Override public String getMessage(HttpServletRequest request, String code, Object[] args) {
		return getMessage(request.getServletContext(), code, args);
	}

	public String getMessage(ServletContext servletContext, String code, Object[] args, Locale locale) {
		return getWebApplicationContext(servletContext).getMessage(code, args, locale);
	}

	@Override public String getMessage(HttpServletRequest request, String code, Object[] args, Locale locale) {
		return getMessage(request.getServletContext(), code, args, locale);
	}

	public boolean beanExists(ServletContext servletContext, String id) {
		return getWebApplicationContext(servletContext).containsBean(id);
	}

	@Override public boolean beanExists(HttpServletRequest request, String id) {
		return beanExists(request.getServletContext(), id);
	}

	public <T> T getBean(ServletContext servletContext, Class<T> clazz) {
		return getWebApplicationContext(servletContext).getBean(clazz);
	}

	@Override public <T> T getBean(HttpServletRequest request, Class<T> clazz) {
		return getBean(request.getServletContext(), clazz);
	}

	public <T> T getBean(ServletContext servletContext, String id, Class<T> clazz) {
		return getWebApplicationContext(servletContext).getBean(id, clazz);
	}

	@Override public <T> T getBean(HttpServletRequest request, String id, Class<T> clazz) {
		return getBean(request.getServletContext(), id, clazz);
	}

	@Override public Object getBean(HttpServletRequest request, String id) {
		return getWebApplicationContext(request).getBean(id);
	}

	public Object getBean(ServletContext servletContext, String id, Object... args) {
		return getWebApplicationContext(servletContext).getBean(id, args);
	}

	@Override public Object getBean(HttpServletRequest request, String id, Object... args) {
		return getBean(request.getServletContext(), id, args);
	}

	public Resource getResource(ServletContext servletContext, String location) {
		return getWebApplicationContext(servletContext).getResource(location);
	}

	@Override public Resource getResource(HttpServletRequest request, String location) {
		return getResource(request.getServletContext(), location);
	}

	@Override public String getBaseURL(HttpServletRequest request) {
		return request.getRequestURI().substring(0, request.getQueryString().length());
	}
}