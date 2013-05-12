package com.alphatek.tylt.web.mvc.controller;

/**
 * @author jason.dimeo
 *         Date: 5/5/13
 *         Time: 12:29 PM
 */
public enum RequestAttribute {
	RESPONSE_ENTITY("responseEntity"),
	EXCEPTION_LOGGED("exceptionLogged");

	private final String name;
	private RequestAttribute(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}