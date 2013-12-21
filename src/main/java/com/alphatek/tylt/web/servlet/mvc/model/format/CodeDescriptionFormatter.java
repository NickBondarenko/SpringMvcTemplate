package com.alphatek.tylt.web.servlet.mvc.model.format;

import com.alphatek.tylt.domain.CodeDescription;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * User: jason.dimeo
 * Date: 2013-06-02 : 8:43 PM
 */
public class CodeDescriptionFormatter implements Formatter<CodeDescription<String>> {
	@Override public CodeDescription<String> parse(String code, Locale locale) throws ParseException {
		return new CodeDescription<>(code, "");
	}

	@Override public String print(CodeDescription<String> codeDescription, Locale locale) {
		return codeDescription.getCode();
	}
}