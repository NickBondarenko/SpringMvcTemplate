package com.alphatek.tylt.service;

import com.alphatek.tylt.repository.AddressDao;
import com.alphatek.tylt.repository.CountryDao;
import com.alphatek.tylt.repository.StateDao;
import com.alphatek.tylt.web.servlet.mvc.model.Address;
import com.alphatek.tylt.web.servlet.mvc.model.Country;
import com.alphatek.tylt.web.servlet.mvc.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: jason.dimeo
 * Date: 2013-06-02 : 12:35 PM
 */
@Service
public class DefaultAddressService implements AddressService {
	private final AddressDao addressDao;
	private final StateDao stateDao;
	private final CountryDao countryDao;

	@Autowired public DefaultAddressService(AddressDao addressDao, StateDao stateDao, CountryDao countryDao) {
		this.addressDao = addressDao;
		this.stateDao = stateDao;
		this.countryDao = countryDao;
	}

	@Override public Address addAddress(Address address) {
		long addressId = addressDao.insertAddress(address);
		address.setId(addressId);
		return address;
	}

	@Override public Country getCountryById(long id) {
		return countryDao.retrieveById(id);
	}

	@Override public List<State> getStateList() {
		return stateDao.retrieveStateList();
	}

	@Override public List<Country> getCountryList() {
		return countryDao.retrieveCountryList();
	}
}