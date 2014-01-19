package com.alphatek.tylt.repository;

import com.alphatek.tylt.authority.PersistentLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author naquaduh
 *         Created on 2014-01-09:3:27 PM.
 */
@Repository
public class PersistentLoginJdbcDao extends AbstractJdbcDao implements PersistentLoginDao {
	private static final String RETRIEVE_ALL_SQL = "SELECT * FROM persistent_logins";
	private static final String DELETE_EXPIRED_SQL = "DELETE FROM persistent_logins WHERE last_used <= ?";
	private static final String DELETE_USER_LOGIN_SQL = "DELETE FROM persistent_logins WHERE username = ?";
	private static final RowMapper<PersistentLogin> ROW_MAPPER = new RowMapper<PersistentLogin>() {
		@Override public PersistentLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new PersistentLogin(rs.getString("username"), rs.getString("series"), rs.getString("token"), rs.getDate("last_used"));
		}
	};

	@Autowired public PersistentLoginJdbcDao(DataSource dataSource) {
		super(dataSource);
	}

	@Override public void deleteExpiredLogins(Date expirationDate) {
		getJdbcTemplate().update(DELETE_EXPIRED_SQL, expirationDate);
	}

	@Transactional(readOnly = true)
	@Override public List<PersistentLogin> retrieveAllPersistentLogins() {
		return getJdbcTemplate().query(RETRIEVE_ALL_SQL, ROW_MAPPER);
	}

	@Override public void deleteUserPersistentLogin(String username) {
		getJdbcTemplate().update(DELETE_USER_LOGIN_SQL, username);
	}
}
