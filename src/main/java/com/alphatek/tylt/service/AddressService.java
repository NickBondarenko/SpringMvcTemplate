package com.alphatek.tylt.service;

import com.alphatek.tylt.web.servlet.mvc.model.Country;
import com.alphatek.tylt.web.servlet.mvc.model.State;

import java.util.List;

/**
 * User: jason.dimeo
 * Date: 2013-06-02 : 12:35 PM
 */
public interface AddressService {

	List<State> getStateList();

	List<Country> getCountryList();
}