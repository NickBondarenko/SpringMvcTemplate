package com.alphatek.tylt.web.mvc.view;

/**
 * Created with IntelliJ IDEA.
 * User: jason.dimeo
 * Date: 3/30/13
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
public enum View {
	HELLO("home.page"),
	ERROR("error.page");

	private final String name;
	private View(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}