package com.alphatek.tylt.service;

import com.alphatek.tylt.repository.StateDao;
import com.alphatek.tylt.web.mvc.model.State;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: jason.dimeo
 * Date: 2013-06-02 : 12:35 PM
 */
@Service
public class DefaultAddressService implements AddressService {
	@Resource private StateDao stateDao;

	@Override public List<State> getStateList() {
		return stateDao.retrieveStateList();
	}
}