package com.alphatek.tylt.repository;

import com.alphatek.tylt.web.servlet.mvc.model.Address;

import java.util.List;

/**
 * @author jason.dimeo
 *         Date: 5/1/13
 *         Time: 9:03 PM
 */
public interface AddressDao {
	Address retrieveAddress(long id);
	List<Address> findAddress(Address address);
	long insertAddress(Address address);
}
