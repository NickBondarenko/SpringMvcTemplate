package com.alphatek.tylt.domain;

import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * CodeDescription model. Used to hold a <code>code<T></code>
 * and a description (String)
 * Date: 3/23/13
 * Time: 8:53 PM
 * @author jason.dimeo
 */
public class CodeDescription<T> {
	@NotNull private T code;
	@NotBlank private String description;

	public CodeDescription() {}

	public CodeDescription(T code, String description) {
		this.code = code;
		this.description = description;
	}

	public T getCode() {
		return code;
	}

	public void setCode(T code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override public int hashCode() {
		return Objects.hashCode(code, description);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final CodeDescription other = (CodeDescription) obj;
		return Objects.equal(this.code, other.code) && Objects.equal(this.description, other.description);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("code", code)
			.add("description", description)
			.toString();
	}

}