package com.alphatek.tylt.repository;

import com.alphatek.tylt.web.mvc.model.User;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * User: jason.dimeo
 * Date: 2013-05-29 : 7:41 PM
 */
@Repository
public class ExtendedUserDetailsManager extends JdbcUserDetailsManager {
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private static final String USER_QUERY = "SELECT id, username, password, email_address, first_name, last_name, enabled, account_non_expired, account_non_locked, credentials_non_expired, address_id FROM users WHERE ";
	private static final String GROUP_AUTHORITIES_BY_USERNAME_QUERY = "SELECT g.id, g.group_name, ga.authority FROM users u, groups g, group_members gm, group_authorities ga WHERE u.username = ? AND u.id = gm.user_id AND gm.group_id = g.id AND ga.group_id = g.id";
	private static final String ADD_USER_QUERY = "INSERT INTO users (username, password, email_address, first_name, last_name, enabled, account_non_expired, account_non_locked, credentials_non_expired, address_id) VALUES (:username, :password, :emailAddress, :firstName, :lastName, :enabled, :accountNonExpired, :accountNonLocked, :credentialsNonExpired, :addressId)";
	private static final RowMapper<UserDetails> USER_MAPPER = new UserRowMapper("users.");

	@Autowired public ExtendedUserDetailsManager(DataSource dataSource) {
		setDataSource(dataSource);
		setGroupAuthoritiesByUsernameQuery(GROUP_AUTHORITIES_BY_USERNAME_QUERY);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
	}

	@Override protected List<UserDetails> loadUsersByUsername(String username) {
		Preconditions.checkNotNull(username, "username cannot be null");

		return getJdbcTemplate().query(USER_QUERY + "username = ?", USER_MAPPER, username);
	}

	@Override public void createUser(UserDetails userDetails) {
		Preconditions.checkNotNull(userDetails, "userDetails cannot be null");

		User user = (User) userDetails;
		Preconditions.checkArgument(user.getId() == 0, "user.getId() must be 0 when creating a " + User.class.getName());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(ADD_USER_QUERY, new BeanPropertySqlParameterSource(user), keyHolder);
		keyHolder.getKey();
	}

	@Override protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();

		if (!isUsernameBasedPrimaryKey()) {
			returnUsername = username;
		}

		return ((User) userFromUserQuery).asNewBuilder()
			.username(returnUsername)
			.authorities(combinedAuthorities)
			.build();
	}

	//
//	@Cacheable("userCache")
//	@Transactional(readOnly = true)
//	@Override public User retrieveUser(int id) {
//		return (User) getJdbcTemplate().queryForObject(USER_QUERY + "id = ?", USER_MAPPER, id);
//	}
//
//	@Cacheable("userSearchCache")
//	@Transactional(readOnly = true)
//	@Override public User findByUsername(String username) {
//		Preconditions.checkNotNull(username, "username cannot be null");
//		List<UserDetails> users = getJdbcTemplate().query(USER_QUERY + "username = ?", USER_MAPPER, username);
//
//		return users.isEmpty() ? null : (User) Iterables.getOnlyElement(users);
//	}
//
//	@Override public int createUser(User userDetails) {
//		Preconditions.checkNotNull(userDetails, "userDetails cannot be null");
//		Preconditions.checkArgument(userDetails.getId() == 0, "userDetails.getId() must be 0 when creating a " + User.class.getName());
//
//		String sql = "INSERT INTO users (username, password, email_address, first_name, last_name, enabled, account_non_expired, account_non_locked, credentials_non_expired, address_id) VALUES (:username, :password, :emailAddress, :firstName, :lastName, :enabled, :accountNonExpired, :accountNonLocked, :credentialsNonExpired, :addressId)";
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		getJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(userDetails), keyHolder);
//
//		return keyHolder.getKey().intValue();
//	}

	static final class UserRowMapper implements RowMapper<UserDetails> {
		private final String columnLabelPrefix;

		public UserRowMapper(String columnLabelPrefix) {
			this.columnLabelPrefix = columnLabelPrefix;
		}

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			return User.newBuilder().id(rs.getInt(columnLabelPrefix + "id"))
				.username(rs.getString(columnLabelPrefix + "username"))
				.password(rs.getString(columnLabelPrefix + "password"))
				.emailAddress(rs.getString(columnLabelPrefix + "email_address"))
				.firstName(rs.getString(columnLabelPrefix + "first_name"))
				.lastName(rs.getString(columnLabelPrefix + "last_name"))
				.enabled(rs.getBoolean(columnLabelPrefix + "enabled"))
				.accountNonLocked(rs.getBoolean(columnLabelPrefix + "account_non_locked"))
				.accountNonExpired(rs.getBoolean(columnLabelPrefix + "account_non_expired"))
				.credentialsNonExpired(rs.getBoolean(columnLabelPrefix + "credentials_non_expired"))
				.build();
		}
	}
}