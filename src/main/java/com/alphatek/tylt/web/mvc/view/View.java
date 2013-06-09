package com.alphatek.tylt.web.mvc.view;

import com.google.common.collect.ImmutableSet;

import java.util.EnumSet;
import java.util.Set;

public enum View {
	HOME("home.page"),
	LOGIN("login.page"),
	ERROR("error.page"),
	REGISTRATION("registration.page");

	private final String name;
	private static final class ViewsHolder {
		static final Set<View> VIEWS = ImmutableSet.copyOf(EnumSet.allOf(View.class));
	}

	private View(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	private static Set<View> getViews() {
		return ViewsHolder.VIEWS;
	}
}