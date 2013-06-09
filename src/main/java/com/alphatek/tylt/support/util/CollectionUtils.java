package com.alphatek.tylt.support.util;

import com.google.common.collect.ImmutableSet;

import java.util.EnumSet;
import java.util.Set;

/**
 * User: jason.dimeo
 * Date: 2013-06-09 : 7:40 PM
 */
public class CollectionUtils {
	private CollectionUtils() {}

	public static <E extends Enum<E>> Set<E> immutableEnumSet(Class<E> enumClass) {
		return ImmutableSet.copyOf(EnumSet.allOf(enumClass));
	}
}
