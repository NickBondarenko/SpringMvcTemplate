package com.alphatek.tylt.web.servlet.mvc.view;

import com.google.common.collect.ImmutableSet;

import java.util.EnumSet;
import java.util.Set;

public enum View {
	HOME("home.page"),
	LOGIN("login.page"),
	ERROR("error.page"),
	REGISTRATION("registration.page");

	private final String name;
	private View(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Set<View> getViews() {
		return ImmutableSet.copyOf(ViewsHolder.VIEWS);
	}

	private static final class ViewsHolder {
		static final Set<View> VIEWS = ImmutableSet.copyOf(EnumSet.allOf(View.class));
	}
}