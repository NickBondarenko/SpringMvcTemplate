package com.alphatek.tylt.repository;

import com.alphatek.tylt.domain.CodeDescription;
import com.alphatek.tylt.web.mvc.model.Address;
import com.alphatek.tylt.web.mvc.model.State;
import com.alphatek.tylt.web.mvc.model.ZipCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
	private static final String SQL_INSERT = "INSERT INTO address(street, additional_info, city, state, zip_code_prefix, zip_code_suffix, country, county, within_city_limits) " +
		"VALUES (:street, :additionalInfo, :city, :state.code, :zipCode.prefix, :zipCode.suffix, :country, :county, :withinCityLimits)";
	private static final String SQL_SELECT = "SELECT * FROM address WHERE ";
	private static final RowMapper<Address> ROW_MAPPER = new RowMapper<Address>() {
		@Override public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			Address address = new Address();
			address.setId(rs.getInt("id"));
			address.setStreet(rs.getString("street"));
			address.setAdditionalInfo(rs.getString("additional_info"));
			address.setCity(rs.getString("city"));
			address.setState(State.findByCode(rs.getString("state")));
			address.setZipCode(new ZipCode(rs.getString("zip_code_prefix"), rs.getString("zip_code_suffix")));
			address.setCountry(new CodeDescription<>("", rs.getString("country")));
			address.setCounty(rs.getString("county"));
			address.setWithinCityLimits(rs.getBoolean("within_city_limits"));
			return address;
		}
	};

	@Autowired public AddressJdbcDao(DataSource dataSource) {
		super(dataSource);
	}

	@Override public int insertAddress(Address address) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(SQL_INSERT, new BeanPropertySqlParameterSource(address), keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override public Address retrieveAddress(int id) {
		return getNamedParameterJdbcTemplate().queryForObject(SQL_SELECT + "address.id = :id", new MapSqlParameterSource("id", id), ROW_MAPPER);
	}

	@Override public List<Address> findAddress(Address address) {
		return null;
	}
}
