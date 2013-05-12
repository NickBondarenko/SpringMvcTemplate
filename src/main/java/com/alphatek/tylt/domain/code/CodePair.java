package com.alphatek.tylt.domain.code;

import com.alphatek.tylt.domain.Pair;
import com.google.common.base.Preconditions;

public final class CodePair<T> {
	private final Pair<T, T> pair;
	//private final CodeSplitter<T> splitter;

	public CodePair(T first, T second) {
		pair = Pair.newInstance(first, second);
	}

	public static <T> CodePair<T> newInstance(T first, T second) {
		return new CodePair<>(first, second);
	}

	public static <T> CodePair<T> fromCode(T code, CodeSplitter<T> splitter) {
		T[] codes = splitter.split(code);
		Preconditions.checkState(codes.length == 2, "CodeSplitter.split returned an invalid result. Size of the array was %s. Expected 2.", codes.length);
		return new CodePair<>(codes[0], codes[1]);
	}

	public static <T> CodePair<T> fromParts(T first, T second, CodeJoiner<T> codeJoiner) {
		return new CodePair<>(first, second);
	}

	public T getFirst() {
		return pair.getFirst();
	}

	public T getSecond() {
		return pair.getSecond();
	}
}