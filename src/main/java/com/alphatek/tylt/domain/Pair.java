package com.alphatek.tylt.domain;

import com.google.common.base.Objects;

public class Pair<F, S> {
	private final F first;
	private final S second;

	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	public static <F, S> Pair<F, S> newInstance(F first, S second) {
		return new Pair<>(first, second);
	}

	public F getFirst() {
		return first;
	}

	public S getSecond() {
		return second;
	}

	@Override public int hashCode() {
		return Objects.hashCode(first, second);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }

		final Pair other = (Pair) obj;
		return Objects.equal(this.first, other.first) && Objects.equal(this.second, other.second);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("first", first)
			.add("second", second)
			.toString();
	}
}