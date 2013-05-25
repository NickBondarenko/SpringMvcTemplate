package com.alphatek.tylt.repository;

import com.alphatek.tylt.web.mvc.model.Address;

import java.util.List;

/**
 * @author jason.dimeo
 *         Date: 5/1/13
 *         Time: 9:03 PM
 */
public interface AddressDao {
	Address retrieveAddress(int id);
	List<Address> findAddress(Address address);
	int insertAddress(Address address);
}
