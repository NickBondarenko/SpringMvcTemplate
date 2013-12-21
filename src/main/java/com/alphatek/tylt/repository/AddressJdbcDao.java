package com.alphatek.tylt.repository;

import com.alphatek.tylt.web.servlet.mvc.model.Address;
import com.alphatek.tylt.web.servlet.mvc.model.Country;
import com.alphatek.tylt.web.servlet.mvc.model.State;
import com.alphatek.tylt.web.servlet.mvc.model.ZipCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
 *         Date: 5/1/13
 *         Time: 9:08 PM
 */
@Repository
public class AddressJdbcDao extends AbstractJdbcDao implements AddressDao {
	private static final String SQL_INSERT = "INSERT INTO address(street, additional_info, city, state_id, zip_code_prefix, zip_code_suffix, country_id, county, within_city_limits) " +
		"VALUES (:street, :additionalInfo, :city, (SELECT ID FROM state WHERE abbreviation = :state.abbreviation), :zipCode.prefix, :zipCode.suffix, (SELECT id FROM country WHERE code = :country.code), :county, :withinCityLimits)";
	private static final String SQL_SELECT = "SELECT address.id, street, additional_info, city, state.abbreviation, state_id, state.name, zip_code_prefix, zip_code_suffix, country_id, " +
		"country.code country_code, country.name country_name, county, within_city_limits FROM address, state, country WHERE address.state_id = state.id AND address.country_id = country.id AND ";
	private static final RowMapper<Address> ROW_MAPPER = new RowMapper<Address>() {
		@Override public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			Address address = new Address();
			address.setId(rs.getLong("id"));
			address.setStreet(rs.getString("street"));
			address.setAdditionalInfo(rs.getString("additional_info"));
			address.setCity(rs.getString("city"));
			address.setState(new State(rs.getString("state.abbreviation"), rs.getString("state.description")));
			address.setZipCode(new ZipCode(rs.getString("zip_code_prefix"), rs.getString("zip_code_suffix")));
			address.setCountry(new Country(rs.getString("country_code"), rs.getString("country_name")));
			address.setCounty(rs.getString("county"));
			address.setWithinCityLimits(rs.getBoolean("within_city_limits"));
			return address;
		}
	};

	@Autowired public AddressJdbcDao(DataSource dataSource) {
		super(dataSource);
	}

	@Override public long insertAddress(Address address) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(SQL_INSERT, new BeanPropertySqlParameterSource(address), keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Cacheable("address")
	@Transactional(readOnly = true)
	@Override public Address retrieveAddress(long id) {
		return getNamedParameterJdbcTemplate().queryForObject(SQL_SELECT + "address.id = :id", new MapSqlParameterSource("id", id), ROW_MAPPER);
	}

	@Override public List<Address> findAddress(Address address) {
		return null;
	}
}
