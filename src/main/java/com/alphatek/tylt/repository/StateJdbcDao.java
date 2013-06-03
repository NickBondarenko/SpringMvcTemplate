package com.alphatek.tylt.repository;

import com.alphatek.tylt.domain.CodeDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * User: jason.dimeo
 * Date: 2013-06-02 : 11:50 AM
 */
@Repository
public class StateJdbcDao extends AbstractJdbcDao implements StateDao {
	private static final String RETRIEVE_STATES_SQL = "SELECT abbreviation, description FROM state";
	private static final RowMapper<CodeDescription<String>> ROW_MAPPER = new RowMapper<CodeDescription<String>>() {
		@Override public CodeDescription<String> mapRow(ResultSet resultSet, int i) throws SQLException {
			return new CodeDescription<>(resultSet.getString("abbreviation"), resultSet.getString("description"));
		}
	};

	@Autowired public StateJdbcDao(DataSource dataSource) {
		super(dataSource);
	}

	@Cacheable("states")
	@Override public List<CodeDescription<String>> retrieveStateList() {
		return getJdbcTemplate().query(RETRIEVE_STATES_SQL, ROW_MAPPER);
	}

	@Override public CodeDescription<String> retrieveStateById(int id) {
		return getNamedParameterJdbcTemplate().queryForObject(RETRIEVE_STATES_SQL + " WHERE id = :id", new MapSqlParameterSource("id", id), ROW_MAPPER);
	}
}