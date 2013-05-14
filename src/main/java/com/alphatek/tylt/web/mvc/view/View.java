package com.alphatek.tylt.web.mvc.view;

public enum View {
	HELLO("hello.page"),
	ERROR("error.page");

	private final String name;
	private View(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}