package com.alphatek.tylt.web.mvc.model;

import com.alphatek.tylt.domain.CodeDescription;
import com.alphatek.tylt.domain.construct.Buildable;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * The type Address.
 */
public final class Address implements Buildable<Address.Builder> {
	private final int id;
	@NotEmpty(message = "{com.alphatek.tylt.web.mvc.model.Address.street[NotEmpty.message]}")
	private final String street;
	private final String additionalInfo;
	@NotEmpty(message = "{com.alphatek.tylt.web.mvc.model.Address.city[NotEmpty.message]}")
	private final String city;
	@NotNull(message = "{com.alphatek.tylt.web.mvc.model.Address.state[NotNull.message]}")
	private final State state;
	private final CodeDescription<String> country;
	@NotNull(message = "{com.alphatek.tylt.web.mvc.model.Address.zipCode[NotNull.message]}")
	@Valid private final ZipCode zipCode;
	private final String county;
	private final boolean isWithinCityLimits;
	private final int municipalityCode;
	private final boolean isClean;

	private Address(Builder builder) {
		id = builder.id;
		street = builder.street;
		additionalInfo = builder.additionalInfo;
		city = builder.city;
		state = builder.state;
		country = builder.country;
		zipCode = builder.zipCode;
		county = builder.county;
		isWithinCityLimits = builder.isWithinCityLimits;
		municipalityCode = builder.municipalityCode;
		isClean = builder.isClean;
	}

	/**
	 * New builder.
	 *
	 * @return the builder
	 */
	public static Builder newBuilder() {
		return new Builder();
	}

	/**
	 * As new builder.
	 *
	 * @return the builder
	 */
	@Override public Builder asNewBuilder() {
	  return new Builder(this);
  }

	/**
	 * The type Builder.
	 */
	public static final class Builder implements com.alphatek.tylt.domain.construct.Builder<Address> {
		private int id;
		@NotEmpty(message = "{com.alphatek.tylt.web.mvc.model.Address.street[NotEmpty.message]}")
		private String street;
		private String additionalInfo;
		@NotEmpty(message = "{com.alphatek.tylt.web.mvc.model.Address.city[NotEmpty.message]}")
		private String city;
		@NotNull(message = "{com.alphatek.tylt.web.mvc.model.Address.state[NotNull.message]}")
		private State state;
		private CodeDescription<String> country;
		@NotNull(message = "{com.alphatek.tylt.web.mvc.model.Address.zipCode[NotNull.message]}")
		@Valid private ZipCode zipCode;
		private String county;
		private boolean isWithinCityLimits;
		private int municipalityCode;
		private boolean isClean;

		private Builder() {}

		private Builder(Address address) {
			id = address.id;
			street = address.street;
			additionalInfo = address.additionalInfo;
			city = address.city;
			state = address.state;
			country = address.country;
			zipCode = address.zipCode;
			county = address.county;
			isWithinCityLimits = address.isWithinCityLimits;
			municipalityCode = address.municipalityCode;
			isClean = address.isClean;
		}

		/**
		 * Id builder.
		 *
		 * @param id the id
		 * @return the builder
		 */
		public Builder id(int id) {
			this.id = id;
			return this;
		}

		/**
		 * Street builder.
		 *
		 * @param street the street
		 * @return the builder
		 */
		public Builder street(String street) {
			this.street = street;
			return this;
		}

		/**
		 * Additional info.
		 *
		 * @param additionalInfo the additional info
		 * @return the builder
		 */
		public Builder additionalInfo(String additionalInfo) {
			this.additionalInfo = additionalInfo;
			return this;
		}

		/**
		 * City builder.
		 *
		 * @param cityName the city name
		 * @return the builder
		 */
		public Builder city(String cityName) {
			this.city = cityName;
			return this;
		}

		/**
		 * State builder.
		 *
		 * @param state the state
		 * @return the builder
		 */
		public Builder state(State state) {
			this.state = state;
			return this;
		}

		/**
		 * Country builder.
		 *
		 * @param country the country
		 * @return the builder
		 */
		public Builder country(CodeDescription<String> country) {
			this.country = country;
			return this;
		}

		/**
		 * Zip code.
		 *
		 * @param zipCode the zip code
		 * @return the builder
		 */
		public Builder zipCode(ZipCode zipCode) {
			this.zipCode = zipCode;
			return this;
		}

		/**
		 * County builder.
		 *
		 * @param county the county
		 * @return the builder
		 */
		public Builder county(String county) {
			this.county = county;
			return this;
		}

		/**
		 * Within city limits.
		 *
		 * @param isWithinCityLimits the is within city limits
		 * @return the builder
		 */
		public Builder withinCityLimits(boolean isWithinCityLimits) {
			this.isWithinCityLimits = isWithinCityLimits;
			return this;
		}

		/**
		 * Municipality code.
		 *
		 * @param municipalityCode the municipality code
		 * @return the builder
		 */
		public Builder municipalityCode(int municipalityCode) {
			this.municipalityCode = municipalityCode;
			return this;
		}

		/**
		 * Is clean.
		 *
		 * @param isClean the is clean
		 * @return the builder
		 */
		public Builder isClean(boolean isClean) {
			this.isClean = isClean;
			return this;
		}

		/* Standard JavaBean Setters */

		/**
		 * Gets id.
		 *
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * Sets id.
		 *
		 * @param id the id
		 */
		public void setId(int id) {
			this.id = id;
		}

		/**
		 * Gets street.
		 *
		 * @return the street
		 */
		public String getStreet() {
			return street;
		}

		/**
		 * Sets street.
		 *
		 * @param street the street
		 */
		public void setStreet(String street) {
			this.street = street;
		}

		/**
		 * Gets additional info.
		 *
		 * @return the additional info
		 */
		public String getAdditionalInfo() {
			return additionalInfo;
		}

		/**
		 * Sets additional info.
		 *
		 * @param additionalInfo the additional info
		 */
		public void setAdditionalInfo(String additionalInfo) {
			this.additionalInfo = additionalInfo;
		}

		/**
		 * Gets city.
		 *
		 * @return the city
		 */
		public String getCity() {
			return city;
		}

		/**
		 * Sets city.
		 *
		 * @param city the city
		 */
		public void setCity(String city) {
			this.city = city;
		}

		/**
		 * Gets state.
		 *
		 * @return the state
		 */
		public State getState() {
			return state;
		}

		/**
		 * Sets state.
		 *
		 * @param state the state
		 */
		public void setState(State state) {
			this.state = state;
		}

		/**
		 * Gets country.
		 *
		 * @return the country
		 */
		public CodeDescription<String> getCountry() {
			return country;
		}

		/**
		 * Sets country.
		 *
		 * @param country the country
		 */
		public void setCountry(CodeDescription<String> country) {
			this.country = country;
		}

		/**
		 * Gets county.
		 *
		 * @return the county
		 */
		public String getCounty() {
			return county;
		}

		/**
		 * Gets zip code.
		 *
		 * @return the zip code
		 */
		public ZipCode getZipCode() {
			return zipCode;
		}

		/**
		 * Sets zip code.
		 *
		 * @param zipCode the zip code
		 */
		public void setZipCode(ZipCode zipCode) {
			this.zipCode = zipCode;
		}

		/**
		 * Sets county.
		 *
		 * @param county the county
		 */
		public void setCounty(String county) {
			this.county = county;
		}

		/**
		 * Is within city limits.
		 *
		 * @return the boolean
		 */
		public boolean isWithinCityLimits() {
			return isWithinCityLimits;
		}

		/**
		 * Sets within city limits.
		 *
		 * @param withinCityLimits the within city limits
		 */
		public void setWithinCityLimits(boolean withinCityLimits) {
			isWithinCityLimits = withinCityLimits;
		}

		/**
		 * Gets municipality code.
		 *
		 * @return the municipality code
		 */
		public int getMunicipalityCode() {
			return municipalityCode;
		}

		/**
		 * Sets municipality code.
		 *
		 * @param municipalityCode the municipality code
		 */
		public void setMunicipalityCode(int municipalityCode) {
			this.municipalityCode = municipalityCode;
		}

		/**
		 * Is clean.
		 *
		 * @return the boolean
		 */
		public boolean isClean() {
			return isClean;
		}

		/**
		 * Sets clean.
		 *
		 * @param clean the clean
		 */
		public void setClean(boolean clean) {
			isClean = clean;
		}

		/**
		 * Build address.
		 *
		 * @return the address
		 */
		@Override	public Address build() {
			return new Address(this);
		}

		/**
		 * Hash code.
		 *
		 * @return the int
		 */
		@Override public int hashCode() {
			return Objects.hashCode(id, street, additionalInfo, city, state, country, zipCode, county, isWithinCityLimits, municipalityCode, isClean);
		}

		/**
		 * Equals boolean.
		 *
		 * @param obj the obj
		 * @return the boolean
		 */
		@Override public boolean equals(Object obj) {
			if (this == obj) { return true; }
			if (obj == null || getClass() != obj.getClass()) { return false; }
			final Builder other = (Builder) obj;
			return Objects.equal(this.id, other.id) &&
				Objects.equal(this.street, other.street) &&
				Objects.equal(this.additionalInfo, other.additionalInfo) &&
				Objects.equal(this.city, other.city) &&
				Objects.equal(this.state, other.state) &&
				Objects.equal(this.country, other.country) &&
				Objects.equal(this.zipCode, other.zipCode) &&
				Objects.equal(this.county, other.county) &&
				Objects.equal(this.isWithinCityLimits, other.isWithinCityLimits) &&
				Objects.equal(this.municipalityCode, other.municipalityCode) &&
				Objects.equal(this.isClean, other.isClean);
		}

		/**
		 * To string.
		 *
		 * @return the string
		 */
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
				.add("isWithinCityLimits", isWithinCityLimits)
				.add("municipalityCode", municipalityCode)
				.add("isClean", isClean)
				.toString();
		}
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Gets additional info.
	 *
	 * @return the additional info
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	/**
	 * Gets city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Gets state.
	 *
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * Gets country.
	 *
	 * @return the country
	 */
	public CodeDescription<String> getCountry() {
		return country;
	}

	/**
	 * Gets zip code.
	 *
	 * @return the zip code
	 */
	public ZipCode getZipCode() {
		return zipCode;
	}

	/**
	 * Gets county.
	 *
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * Is within city limits.
	 *
	 * @return the boolean
	 */
	public boolean isWithinCityLimits() {
		return isWithinCityLimits;
	}

	/**
	 * Gets municipality code.
	 *
	 * @return the municipality code
	 */
	public int getMunicipalityCode() {
		return municipalityCode;
	}

	/**
	 * Is clean.
	 *
	 * @return the boolean
	 */
	public boolean isClean() {
		return isClean;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override public int hashCode() {
		return Objects.hashCode(id, street, additionalInfo, city, state, country, zipCode, county, isWithinCityLimits, municipalityCode, isClean);
	}

	/**
	 * Equals boolean.
	 *
	 * @param obj the obj
	 * @return the boolean
	 */
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
			Objects.equal(this.isWithinCityLimits, other.isWithinCityLimits) &&
			Objects.equal(this.municipalityCode, other.municipalityCode) &&
			Objects.equal(this.isClean, other.isClean);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
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
		.add("isWithinCityLimits", isWithinCityLimits)
		.add("municipalityCode", municipalityCode)
		.add("isClean", isClean)
		.toString();
	}

}