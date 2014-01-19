package com.alphatek.tylt.authority;

import com.google.common.base.Objects;

import java.util.Date;

/**
 * @author jason.dimeo
 * Created on 2014-01-09:3:05 PM.
 */
public class PersistentLogin {
	private final String username;
	private final String series;
	private final String token;
	private final long lastUsed;

	public PersistentLogin(String username, String series, String token, Date lastUsed) {
		this.username = username;
		this.series = series;
		this.token = token;
		this.lastUsed = lastUsed.getTime();
	}

	public String getUsername() {
		return username;
	}

	public String getSeries() {
		return series;
	}

	public String getToken() {
		return token;
	}

	public Date getLastUsed() {
		return new Date(lastUsed);
	}

	@Override public int hashCode() {
		return Objects.hashCode(username, series, token, lastUsed);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final PersistentLogin other = (PersistentLogin) obj;
		return Objects.equal(this.username, other.username) &&
			Objects.equal(this.series, other.series) &&
			Objects.equal(this.token, other.token) &&
			Objects.equal(this.lastUsed, other.lastUsed);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("username", username)
			.add("series", series)
			.add("token", token)
			.add("lastUsed", lastUsed)
			.toString();
	}
}
