package com.alphatek.tylt.web.servlet.mvc.model;

import com.alphatek.tylt.web.servlet.mvc.model.validate.constraints.FieldMatch;
import com.alphatek.tylt.web.servlet.mvc.model.validate.constraints.Username;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

/**
 * @author naquaduh
 *         Created on 2013-12-26:9:09 PM.
 */
@FieldMatch(field = "password", matches = "confirmPassword", message = "{com.alphatek.tylt.web.servlet.mvc.model.user.passwords.FieldMatch.message}")
public class RegistrationForm {
	private static final String PACKAGE_NAME = "com.alphatek.tylt.web.servlet.mvc.model.user.";

	@NotEmpty(message = "{" + PACKAGE_NAME + "firstName[NotEmpty.message]}")
	private String firstName;
	@NotEmpty(message = "{" + PACKAGE_NAME + "lastName[NotEmpty.message]}")
	private String lastName;
	@NotEmpty(message = "{" + PACKAGE_NAME + "username[NotEmpty.message]}")
	@Username
	private String username;
	@NotEmpty(message = "{" + PACKAGE_NAME + "password[NotEmpty.message]}")
	private String password;
	@NotEmpty(message = "{" + PACKAGE_NAME + "confirmPassword[NotEmpty.message]}")
	private String confirmPassword;
	@NotEmpty(message = "{" + PACKAGE_NAME + "emailAddress[NotEmpty.message]}")
	@Email(message = "{" + PACKAGE_NAME + "emailAddress[Email.message]}")
	private String emailAddress;
	@Valid
	private Address address;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override public int hashCode() {
		return Objects.hashCode(firstName, lastName, username, password, confirmPassword, emailAddress, address);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final RegistrationForm other = (RegistrationForm) obj;
		return Objects.equal(this.firstName, other.firstName) &&
			Objects.equal(this.lastName, other.lastName) &&
			Objects.equal(this.username, other.username) &&
			Objects.equal(this.password, other.password) &&
			Objects.equal(this.confirmPassword, other.confirmPassword) &&
			Objects.equal(this.emailAddress, other.emailAddress) &&
			Objects.equal(this.address, other.address);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("firstName", firstName)
			.add("lastName", lastName)
			.add("username", username)
			.add("password", password)
			.add("confirmPassword", confirmPassword)
			.add("emailAddress", emailAddress)
			.add("address", address)
			.toString();
	}
}