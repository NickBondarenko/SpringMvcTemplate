package com.alphatek.tylt.construct;

/**
 * ExtendableBuilder - Used by extendable classes that need to use the builder pattern.
 * all builder methods should return <code>builder()</code> instead of <code>this</code>
 * @author jason.dimeo
 *
 * @param <T>
 * @param <B>
 */
public interface ExtendableBuilder<T, B extends ExtendableBuilder<T, B>> extends Builder<T> {
	/**
	 * Method builder.
	 * @return B
	 */
	B builder();
}