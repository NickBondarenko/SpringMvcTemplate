package com.alphatek.tylt.construct;

/**
 * Created with IntelliJ IDEA.
 * User: jason.dimeo
 * Date: 3/23/13
 * Time: 8:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Buildable<T> {
	T asNewBuilder();
}
