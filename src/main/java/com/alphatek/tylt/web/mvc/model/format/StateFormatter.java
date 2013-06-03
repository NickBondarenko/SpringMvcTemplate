package com.alphatek.tylt.web.mvc.model.format;

import com.alphatek.tylt.repository.StateDao;
import com.alphatek.tylt.web.mvc.model.State;
import org.springframework.format.Formatter;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Locale;

/**
 * User: jason.dimeo
 * Date: 2013-06-03 : 3:01 PM
 */
public class StateFormatter implements Formatter<State> {
	@Resource private StateDao stateDao;

	@Override public State parse(String abbreviation, Locale locale) throws ParseException {
		return stateDao.retrieveByAbbreviation(abbreviation);
	}

	@Override public String print(State state, Locale locale) {
		return state.getAbbreviation();
	}
}