package com.alphatek.tylt.repository;

import com.alphatek.tylt.authority.PersistentLogin;

import java.util.Date;
import java.util.List;

/**
 * @author naquaduh
 *         Created on 2014-01-08:12:06 PM.
 */
public interface PersistentLoginDao {
	void deleteExpiredLogins(Date expirationDate);

	List<PersistentLogin> retrieveAllPersistentLogins();

	void deleteUserPersistentLogin(String username);
}
