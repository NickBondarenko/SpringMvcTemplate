package com.alphatek.tylt.web.support;

import com.google.common.base.Objects;

/**
 * @author jason.dimeo
 * Created on 12/19/13.
 */
public enum Path {
	ROOT("/"),
	ADMIN("/admin"),
	REGISTRATION("/registration");

	private final String value;
	private Path(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public String redirect() {
		return "redirect:" + value;
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("redirect", value)
			.toString();
	}
}