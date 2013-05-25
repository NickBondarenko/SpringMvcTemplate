package com.alphatek.tylt.authority;

import com.alphatek.tylt.web.mvc.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.List;

public final class UserAuthorityUtils {
  private static final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
  private static final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");

	private UserAuthorityUtils() {}

  public static Collection<? extends GrantedAuthority> createAuthorities(User user) {
    String username = user.getUsername();
    if (username.startsWith("admin")) {
      return ADMIN_ROLES;
    }
    return USER_ROLES;
  }
}