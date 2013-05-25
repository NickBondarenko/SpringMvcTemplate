package com.alphatek.tylt.domain;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class ApplicationError {
	private final String description;
	private final List<String> messages;

	public ApplicationError(String description, List<String> messages) {
		this.description = description;
		this.messages = ImmutableList.copyOf(messages);
	}

	public String getDescription() {
		return description;
	}

	public List<String> getMessages() {
		return messages;
	}

	@Override public int hashCode() {
		return Objects.hashCode(description, messages);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final ApplicationError other = (ApplicationError) obj;
		return Objects.equal(this.description, other.description) && Objects.equal(this.messages, other.messages);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("description", description)
			.add("messages", messages)
			.toString();
	}
}