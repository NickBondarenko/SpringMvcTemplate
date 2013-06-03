package com.alphatek.tylt.repository;

import com.alphatek.tylt.domain.CodeDescription;

import java.util.List;

/**
 * User: jason.dimeo
 * Date: 2013-06-02 : 11:49 AM
 */
public interface StateDao {

	List<CodeDescription<String>> retrieveStateList();

	CodeDescription<String> retrieveStateById(int id);
}