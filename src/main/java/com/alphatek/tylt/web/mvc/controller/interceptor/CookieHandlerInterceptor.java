package com.alphatek.tylt.web.mvc.controller.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Propagates all cookies from page to iframes. Needed only for IE.
 * @author jason.dimeo
 * Date: 2013-06-13
 * Time: 5:41 PM
 */
public class CookieHandlerInterceptor extends HandlerInterceptorAdapter {
	@Override public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		response.setHeader("P3P","CP=\"ALL ADM DEV PSAi COM OUR OTRo STP IND ONL\"");
		super.postHandle(request, response, handler, modelAndView);
	}
}