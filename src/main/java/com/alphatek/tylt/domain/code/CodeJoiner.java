package com.alphatek.tylt.domain.code;

public interface CodeJoiner<T> {
	T join(T first, T second);
}