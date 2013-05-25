package com.alphatek.tylt.repository;

import com.alphatek.tylt.web.mvc.model.User;

/**
 * @author jason.dimeo
 *         Date: 4/28/13
 *         Time: 12:37 PM
 */
public interface UserDao {
	User retrieveUser(int id);
	User findByUsername(String username);
	int createUser(User user);
}
