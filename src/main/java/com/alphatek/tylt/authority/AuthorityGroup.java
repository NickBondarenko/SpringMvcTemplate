package com.alphatek.tylt.authority;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author jason.dimeo
 * Created on 2013-12-21:05:02 PM.
 */
public enum AuthorityGroup {
	USER(1L, new AuthorityRole[] {AuthorityRole.USER}),
	ADMIN(2L, new AuthorityRole[] {AuthorityRole.USER, AuthorityRole.ADMIN});

	private final long id;
	private final Collection<? extends GrantedAuthority> authorities;

	private AuthorityGroup(long id, AuthorityRole[] authorityRoles) {
		this.id = id;

		Collection<GrantedAuthority> grantedAuthorities = Lists.newArrayListWithExpectedSize(authorityRoles.length);
		for (AuthorityRole authorityRole : authorityRoles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authorityRole.getName()));
		}
		this.authorities = grantedAuthorities;
	}

	public long getId() {
		return id;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public static AuthorityGroup findById(final long id) {
		return Iterables.find(AuthorityGroupsHolder.AUTHORITY_GROUPS, new Predicate<AuthorityGroup>() {
			@Override public boolean apply(AuthorityGroup authorityGroup) {
				return authorityGroup.id == id;
			}
		}, null);
	}

	public boolean hasRole(final AuthorityRole authorityRole) {
		return Iterables.any(authorities, new Predicate<GrantedAuthority>() {
			@Override public boolean apply(GrantedAuthority grantedAuthority) {
				return grantedAuthority.getAuthority().equals(authorityRole.getName());
			}
		});
	}

	private static final class AuthorityGroupsHolder {
		static final Set<AuthorityGroup> AUTHORITY_GROUPS = Sets.immutableEnumSet(EnumSet.allOf(AuthorityGroup.class));
	}
}