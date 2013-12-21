package com.alphatek.tylt.repository;

import com.alphatek.tylt.web.servlet.mvc.model.State;

import java.util.List;

/**
 * User: jason.dimeo
 * Date: 2013-06-02 : 11:49 AM
 */
public interface StateDao {

	List<State> retrieveStateList();

	State retrieveStateById(int id);

	State retrieveByAbbreviation(String abbreviation);

	boolean isValidStateAbbreviation(String abbreviation);
}