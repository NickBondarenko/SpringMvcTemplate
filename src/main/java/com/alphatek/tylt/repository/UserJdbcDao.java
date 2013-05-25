package com.alphatek.tylt.repository;

import com.alphatek.tylt.web.mvc.model.User;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author jason.dimeo
 *         Date: 4/28/13
 *         Time: 12:38 PM
 */
@Repository("userDao")
public class UserJdbcDao extends AbstractJdbcDao implements UserDao {
	private static final String USER_QUERY = "SELECT id, username, password, email_address, first_name, last_name FROM users WHERE ";
	private static final RowMapper<User> USER_MAPPER = new UserRowMapper("users.");

	@Autowired public UserJdbcDao(DataSource dataSource) {
		super(dataSource);
	}

	@Cacheable("userCache")
	@Transactional(readOnly = true)
	@Override public User retrieveUser(int id) {
		return getJdbcTemplate().queryForObject(USER_QUERY + "id = ?", USER_MAPPER, id);
	}

	@Cacheable("userSearchCache")
	@Transactional(readOnly = true)
	@Override public User findByUsername(String username) {
		Preconditions.checkNotNull(username, "username cannot be null");
		List<User> users = getJdbcTemplate().query(USER_QUERY + "username = ?", USER_MAPPER, username);

		return users.isEmpty() ? null : Iterables.getOnlyElement(users);
	}

	@Override public int createUser(User user) {
		Preconditions.checkNotNull(user, "user cannot be null");
		Preconditions.checkArgument(user.getId() == 0, "user.getId() must be 0 when creating a " + User.class.getName());

		String sql = "INSERT INTO users (username, password, email_address, first_name, last_name) VALUES (:username, :password, :emailAddress, :firstName, :lastName)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(user), keyHolder);
		return keyHolder.getKey().intValue();
	}

	static final class UserRowMapper implements RowMapper<User> {
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
				.build();
		}
	}
}