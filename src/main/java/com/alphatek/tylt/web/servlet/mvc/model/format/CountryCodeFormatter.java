package com.alphatek.tylt.web.servlet.mvc.model.format;

import com.alphatek.tylt.web.servlet.mvc.model.Country;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author jason.dimeo
 *         Date: 6/3/13
 *         Time: 7:57 PM
 */
public class CountryCodeFormatter implements Formatter<Country> {
	@Override public Country parse(String code, Locale locale) throws ParseException {
		return new Country(code);
	}

	@Override public String print(Country country, Locale locale) {
		return country.getCode();
	}
}