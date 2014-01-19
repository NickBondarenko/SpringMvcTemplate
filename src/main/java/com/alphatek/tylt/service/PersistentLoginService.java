package com.alphatek.tylt.service;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

/**
 * @author naquaduh
 *         Created on 2014-01-09:3:53 PM.
 */
@Validated
public interface PersistentLoginService {
	void clearExpiredPersistentLogins();

	void clearUserPersistedLogin(@NotBlank String username);
}
