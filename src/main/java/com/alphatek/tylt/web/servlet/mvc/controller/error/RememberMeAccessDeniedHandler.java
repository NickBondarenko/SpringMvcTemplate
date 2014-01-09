package com.alphatek.tylt.web.servlet.mvc.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author naquaduh
 *         Created on 2014-01-08:9:30 PM.
 */
public final class RememberMeAccessDeniedHandler implements AccessDeniedHandler {
	private final RequestCache requestCache;
	private final AuthenticationEntryPoint authenticationEntryPoint;
	private final AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
	private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
	private static final Logger logger = LoggerFactory.getLogger(RememberMeAccessDeniedHandler.class);

	public RememberMeAccessDeniedHandler(RequestCache requestCache, AuthenticationEntryPoint authenticationEntryPoint) {
		this.requestCache = requestCache;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if (authenticationTrustResolver.isRememberMe(SecurityContextHolder.getContext().getAuthentication())) {
			sendStartAuthentication(request, response);
		} else {
			accessDeniedHandler.handle(request, response, accessDeniedException);
		}
	}

	private void sendStartAuthentication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// SEC-112: Clear the SecurityContextHolder's Authentication, as the existing Authentication is no longer considered valid
		SecurityContextHolder.getContext().setAuthentication(null);
		requestCache.saveRequest(request, response);
		logger.debug("Calling Authentication entry point.");
		authenticationEntryPoint.commence(request, response, new InsufficientAuthenticationException("Full authentication is required to access this resource"));
	}

	public void setErrorPage(String errorPage) {
		accessDeniedHandler.setErrorPage(errorPage);
	}
}