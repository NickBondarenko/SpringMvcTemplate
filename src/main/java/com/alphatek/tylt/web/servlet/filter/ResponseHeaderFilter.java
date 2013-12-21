package com.alphatek.tylt.web.servlet.filter;

import com.google.common.base.Objects;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jason.dimeo
 * Created on 12/18/13.
 */
public class ResponseHeaderFilter implements Filter {
	private String header;
	private String value;

	@Override public void init(FilterConfig filterConfig) throws ServletException {
		header = filterConfig.getInitParameter("header");
		value = filterConfig.getInitParameter("value");
	}

	@Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.addHeader(header, value);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override public void destroy() {}

	@Override public int hashCode() {
		return Objects.hashCode(header, value);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final ResponseHeaderFilter other = (ResponseHeaderFilter) obj;
		return Objects.equal(this.header, other.header) && Objects.equal(this.value, other.value);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this).add("header", header).add("value", value).toString();
	}
}