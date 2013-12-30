package com.alphatek.tylt.repository;

import com.alphatek.tylt.authority.AuthorityGroup;
import com.alphatek.tylt.web.servlet.mvc.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;

/**
 * @author jason.dimeo
 *         Date: 4/28/13
 *         Time: 12:37 PM
 */
public interface UserManagerDao extends UserDetailsManager, GroupManager {
	User createUser(User.Builder userBuilder);

	long createUser(User user);

	List<GrantedAuthority> getCombinedAuthorities(String username);

	AuthorityGroup findAuthorityGroupByName(String groupName);

	void addUserToGroup(User user);
}