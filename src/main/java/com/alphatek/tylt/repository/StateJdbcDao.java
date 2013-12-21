package com.alphatek.tylt.repository;

import com.alphatek.tylt.web.servlet.mvc.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	private static final String RETRIEVE_STATES_SQL = "SELECT abbreviation, name FROM state";
	private static final String COUNT_STATES_SQL = "SELECT COUNT(*) FROM state";
	private static final RowMapper<State> ROW_MAPPER = new RowMapper<State>() {
		@Override public State mapRow(ResultSet resultSet, int i) throws SQLException {
			return new State(resultSet.getString("abbreviation"), resultSet.getString("name"));
		}
	};

	@Autowired public StateJdbcDao(DataSource dataSource) {
		super(dataSource);
	}

	@Cacheable("states")
	@Transactional(readOnly = true)
	@Override public List<State> retrieveStateList() {
		return getJdbcTemplate().query(RETRIEVE_STATES_SQL, ROW_MAPPER);
	}

	@Cacheable("validState")
	@Transactional(readOnly = true)
	@Override public boolean isValidStateAbbreviation(String abbreviation) {
		return getNamedParameterJdbcTemplate().queryForObject(COUNT_STATES_SQL + " WHERE abbreviation = :abbreviation", new MapSqlParameterSource("abbreviation", abbreviation), Integer.class) > 0;
	}

	@Cacheable("stateByAbbreviation")
	@Transactional(readOnly = true)
	@Override public State retrieveByAbbreviation(String abbreviation) {
		return getNamedParameterJdbcTemplate().queryForObject(RETRIEVE_STATES_SQL + " WHERE abbreviation = :abbreviation", new MapSqlParameterSource("abbreviation", abbreviation), ROW_MAPPER);
	}

	@Cacheable("state")
	@Transactional(readOnly = true)
	@Override public State retrieveStateById(int id) {
		return getNamedParameterJdbcTemplate().queryForObject(RETRIEVE_STATES_SQL + " WHERE id = :id", new MapSqlParameterSource("id", id), ROW_MAPPER);
	}
}