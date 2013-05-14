package com.alphatek.tylt.web.mvc.model.format;

import com.alphatek.tylt.web.mvc.model.ZipCode;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author jason.dimeo
 *         Date: 4/8/13
 *         Time: 2:03 PM
 */
public class ZipCodeFormatter implements Formatter<ZipCode> {
	/**
	 * Parse a text String to produce a ZipCode.
	 *
	 * @param text the text string
	 * @param locale the current user locale
	 * @return an instance of ZipCode
	 * @throws java.text.ParseException when a parse exception occurs in a java.text parsing library
	 */
	@Override	public ZipCode parse(String text, Locale locale) throws ParseException {
		return ZipCode.parse(text);
	}

	/**
	 * Print the zipCode of type ZipCode for display.
	 *
	 * @param zipCode the instance to print
	 * @param locale the current user locale
	 * @return the printed text string
	 */
	@Override	public String print(ZipCode zipCode, Locale locale) {
		return zipCode.getFormattedZipCode();
	}
}