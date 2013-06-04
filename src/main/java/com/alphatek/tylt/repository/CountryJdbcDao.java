package com.alphatek.tylt.repository;

import com.alphatek.tylt.web.mvc.model.Country;
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
 * Date: 2013-06-03 : 10:18 PM
 */
@Repository
public class CountryJdbcDao extends AbstractJdbcDao implements CountryDao {
	private static final String RETRIEVE_COUNTRIES_SQL = "SELECT id, code, name FROM country ";
	private static final String COUNTRY_COUNT_SQL = "SELECT COUNT(*) FROM country";
	private static final RowMapper<Country> ROW_MAPPER = new RowMapper<Country>() {
		@Override public Country mapRow(ResultSet resultSet, int i) throws SQLException {
			Country country = new Country(resultSet.getString("code"), resultSet.getString("name"));
			country.setId(resultSet.getLong("id"));
			return country;
		}
	};

	@Autowired public CountryJdbcDao(DataSource dataSource) {
		super(dataSource);
	}

	@Cacheable("countries")
	@Transactional(readOnly = true)
	@Override public List<Country> retrieveCountryList() {
		return getJdbcTemplate().query(RETRIEVE_COUNTRIES_SQL, ROW_MAPPER);
	}

	@Cacheable("countryById")
	@Transactional(readOnly = true)
	@Override public Country retrieveById(long id) {
		return getNamedParameterJdbcTemplate().queryForObject(RETRIEVE_COUNTRIES_SQL + "WHERE id = :id", new MapSqlParameterSource("id", id), ROW_MAPPER);
	}

	@Cacheable("validCountry")
	@Transactional(readOnly = true)
	@Override public boolean isValidCountryCode(String code) {
		return getNamedParameterJdbcTemplate().queryForObject(COUNTRY_COUNT_SQL + " WHERE code = :code", new MapSqlParameterSource("code", code), Integer.class) > 0;
	}

}