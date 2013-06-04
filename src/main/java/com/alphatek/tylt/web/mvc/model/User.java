package com.alphatek.tylt.web.mvc.model;

import com.alphatek.tylt.web.mvc.model.validate.constraints.FieldMatch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collection;

@FieldMatch(field = "password", matches = "confirmPassword", message = "{com.alphatek.tylt.web.mvc.model.user.passwords.FieldMatch.message}")
public final class User implements Serializable, UserDetails {
	private long id;
	@NotEmpty(message = "{com.alphatek.tylt.web.mvc.model.user.firstName[NotEmpty.message]}")
	private String firstName;
	@NotEmpty(message = "{com.alphatek.tylt.web.mvc.model.user.lastName[NotEmpty.message]}")
	private String lastName;
	@NotEmpty(message = "{com.alphatek.tylt.web.mvc.model.user.username[NotEmpty.message]}")
	private String username;
	@NotEmpty(message = "{com.alphatek.tylt.web.mvc.model.user.password[NotEmpty.message]}")
	private String password;
	@NotEmpty(message = "{com.alphatek.tylt.web.mvc.model.user.confirmPassword[NotEmpty.message]}")
	private String confirmPassword;
	@NotEmpty(message = "{com.alphatek.tylt.web.mvc.model.user.emailAddress[NotEmpty.message]}")
	@Email(message = "{com.alphatek.tylt.web.mvc.model.user.emailAddress[Email.message]}")
	private String emailAddress;
	@Valid private Address address;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private Collection<? extends GrantedAuthority> authorities;

	public User() {}

	public User(User user) {
		id = user.id;
		firstName = user.firstName;
		lastName = user.lastName;
		username = user.username;
		password = user.password;
		confirmPassword = user.confirmPassword;
		emailAddress = user.emailAddress;
		address = user.address;
		accountNonExpired = user.accountNonExpired;
		accountNonLocked = user.accountNonLocked;
		credentialsNonExpired = user.credentialsNonExpired;
		enabled = user.enabled;
		authorities = user.authorities;
	}

	public static User newInstance() {
		return new User();
	}

	public static User newInstance(User user) {
		return new User(user);
	}

	@JsonIgnore
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	@Override public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	@Override public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
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

	@Override public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override public int hashCode() {
		return Objects.hashCode(id, firstName, lastName, username, password, confirmPassword, emailAddress, address, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, authorities);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final User other = (User) obj;
		return Objects.equal(this.id, other.id) &&
			Objects.equal(this.firstName, other.firstName) &&
			Objects.equal(this.lastName, other.lastName) &&
			Objects.equal(this.username, other.username) &&
			Objects.equal(this.password, other.password) &&
			Objects.equal(this.confirmPassword, other.confirmPassword) &&
			Objects.equal(this.emailAddress, other.emailAddress) &&
			Objects.equal(this.address, other.address) &&
			Objects.equal(this.accountNonExpired, other.accountNonExpired) &&
			Objects.equal(this.accountNonLocked, other.accountNonLocked) &&
			Objects.equal(this.credentialsNonExpired, other.credentialsNonExpired) &&
			Objects.equal(this.enabled, other.enabled) &&
			Objects.equal(this.authorities, other.authorities);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("id", id)
			.add("firstName", firstName)
			.add("lastName", lastName)
			.add("username", username)
			.add("password", password)
			.add("confirmPassword", confirmPassword)
			.add("emailAddress", emailAddress)
			.add("address", address)
			.add("accountNonExpired", accountNonExpired)
			.add("accountNonLocked", accountNonLocked)
			.add("credentialsNonExpired", credentialsNonExpired)
			.add("enabled", enabled)
			.add("authorities", authorities)
			.toString();
	}

}