package com.alphatek.tylt.domain.code;

public interface CodeSplitter<T> {
	T[] split(T code);
}