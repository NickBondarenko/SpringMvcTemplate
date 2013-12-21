package com.alphatek.tylt.web.servlet.mvc.model;

import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The type Address.
 */
public final class Address implements Serializable {
	private long id;
	@NotEmpty(message = "{com.alphatek.tylt.web.servlet.mvc.model.Address.street[NotEmpty.message]}")
	private String street;
	private String additionalInfo;
	@NotEmpty(message = "{com.alphatek.tylt.web.servlet.mvc.model.Address.city[NotEmpty.message]}")
	private String city;
	@NotNull(message = "{com.alphatek.tylt.web.servlet.mvc.model.Address.state[NotNull.message]}")
	private State state;
	@Valid private Country country;
	@NotNull(message = "{com.alphatek.tylt.web.servlet.mvc.model.Address.zipCode[NotNull.message]}")
	@Valid private ZipCode zipCode;
	private String county;
	private boolean withinCityLimits;
	private int municipalityCode;
	private boolean deliverable;

	public Address() {}

	public Address(Address address) {
		id = address.id;
		street = address.street;
		additionalInfo = address.additionalInfo;
		city = address.city;
		state = address.state;
		country = address.country;
		zipCode = address.zipCode;
		county = address.county;
		withinCityLimits = address.withinCityLimits;
		municipalityCode = address.municipalityCode;
		deliverable = address.deliverable;
	}

	public static Address newInstance() {
		return new Address();
	}

	public static Address newInstance(Address address) {
		return new Address(address);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public ZipCode getZipCode() {
		return zipCode;
	}

	public void setZipCode(ZipCode zipCode) {
		this.zipCode = zipCode;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public boolean isWithinCityLimits() {
		return withinCityLimits;
	}

	public void setWithinCityLimits(boolean withinCityLimits) {
		this.withinCityLimits = withinCityLimits;
	}

	public int getMunicipalityCode() {
		return municipalityCode;
	}

	public void setMunicipalityCode(int municipalityCode) {
		this.municipalityCode = municipalityCode;
	}

	public boolean isDeliverable() {
		return deliverable;
	}

	public void setDeliverable(boolean deliverable) {
		this.deliverable = deliverable;
	}

	@Override public int hashCode() {
		return Objects.hashCode(id, street, additionalInfo, city, state, country, zipCode, county, withinCityLimits, municipalityCode, deliverable);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final Address other = (Address) obj;
		return Objects.equal(this.id, other.id) &&
			Objects.equal(this.street, other.street) &&
			Objects.equal(this.additionalInfo, other.additionalInfo) &&
			Objects.equal(this.city, other.city) &&
			Objects.equal(this.state, other.state) &&
			Objects.equal(this.country, other.country) &&
			Objects.equal(this.zipCode, other.zipCode) &&
			Objects.equal(this.county, other.county) &&
			Objects.equal(this.withinCityLimits, other.withinCityLimits) &&
			Objects.equal(this.municipalityCode, other.municipalityCode) &&
			Objects.equal(this.deliverable, other.deliverable);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("id", id)
			.add("street", street)
			.add("additionalInfo", additionalInfo)
			.add("city", city)
			.add("state", state)
			.add("country", country)
			.add("zipCode", zipCode)
			.add("county", county)
			.add("withinCityLimits", withinCityLimits)
			.add("municipalityCode", municipalityCode)
			.add("deliverable", deliverable)
			.toString();
	}
}