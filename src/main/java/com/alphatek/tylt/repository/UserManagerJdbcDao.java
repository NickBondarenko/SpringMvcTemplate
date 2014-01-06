package com.alphatek.tylt.repository;

import com.alphatek.tylt.authority.AuthorityGroup;
import com.alphatek.tylt.web.servlet.mvc.model.User;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * User: jason.dimeo
 * Date: 2013-05-29 : 7:41 PM
 */
public class UserManagerJdbcDao extends JdbcUserDetailsManager implements UserManagerDao {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private static final boolean ENABLE_GROUPS = true;
	private static final boolean ENABLE_AUTHORITIES = false;
	private static final String FIND_USER_QUERY = "SELECT users.id, group_id, username, password, email_address, first_name, last_name, enabled, account_non_expired, account_non_locked, credentials_non_expired, address_id FROM users, group_members WHERE users.id = group_members.user_id AND ";
	private static final String GROUP_AUTHORITIES_BY_USERNAME_QUERY = "SELECT g.id, g.group_name, ga.authority FROM users u, groups g, group_members gm, group_authorities ga WHERE u.username = ? AND u.id = gm.user_id AND gm.group_id = g.id AND ga.group_id = g.id";
	private static final String ADD_USER_QUERY = "INSERT INTO users (username, password, email_address, first_name, last_name, enabled, account_non_expired, account_non_locked, credentials_non_expired, address_id) VALUES (:username, :password, :emailAddress, :firstName, :lastName, :enabled, :accountNonExpired, :accountNonLocked, :credentialsNonExpired, :address.id)";
	private static final String INSERT_GROUP_MEMBER_SQL = "INSERT INTO group_members (user_id, group_id) VALUES (:userId, :groupId)";
	private static final String FIND_GROUP_ID_SQL = "SELECT id FROM groups WHERE group_name = ?";
	private static final RowMapper<AuthorityGroup> AUTHORITY_GROUP_ROW_MAPPER = new RowMapper<AuthorityGroup>() {
		@Override public AuthorityGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AuthorityGroup.findById(rs.getLong("id"));
		}
	};
	private static final RowMapper<UserDetails> USER_MAPPER = new RowMapper<UserDetails>() {
		@Override public UserDetails mapRow(ResultSet resultSet, int index) throws SQLException {
			return User.newBuilder()
				.id(resultSet.getLong("id"))
				.groupId(resultSet.getLong("group_id"))
				.username(resultSet.getString("username"))
				.password(resultSet.getString("password"))
				.emailAddress(resultSet.getString("email_address"))
				.firstName(resultSet.getString("first_name"))
				.lastName(resultSet.getString("last_name"))
				.enabled(resultSet.getBoolean("enabled"))
				.accountNonLocked(resultSet.getBoolean("account_non_locked"))
				.accountNonExpired(resultSet.getBoolean("account_non_expired"))
				.credentialsNonExpired(resultSet.getBoolean("credentials_non_expired"))
				.authorities(AuthorityUtils.NO_AUTHORITIES)
				.build();
		}
	};

	@Autowired public UserManagerJdbcDao(DataSource dataSource) {
		setDataSource(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
		setGroupAuthoritiesByUsernameQuery(GROUP_AUTHORITIES_BY_USERNAME_QUERY);
		setFindGroupIdSql(FIND_GROUP_ID_SQL);
	  setInsertGroupMemberSql(INSERT_GROUP_MEMBER_SQL);
		setEnableGroups(ENABLE_GROUPS);
		setEnableAuthorities(ENABLE_AUTHORITIES);
	}

	@Transactional(readOnly = true)
	@Override protected List<UserDetails> loadUsersByUsername(String username) {
		Preconditions.checkNotNull(username, "Username cannot be null");

		return getJdbcTemplate().query(FIND_USER_QUERY + "username = ?", USER_MAPPER, username);
	}

	@Override public User createUser(User.Builder userBuilder) {
		Preconditions.checkNotNull(userBuilder, "User builder cannot be null");
		Preconditions.checkArgument(userBuilder.getId() == 0, "userBuilder.getId() must be 0 when creating a " + User.class.getName());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(ADD_USER_QUERY, new BeanPropertySqlParameterSource(userBuilder), keyHolder);
		userBuilder.id(keyHolder.getKey().longValue());
		return userBuilder.build();
	}

	@Override public void createUser(UserDetails userDetails) {
		Preconditions.checkNotNull(userDetails, "User details cannot be null");

		createUser(((User) userDetails).asNewBuilder());
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

		User user = (User) loadUserByUsername(username);
		addUserToGroup(user.getId(), user.getGroupId());
	}

	@Transactional(readOnly = true)
	@Override public AuthorityGroup findAuthorityGroupByName(String groupName) {
		Preconditions.checkNotNull(groupName, "Group name cannot be null");

		return getJdbcTemplate().queryForObject(FIND_GROUP_ID_SQL, AUTHORITY_GROUP_ROW_MAPPER, groupName);
	}

	@Override public void addUserToGroup(User user) {
		Preconditions.checkNotNull(user, "User cannot be null");
		Preconditions.checkNotNull(user.getGroupId(), "Group ID cannot be null");

		addUserToGroup(user.getId(), user.getGroupId());
	}

	private void addUserToGroup(long userId, long groupId) {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		sqlParameterSource.addValue("userId", userId);
		sqlParameterSource.addValue("groupId", groupId);
		namedParameterJdbcTemplate.update(INSERT_GROUP_MEMBER_SQL, sqlParameterSource);
	}

	@Override protected UserDetails createUserDetails(String username, UserDetails userDetails, List<GrantedAuthority> combinedAuthorities) {
		Preconditions.checkNotNull(username, "Username cannot be null");
		Preconditions.checkNotNull(userDetails, "UserDetails cannot be null");
		Preconditions.checkNotNull(combinedAuthorities, "GrantedAuthority list cannot be null");

		String returnUsername = isUsernameBasedPrimaryKey() ? userDetails.getUsername() : username;
		return ((User) userDetails).asNewBuilder().username(returnUsername).authorities(combinedAuthorities).build();
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