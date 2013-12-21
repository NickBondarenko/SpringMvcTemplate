package com.alphatek.tylt.repository;

import com.alphatek.tylt.web.servlet.mvc.model.User;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * User: jason.dimeo
 * Date: 2013-05-29 : 7:41 PM
 */
public class UserDetailsManagerJdbcDao extends JdbcUserDetailsManager implements UserDetailsManagerDao {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private static final String FIND_USER_QUERY = "SELECT id, username, password, email_address, first_name, last_name, enabled, account_non_expired, account_non_locked, credentials_non_expired, address_id FROM users WHERE ";
	private static final String GROUP_AUTHORITIES_BY_USERNAME_QUERY = "SELECT g.id, g.group_name, ga.authority FROM users u, groups g, group_members gm, group_authorities ga WHERE u.username = ? AND u.id = gm.user_id AND gm.group_id = g.id AND ga.group_id = g.id";
	private static final String ADD_USER_QUERY = "INSERT INTO users (username, password, email_address, first_name, last_name, enabled, account_non_expired, account_non_locked, credentials_non_expired, address_id) VALUES (:username, :password, :emailAddress, :firstName, :lastName, :enabled, :accountNonExpired, :accountNonLocked, :credentialsNonExpired, :address.id)";
	private static final String INSERT_GROUP_MEMBER_SQL = "INSERT INTO group_members (user_id, group_id) VALUES (:userId, :groupId)";
	private static final String FIND_GROUP_ID_SQL = "SELECT id FROM groups WHERE group_name = ?";
	private static final RowMapper<UserDetails> USER_MAPPER = new RowMapper<UserDetails>() {
		@Override public UserDetails mapRow(ResultSet resultSet, int index) throws SQLException {
			User user = new User();
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setEmailAddress(resultSet.getString("email_address"));
			user.setFirstName(resultSet.getString("first_name"));
			user.setLastName(resultSet.getString("last_name"));
			user.setEnabled(resultSet.getBoolean("enabled"));
			user.setAccountNonLocked(resultSet.getBoolean("account_non_locked"));
			user.setAccountNonExpired(resultSet.getBoolean("account_non_expired"));
			user.setCredentialsNonExpired(resultSet.getBoolean("credentials_non_expired"));

			return user;
		}
	};

	@Autowired public UserDetailsManagerJdbcDao(DataSource dataSource) {
		setDataSource(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
		setGroupAuthoritiesByUsernameQuery(GROUP_AUTHORITIES_BY_USERNAME_QUERY);
		setFindGroupIdSql(FIND_GROUP_ID_SQL);
	  setInsertGroupMemberSql(INSERT_GROUP_MEMBER_SQL);
	}

	@Transactional(readOnly = true)
	@Override protected List<UserDetails> loadUsersByUsername(String username) {
		Preconditions.checkNotNull(username, "Username cannot be null");

		return getJdbcTemplate().query(FIND_USER_QUERY + "username = ?", USER_MAPPER, username);
	}

	@Override public void createUser(UserDetails userDetails) {
		createUser((User) userDetails);
	}

	@Override public long createUser(User user) {
		Preconditions.checkNotNull(user, "User cannot be null");
		Preconditions.checkArgument(user.getId() == 0, "user.getId() must be 0 when creating a " + User.class.getName());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(ADD_USER_QUERY, new BeanPropertySqlParameterSource(user), keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override public void addUserToGroup(String username, String groupName) {
		Preconditions.checkNotNull(username, "Username cannot be null");
		Preconditions.checkNotNull(groupName, "Group name cannot be null");

		User user = (User) getJdbcTemplate().queryForObject(FIND_USER_QUERY, USER_MAPPER, username);
		long groupId = getJdbcTemplate().queryForObject(FIND_GROUP_ID_SQL, Long.class, groupName);
		addUserToGroup(user, groupId);
	}

	@Override public void addUserToGroup(final User user, final long groupId) {
		Preconditions.checkNotNull(user, "User cannot be null");
		Preconditions.checkArgument(groupId > 0, "Invalid group id: %s. Value must be > 0", groupId);

		SqlParameterSource sqlParameterSource = new MapSqlParameterSource(new HashMap<String, Long>() {{
			put("userId", user.getId());
			put("groupId", groupId);
		}});
		namedParameterJdbcTemplate.update(INSERT_GROUP_MEMBER_SQL, sqlParameterSource);
	}

	@Override protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();

		if (!isUsernameBasedPrimaryKey()) {
			returnUsername = username;
		}

		User user = (User) userFromUserQuery;
		user.setUsername(returnUsername);
		user.setAuthorities(combinedAuthorities);

		return user;
	}

	@Override public List<GrantedAuthority> getCombinedAuthorities(String username) {
		List<GrantedAuthority> combinedAuthorities = Lists.newArrayList();

		if (getEnableAuthorities()) {
			combinedAuthorities.addAll(loadUserAuthorities(username));
		}

		if (getEnableGroups()) {
			combinedAuthorities.addAll(loadGroupAuthorities(username));
		}

		addCustomAuthorities(username, combinedAuthorities);

		return combinedAuthorities;
	}
}