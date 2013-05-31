package com.alphatek.tylt.authority;

import com.alphatek.tylt.web.mvc.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * An implementation of {@link UserContext} that looks up the {@link com.alphatek.tylt.web.mvc.model.User} using the Spring Security's
 * {@link org.springframework.security.core.Authentication} by principal name.
 *
 * @author Rob Winch
 *
 */
@Component
public class DefaultUserContext implements UserContext {
    /**
     * Get the {@link com.alphatek.tylt.web.mvc.model.User} by obtaining the currently logged in Spring Security user's
     * {@link org.springframework.security.core.Authentication#getName()}
     */
    @Override public User getCurrentUser() {
      SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    if (authentication == null) {
		    return null;
	    }

	    return (User) authentication.getPrincipal();
    }

    @Override public void setCurrentUser(User user) {
	    Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
	    SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
