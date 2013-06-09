package com.alphatek.tylt.web.support;

import com.alphatek.tylt.support.util.CollectionUtils;

import java.util.Set;

/**
 * @author jason.dimeo
 *         Date: 5/5/13
 *         Time: 12:29 PM
 */
public enum RequestAttribute {
	RESPONSE_ENTITY("responseEntity"),
	EXCEPTION_LOGGED("exceptionLogged");

	private static final class Holder {
		private static final Set<RequestAttribute> REQUEST_ATTRIBUTES = CollectionUtils.immutableEnumSet(RequestAttribute.class);
	}

	private final String name;
	private RequestAttribute(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Set<RequestAttribute> getRequestAttributes() {
		return Holder.REQUEST_ATTRIBUTES;
	}
}