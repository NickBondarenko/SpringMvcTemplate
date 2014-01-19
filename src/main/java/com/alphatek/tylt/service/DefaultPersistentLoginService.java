package com.alphatek.tylt.service;

import com.alphatek.tylt.repository.PersistentLoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author naquaduh
 * Created on 2014-01-09:5:39 PM.
 */
@Service
public class DefaultPersistentLoginService implements PersistentLoginService {
	private final long tokenValiditySeconds;
	private final PersistentLoginDao persistentLoginDao;


	@Autowired public DefaultPersistentLoginService(PersistentLoginDao persistentLoginDao, @Value("${token.validity.seconds}") long tokenValiditySeconds) {
		this.persistentLoginDao = persistentLoginDao;
		this.tokenValiditySeconds = tokenValiditySeconds;
	}

	@Override public void clearExpiredPersistentLogins() {
		persistentLoginDao.deleteExpiredLogins(new Date(System.currentTimeMillis() - (tokenValiditySeconds * 1000)));
	}

	@Override public void clearUserPersistedLogin(String username) {
		persistentLoginDao.deleteUserPersistentLogin(username);
	}
}
