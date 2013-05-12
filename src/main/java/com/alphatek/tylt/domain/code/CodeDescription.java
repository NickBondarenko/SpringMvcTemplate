package com.alphatek.tylt.domain.code;

/**
 * CodeDescription model. Used to hold a <code>code<T></code>
 * and a description (String)
 * Date: 3/23/13
 * Time: 8:53 PM
 * @author jason.dimeo
 */
public class CodeDescription<T> {
	private final T code;
	private final String description;

	public CodeDescription(T code, String description) {
		this.code = code;
		this.description = description;
	}

	public T getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
