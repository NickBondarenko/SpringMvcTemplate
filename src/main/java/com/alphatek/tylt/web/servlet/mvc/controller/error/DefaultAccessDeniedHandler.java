package com.alphatek.tylt.web.servlet.mvc.controller.error;

import com.alphatek.tylt.web.support.ControllerUtils;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author naquaduh
 *         Created on 2014-01-08:9:30 PM.
 */
public final class DefaultAccessDeniedHandler implements AccessDeniedHandler {
	private String errorPage;
	private final RequestCache requestCache;
	private final AuthenticationEntryPoint authenticationEntryPoint;
	private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAccessDeniedHandler.class);

	public DefaultAccessDeniedHandler(RequestCache requestCache, AuthenticationEntryPoint authenticationEntryPoint) {
		this.requestCache = requestCache;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if (!response.isCommitted()) {
			if (errorPage != null) {
				if (accessDeniedException instanceof InvalidCsrfTokenException) {
					ControllerUtils.logControllerError("Invalid CSRF Token!!!", accessDeniedException, new ServletWebRequest(request));
					request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpServletResponse.SC_FORBIDDEN);
					RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
					dispatcher.forward(request, response);
				} else {
					if (authenticationTrustResolver.isRememberMe(SecurityContextHolder.getContext().getAuthentication())) {
						sendStartAuthentication(request, response);
					} else {
						response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
					}
				}
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
			}
		}
	}

	private void sendStartAuthentication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// SEC-112: Clear the SecurityContextHolder's Authentication, as the existing Authentication is no longer considered valid
		SecurityContextHolder.getContext().setAuthentication(null);
		requestCache.saveRequest(request, response);
		LOGGER.debug("Calling Authentication entry point.");
		authenticationEntryPoint.commence(request, response, new InsufficientAuthenticationException("Full authentication is required to access this resource"));
	}

	public void setErrorPage(String errorPage) {
		Preconditions.checkArgument(errorPage != null && errorPage.startsWith("/"), "errorPage must begin with '/'");
		this.errorPage = errorPage;
	}
}