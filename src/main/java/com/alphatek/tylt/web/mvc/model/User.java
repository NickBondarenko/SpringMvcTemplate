package com.alphatek.tylt.web.mvc.model;

import com.alphatek.tylt.domain.construct.Buildable;
import com.alphatek.tylt.web.mvc.model.validate.constraints.FieldMatch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import java.util.Collection;

public final class User implements Buildable<User.Builder>, UserDetails {
	private final long id;
	private final String firstName;
	private final String lastName;
	private final String username;
	private final String password;
	private final String confirmPassword;
	private final String emailAddress;
	private final Address address;
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;
	private final boolean enabled;
	private final Collection<? extends GrantedAuthority> authorities;

	private User(Builder builder) {
		id = builder.id;
		firstName = builder.firstName;
		lastName = builder.lastName;
		username = builder.username;
		password = builder.password;
		confirmPassword = builder.confirmPassword;
		emailAddress = builder.emailAddress;
		address = builder.address.build();
		accountNonExpired = builder.accountNonExpired;
		accountNonLocked = builder.accountNonLocked;
		credentialsNonExpired = builder.credentialsNonExpired;
		enabled = builder.enabled;
		authorities = builder.authorities;
	}

	@Override public Builder asNewBuilder() {
		return new Builder(this);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	@FieldMatch(field = "password", matches = "confirmPassword", message = "{com.alphatek.tylt.web.mvc.model.user.passwords.FieldMatch.message}")
	public static final class Builder implements com.alphatek.tylt.domain.construct.Builder<User> {
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
		@Valid private Address.Builder address = Address.newBuilder();
		private boolean accountNonExpired;
		private boolean accountNonLocked;
		private boolean credentialsNonExpired;
		private boolean enabled;
		private Collection<? extends GrantedAuthority> authorities;

		private Builder() {}

		private Builder(User user) {
			id = user.id;
			firstName = user.firstName;
			lastName = user.lastName;
			username = user.username;
			password = user.password;
			confirmPassword = user.confirmPassword;
			emailAddress = user.emailAddress;
			address = user.address.asNewBuilder();
			enabled = user.enabled;
			accountNonExpired = user.accountNonExpired;
			accountNonLocked = user.accountNonLocked;
			credentialsNonExpired = user.credentialsNonExpired;
			authorities = user.authorities;
		}

		public Builder id(long id) {
			this.id = id;
			return this;
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public Builder confirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
			return this;
		}

		public Builder emailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
			return this;
		}

		public Builder address(Address.Builder addressBuilder) {
			this.address = addressBuilder;
			return this;
		}

		public Builder accountNonExpired(boolean accountNonExpired) {
			this.accountNonExpired = accountNonExpired;
			return this;
		}

		public Builder accountNonLocked(boolean accountNonLocked) {
			this.accountNonLocked = accountNonLocked;
			return this;
		}

		public Builder credentialsNonExpired(boolean credentialsNonExpired) {
			this.credentialsNonExpired = credentialsNonExpired;
			return this;
		}

		public Builder enabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public Builder authorities(Collection<? extends GrantedAuthority> authorities) {
			this.authorities = authorities;
			return this;
		}

		/** JavaBeans Getters/Setters **/
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

		public Address.Builder getAddress() {
			return address;
		}

		public void setAddress(Address.Builder address) {
			this.address = address;
		}

		public boolean isAccountNonExpired() {
			return accountNonExpired;
		}

		public void setAccountNonExpired(boolean accountNonExpired) {
			this.accountNonExpired = accountNonExpired;
		}

		public boolean isAccountNonLocked() {
			return accountNonLocked;
		}

		public void setAccountNonLocked(boolean accountNonLocked) {
			this.accountNonLocked = accountNonLocked;
		}

		public boolean isCredentialsNonExpired() {
			return credentialsNonExpired;
		}

		public void setCredentialsNonExpired(boolean credentialsNonExpired) {
			this.credentialsNonExpired = credentialsNonExpired;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}

		public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
			this.authorities = authorities;
		}

		@Override	public User build() {
			return new User(this);
		}

		@Override public int hashCode() {
			return Objects.hashCode(id, firstName, lastName, username, password, confirmPassword, emailAddress, address, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled);
		}

		@Override public boolean equals(Object obj) {
			if (this == obj) { return true; }
			if (obj == null || getClass() != obj.getClass()) { return false; }
			final Builder other = (Builder) obj;
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
				Objects.equal(this.enabled, other.enabled);
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
				.toString();
		}

	}

	@JsonIgnore	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@Override public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override public String getPassword() {
		return password;
	}

	@JsonIgnore
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public Address getAddress() {
		return address;
	}

	@Override public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override public boolean isEnabled() {
		return enabled;
	}

	@Override public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
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