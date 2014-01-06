package com.alphatek.tylt.web.servlet.mvc.controller.interceptor;

import com.google.common.net.HttpHeaders;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author jason.dimeo
 * Created on 2013-12-18.
 */
public class ExtendedWebContentInterceptor extends WebContentInterceptor {
	private long lastModifiedSeconds;

	public long getLastModifiedSeconds() {
		return lastModifiedSeconds;
	}

	public void setLastModifiedSeconds(long lastModifiedSeconds) {
		this.lastModifiedSeconds = lastModifiedSeconds;
	}

	@Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		super.preHandle(request, response, handler);

		response.addDateHeader(HttpHeaders.LAST_MODIFIED, generateLastModifiedDate(lastModifiedSeconds).getTime());
		return true;
	}

	private Date generateLastModifiedDate(long lastModifiedSeconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, (int) TimeUnit.SECONDS.toDays(lastModifiedSeconds));
		return calendar.getTime();
	}
}