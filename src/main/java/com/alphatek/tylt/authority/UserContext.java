package com.alphatek.tylt.authority;

import com.alphatek.tylt.web.mvc.model.User;

/**
 * Manages the current {@link User}. This demonstrates how in larger applications it is good to abstract out
 * accessing the current user to return the application specific user rather than interacting with Spring Security
 * classes directly.
 *
 * @author jason.dimeo
 *
 */
public interface UserContext {
    /**
     * Gets the currently logged in {@link com.alphatek.tylt.web.mvc.model.User} or null if there is no authenticated user.
     *
     * @return
     */
    User getCurrentUser();

    /**
     * Sets the currently logged in {@link com.alphatek.tylt.web.mvc.model.User}.
     * @param user the logged in {@link com.alphatek.tylt.web.mvc.model.User}. Cannot be null.
     * @throws IllegalArgumentException if the {@link com.alphatek.tylt.web.mvc.model.User} is null.
     */
    void setCurrentUser(User user);
}
