package com.alphatek.tylt.service;

import com.alphatek.tylt.domain.CodeDescription;

import java.util.List;

/**
 * User: jason.dimeo
 * Date: 2013-06-02 : 12:35 PM
 */
public interface AddressService {

	List<CodeDescription<String>> getStateList();
}