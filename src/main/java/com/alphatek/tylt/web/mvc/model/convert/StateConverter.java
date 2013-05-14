package com.alphatek.tylt.web.mvc.model.convert;

import com.alphatek.tylt.web.mvc.model.State;
import org.springframework.core.convert.converter.Converter;

/**
 * @author jason.dimeo
 *         Date: 4/7/13
 *         Time: 8:58 PM
 */
public class StateConverter implements Converter<String, State> {
	/**
	 * Convert the source of type String to target type State.
	 *
	 * @param state the source object to convert, which must be an instance of String
	 * @return the converted object, which must be an instance of State
	 * @throws IllegalArgumentException if the source could not be converted to the desired target type
	 */
	@Override	public State convert(String state) {
		return State.findByCode(state);
	}
}