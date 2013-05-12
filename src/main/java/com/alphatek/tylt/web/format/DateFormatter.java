package com.alphatek.tylt.web.format;

import com.alphatek.tylt.domain.format.DatePatternFormat;
import com.alphatek.tylt.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public final class DateFormatter implements Formatter<Date> {
	@Resource private DatePatternFormat defaultDatePatternFormat;
	@Autowired(required=false) private DatePatternFormat datePatternFormat;

	@Override
	public String print(Date date, Locale locale) {
		return DateUtils.format(date, datePatternFormat == null ? defaultDatePatternFormat : datePatternFormat, locale);
	}

	@Override
	public Date parse(String dateString, Locale locale) throws ParseException {
		return DateUtils.parse(dateString, datePatternFormat == null ? defaultDatePatternFormat : datePatternFormat, locale);
	}
}