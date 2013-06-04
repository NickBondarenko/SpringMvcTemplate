package com.alphatek.tylt.web.mvc.model;

import com.alphatek.tylt.domain.CodeDescription;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * User: jason.dimeo
 * Date: 2013-06-03 : 1:30 PM
 */
@com.alphatek.tylt.web.mvc.model.validate.constraints.State(message = "{com.alphatek.tylt.web.mvc.model.State.message}")
public final class State implements Serializable {
	private final CodeDescription<String> stateCodeDescription;

	public State() {
		stateCodeDescription = new CodeDescription<>();
	}

	public State(String abbreviation) {
		stateCodeDescription = new CodeDescription<>(abbreviation, "");
	}

	public State(String abbreviation, String name) {
		stateCodeDescription = new CodeDescription<>(abbreviation, name);
	}

	public static State newInstance() {
		return new State();
	}

	public static State newInstance(String abbreviation) {
		return new State(abbreviation);
	}

	public static State newInstance(String abbreviation, String name) {
		return new State(abbreviation, name);
	}

	public String getAbbreviation() {
		return stateCodeDescription.getCode();
	}

	public void setAbbreviation(String abbreviation) {
		stateCodeDescription.setCode(abbreviation);
	}

	public String getName() {
		return stateCodeDescription.getDescription();
	}

	public void setName(String name) {
		stateCodeDescription.setDescription(name);
	}

	@Override public int hashCode() {
		return Objects.hashCode(stateCodeDescription);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final State other = (State) obj;
		return Objects.equal(this.stateCodeDescription, other.stateCodeDescription);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("abbreviation", stateCodeDescription.getCode())
			.add("name", stateCodeDescription.getDescription())
			.toString();
	}

}