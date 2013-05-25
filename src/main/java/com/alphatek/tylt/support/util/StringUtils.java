package com.alphatek.tylt.support.util;

import com.google.common.base.Strings;

public class StringUtils {
	private StringUtils() {}

	public static boolean isNullOrEmpty(String string) {
		return Strings.isNullOrEmpty(string);
	}

	public static String nullToEmpty(String string) {
		return Strings.nullToEmpty(string);
	}
}