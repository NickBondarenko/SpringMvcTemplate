package com.alphatek.tylt.web.converter;

import com.alphatek.tylt.domain.format.DatePatternFormat;
import com.alphatek.tylt.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {
	private static final DatePatternFormat DEFAULT_DATE_FORMAT = DatePatternFormat.ISO_TIME;
	private DatePatternFormat datePatternFormat;

	@Autowired(required=false)
	public void setDateFormat(DatePatternFormat datePatternFormat) {
		this.datePatternFormat = datePatternFormat;
	}

	@Override
	public Date convert(String dateString) {
		try {
			return DateUtils.parse(dateString, datePatternFormat == null ? DEFAULT_DATE_FORMAT : datePatternFormat);
		} catch (ParseException e) {
			throw new IllegalStateException("Unable to parse date " + dateString, e);
		}
	}
}