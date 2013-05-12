package com.alphatek.tylt.util;

import com.alphatek.tylt.domain.format.DatePatternFormat;
import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jason.dimeo
 * Date: 3/20/13
 * Time: 7:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {
	private DateUtils() {}

	/**
	 * Method getCalendarDays.
	 * @return Collection<Integer>
	 */
	public static Collection<Integer> getCalendarDays() {
		Collection<Integer> days = Lists.newArrayList();
		days.add(Calendar.SUNDAY);
		days.add(Calendar.MONDAY);
		days.add(Calendar.TUESDAY);
		days.add(Calendar.WEDNESDAY);
		days.add(Calendar.THURSDAY);
		days.add(Calendar.FRIDAY);
		days.add(Calendar.SATURDAY);
		return days;
	}

	/**
	 * Method getFormattedDate.
	 * @param format String
	 * @return String
	 */
	public static String getFormattedDate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * Method getFormattedDate.
	 * @param format DatePatternFormat
	 * @return String
	 */
	public static String getFormattedDate(DatePatternFormat format) {
		return getFormattedDate(format.getFormat());
	}

	/**
	 * Method getCalendarTime.
	 * @param date long
	 * @param time long
	 * @param timeZone TimeZone
	 * @return Calendar
	 */
	public static Calendar getCalendarTime(long date, long time, TimeZone timeZone) {
		Calendar calDate = Calendar.getInstance(timeZone);
		Calendar calTime = Calendar.getInstance(timeZone);
		calDate.setTimeInMillis(date);
		calTime.setTimeInMillis(time);

		calDate.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
		calDate.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
		calDate.set(Calendar.SECOND, calTime.get(Calendar.SECOND));

		return calDate;
	}

	/**
	 * Method getCalendarTime.
	 * @param date long
	 * @param time long
	 * @return Calendar
	 */
	public static Calendar getCalendarTime(long date, long time) {
		return getCalendarTime(date, time, TimeZone.getDefault());
	}

	/**
	 * Method getCalendarTime.
	 * @param date Date
	 * @param time Date
	 * @return Calendar
	 */
	public static Calendar getCalendarTime(Date date, Date time) {
		return getCalendarTime(date.getTime(), time.getTime(), TimeZone.getDefault());
	}

	/**
	 * Method getCalendarTime.
	 * @param date Date
	 * @param time Date
	 * @param timeZone TimeZone
	 * @return Calendar
	 */
	public static Calendar getCalendarTime(Date date, Date time, TimeZone timeZone) {
		return getCalendarTime(date.getTime(), time.getTime(), timeZone);
	}

	/**
	 * Method getDateTime.
	 * @param date Date
	 * @param time Date
	 * @param timeZone TimeZone
	 * @return Date
	 */
	public static Date getDateTime(Date date, Date time, TimeZone timeZone) {
		return getCalendarTime(date.getTime(), time.getTime(), timeZone).getTime();
	}

	/**
	 * Method getDateTime.
	 * @param date Date
	 * @param time Date
	 * @return Date
	 */
	public static Date getDateTime(Date date, Date time) {
		return getCalendarTime(date.getTime(), time.getTime(), TimeZone.getDefault()).getTime();
	}

	/**
	 * Method getDateTime.
	 * @param date long
	 * @param time long
	 * @param timeZone TimeZone
	 * @return Date
	 */
	public static Date getDateTime(long date, long time, TimeZone timeZone) {
		return getCalendarTime(date, time, timeZone).getTime();
	}

	/**
	 * Method getDateTime.
	 * @param date long
	 * @param time long
	 * @return Date
	 */
	public static Date getDateTime(long date, long time) {
		return getCalendarTime(date, time, TimeZone.getDefault()).getTime();
	}

	private static SimpleDateFormat createSimpleDateFormat(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateString);
		sdf.setCalendar(Calendar.getInstance());
		return sdf;
	}

	public static String format(Date date, DatePatternFormat datePatternFormat, Locale locale) {
		return new SimpleDateFormat(datePatternFormat.getFormat(), locale).format(date);
	}

	public static String format(Date date, String dateFormat, Locale locale) {
		return format(date, DatePatternFormat.findByFormat(dateFormat), locale);
	}

	public static Date parse(String dateString, DatePatternFormat datePatternFormat) throws ParseException {
		return createSimpleDateFormat(datePatternFormat.getFormat()).parse(dateString);
	}

	public static Date parse(String dateString, DatePatternFormat datePatternFormat, Locale locale) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(datePatternFormat.getFormat());
		sdf.setCalendar(Calendar.getInstance(locale));
		return sdf.parse(dateString);
	}

	public static Date parse(String dateString, String dateFormat, Locale locale) throws ParseException {
		return parse(dateString, DatePatternFormat.findByFormat(dateFormat), locale);
	}
}

