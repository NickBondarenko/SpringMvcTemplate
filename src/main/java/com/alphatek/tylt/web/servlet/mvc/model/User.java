package com.alphatek.tylt.web.servlet.mvc.model;

import com.alphatek.tylt.authority.AuthorityGroup;
import com.alphatek.tylt.domain.construct.Buildable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

public final class User implements Serializable, UserDetails, CredentialsContainer, Buildable<User.Builder> {
	private final long id;
	private final long groupId;
	private final String firstName;
	private final String lastName;
	private final String emailAddress;
	private final Address address;
	private final org.springframework.security.core.userdetails.User user;

	private User(Builder builder) {
		id = builder.id;
		groupId = builder.groupId;
		firstName = builder.firstName;
		lastName = builder.lastName;
		emailAddress = builder.emailAddress;
		address = builder.address;
		user = new org.springframework.security.core.userdetails.User(builder.username,
			builder.password,
			builder.enabled,
			builder.accountNonExpired,
			builder.credentialsNonExpired,
			builder.accountNonLocked,
			builder.authorities);
	}

	public static User.Builder newBuilder() {
		return new User.Builder();
	}

	public static User.Builder newBuilder(AuthorityGroup authorityGroup) {
		return new User.Builder(authorityGroup);
	}

	@Override public Builder asNewBuilder() {
		return new User.Builder(this);
	}

	@JsonIgnore
	public long getId() {
		return id;
	}

	@JsonIgnore
	public long getGroupId() {
		return groupId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@Override public String getUsername() {
		return user.getUsername();
	}

	@JsonIgnore
	@Override public String getPassword() {
		return user.getPassword();
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public Address getAddress() {
		return new Address(address);
	}

	@Override public boolean isAccountNonExpired() {
		return user.isAccountNonExpired();
	}

	@Override public boolean isAccountNonLocked() {
		return user.isAccountNonLocked();
	}

	@Override public boolean isCredentialsNonExpired() {
		return user.isCredentialsNonExpired();
	}

	@Override public boolean isEnabled() {
		return user.isEnabled();
	}

	@Override public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}

	@Override public void eraseCredentials() {
		user.eraseCredentials();
	}

	@Override public int hashCode() {
		return user.hashCode();
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final User other = (User) obj;
		return Objects.equal(this.user, other.user);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("id", id)
			.add("firstName", firstName)
			.add("lastName", lastName)
			.add("emailAddress", emailAddress)
			.add("address", address)
			.add("userDetails", user.toString())
			.toString();
	}

	public static final class Builder implements com.alphatek.tylt.domain.construct.Builder<User> {
		private long id;
		private long groupId;
		private String firstName;
		private String lastName;
		private String username;
		private String password;
		private String emailAddress;
		private Address address;
		private boolean accountNonExpired = true;
		private boolean accountNonLocked = true;
		private boolean credentialsNonExpired = true;
		private boolean enabled = true;
		private Collection<? extends GrantedAuthority> authorities;

		private Builder() {}

		private Builder(AuthorityGroup authorityGroup) {
			authorities = authorityGroup.getAuthorities();
			groupId = authorityGroup.getId();
		}

		private Builder(User copy) {
			id = copy.id;
			groupId = copy.groupId;
			firstName = copy.firstName;
			lastName = copy.lastName;
			username = copy.user.getUsername();
			password = copy.user.getPassword();
			emailAddress = copy.emailAddress;
			address = copy.address;
			accountNonExpired = copy.user.isAccountNonExpired();
			accountNonLocked = copy.user.isAccountNonLocked();
			credentialsNonExpired = copy.user.isCredentialsNonExpired();
			enabled = copy.user.isEnabled();
			authorities = copy.user.getAuthorities();
		}

		public Builder id(long id) {
			this.id = id;
			return this;
		}

		public Builder groupId(long groupId) {
			this.groupId = groupId;
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

		public Builder emailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
			return this;
		}

		public Builder address(Address address) {
			this.address = address;
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
			this.authorities = ImmutableList.copyOf(authorities);
			return this;
		}

		public long getId() {
			return id;
		}

		public long getGroupId() {
			return groupId;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public String getEmailAddress() {
			return emailAddress;
		}

		public Address getAddress() {
			return address;
		}

		public boolean isAccountNonExpired() {
			return accountNonExpired;
		}

		public boolean isAccountNonLocked() {
			return accountNonLocked;
		}

		public boolean isCredentialsNonExpired() {
			return credentialsNonExpired;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public Collection<? extends GrantedAuthority> getAuthorities() {
			return ImmutableList.copyOf(authorities);
		}

		@Override public User build() {
			return new User(this);
		}

		@Override public int hashCode() {
			return Objects.hashCode(id, groupId, firstName, lastName, username, password, emailAddress, address, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, authorities);
		}

		@Override public boolean equals(Object obj) {
			if (this == obj) { return true; }
			if (obj == null || getClass() != obj.getClass()) { return false; }
			final Builder other = (Builder) obj;
			return Objects.equal(this.id, other.id) &&
				Objects.equal(this.groupId, other.groupId) &&
				Objects.equal(this.firstName, other.firstName) &&
				Objects.equal(this.lastName, other.lastName) &&
				Objects.equal(this.username, other.username) &&
				Objects.equal(this.password, other.password) &&
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
				.add("groupId", groupId)
				.add("firstName", firstName)
				.add("lastName", lastName)
				.add("username", username)
				.add("password", "[PROTECTED]")
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
}