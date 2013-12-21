package com.alphatek.tylt.web.servlet.mvc.model;

import com.alphatek.tylt.domain.CodeDescription;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author jason.dimeo
 * Date: 2013-06-03 : 1:30 PM
 * Note: This class has a natural ordering that is inconsistent with equals.
 */
@com.alphatek.tylt.web.servlet.mvc.model.validate.constraints.State(message = "{com.alphatek.tylt.web.servlet.mvc.model.State.message}")
public final class State implements Comparable<State>, Serializable {
	private long id;
	private final CodeDescription<String> stateCodeDescription;

	public State() {
		stateCodeDescription = new CodeDescription<>();
	}

	public State(String abbreviation) {
		stateCodeDescription = new CodeDescription<>(abbreviation);
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Override public int compareTo(State state) {
		int abbreviationDiff = stateCodeDescription.getCode().compareTo(state.stateCodeDescription.getCode());
		if (abbreviationDiff != 0) { return abbreviationDiff; }
		return stateCodeDescription.getDescription().compareTo(state.stateCodeDescription.getDescription());
	}

	@Override public int hashCode() {
		return Objects.hashCode(id, stateCodeDescription);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final State other = (State) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.stateCodeDescription, other.stateCodeDescription);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("id", id)
			.add("abbreviation", stateCodeDescription.getCode())
			.add("name", stateCodeDescription.getDescription())
			.toString();
	}

}