package com.alphatek.tylt.authority;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author naquaduh
 * Created on 2013-12-21:7:21 PM.
 */
public enum AuthorityRole {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	private final String name;
	private AuthorityRole(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Set<AuthorityRole> getAuthorityRoles() {
		return AuthorityRolesHolder.AUTHORITY_ROLES;
	}

	public static AuthorityRole findByName(final String name) {
		return Iterables.find(AuthorityRolesHolder.AUTHORITY_ROLES, new Predicate<AuthorityRole>() {
			@Override public boolean apply(AuthorityRole authorityRole) {
				return authorityRole.name.equals(name);
			}
		}, null);
	}

	private static final class AuthorityRolesHolder {
		static final Set<AuthorityRole> AUTHORITY_ROLES = Sets.immutableEnumSet(EnumSet.allOf(AuthorityRole.class));
	}
}