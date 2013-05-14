package com.alphatek.tylt.domain.construct;

public interface Buildable<T> {
	T asNewBuilder();
}
