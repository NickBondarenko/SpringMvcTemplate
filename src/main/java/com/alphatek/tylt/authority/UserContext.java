package com.alphatek.tylt.authority;

import com.alphatek.tylt.web.mvc.model.User;

public interface UserContext {
	/**
	 * Gets the currently logged in {@link com.alphatek.tylt.web.mvc.model.User} or null if there is no authenticated user.
	 *
	 * @return
	 */
	User getCurrentUser();

	/**
	 * Sets the currently logged in {@link com.alphatek.tylt.web.mvc.model.User}.
	 *
	 * @param user the logged in {@link com.alphatek.tylt.web.mvc.model.User}. Cannot be null.
	 * @throws IllegalArgumentException if the {@link com.alphatek.tylt.web.mvc.model.User} is null.
	 */
	void setCurrentUser(User user);
}