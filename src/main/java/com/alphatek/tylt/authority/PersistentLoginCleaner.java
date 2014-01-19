package com.alphatek.tylt.authority;

import com.alphatek.tylt.service.PersistentLoginService;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author naquaduh
 * Created on 2014-01-09:7:29 PM.
 */
@Component
public class PersistentLoginCleaner implements Runnable {
	private final PersistentLoginService persistentLoginService;

	@Autowired public PersistentLoginCleaner(PersistentLoginService persistentLoginService) {
		this.persistentLoginService = persistentLoginService;
	}

	@Override public void run() {
		persistentLoginService.clearExpiredPersistentLogins();
	}

	@Override public int hashCode() {
		return Objects.hashCode(persistentLoginService);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final PersistentLoginCleaner other = (PersistentLoginCleaner) obj;
		return Objects.equal(this.persistentLoginService, other.persistentLoginService);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("persistentLoginService", persistentLoginService)
			.toString();
	}
}
