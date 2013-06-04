package com.alphatek.tylt.web.mvc.model;

import com.alphatek.tylt.domain.CodeDescription;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author jason.dimeo
 *         Date: 6/3/13
 *         Time: 6:33 PM
 */
public class Country implements Serializable {
	private long id;
	private final CodeDescription<String> countryCodeDescription;

	public Country() {
		countryCodeDescription = new CodeDescription<>();
	}

	public Country(String code) {
		countryCodeDescription = new CodeDescription<>(code);
	}

	public Country(String code, String name) {
		countryCodeDescription = new CodeDescription<>(code, name);
	}

	public Country(Country country) {
		id = country.id;
		countryCodeDescription = new CodeDescription<>(country.getCode(), country.getName());
	}

	public static Country newInstance() {
		return new Country();
	}

	public static Country newInstance(String code) {
		return new Country(code);
	}

	public static Country newInstance(String code, String name) {
		return new Country(code, name);
	}

	public static Country newInstance(Country country) {
		return new Country(country);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return countryCodeDescription.getCode();
	}

	public void setCode(String code) {
		countryCodeDescription.setCode(code);
	}

	public String getName() {
		return countryCodeDescription.getDescription();
	}

	public void setName(String name) {
		countryCodeDescription.setDescription(name);
	}

	@Override public int hashCode() {
		return Objects.hashCode(id, countryCodeDescription);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final Country other = (Country) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.countryCodeDescription, other.countryCodeDescription);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("id", id)
			.add("code", countryCodeDescription.getCode())
			.add("name", countryCodeDescription.getDescription())
			.toString();
	}

}