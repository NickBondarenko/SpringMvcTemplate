package com.alphatek.tylt.repository;

import com.alphatek.tylt.web.servlet.mvc.model.Country;

import java.util.List;

/**
 * User: jason.dimeo
 * Date: 2013-06-03 : 10:17 PM
 */
public interface CountryDao {

	List<Country> retrieveCountryList();

	Country retrieveById(long id);

	boolean isValidCountryCode(String code);
}