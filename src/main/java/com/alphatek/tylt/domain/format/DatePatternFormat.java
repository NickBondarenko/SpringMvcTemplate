package com.alphatek.tylt.domain.format;

import static com.google.common.collect.Sets.immutableEnumSet;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author jason.dimeo
 * @version $Revision 1.3 $
 */
public enum DatePatternFormat {
	ISO_DATE("yyyy-MM-dd"),
	USA_DATE("MM/dd/yyyy"),
	ISO_TIME("hh:mm:ss a"),
	ISO_TIME_24("kk:mm:ss"),
	ISO_DATE_TIME(ISO_DATE.format + " " + ISO_TIME.format),
	USA_DATE_TIME(USA_DATE.format + " " + ISO_TIME.format),
	ISO_DATE_TIME_24(ISO_DATE.format + " " + ISO_TIME_24.format),
	USA_DATE_TIME_24(USA_DATE.format + " " + ISO_TIME_24.format);

	private static final class DatePatternFormatsHolder {
		static final Set<DatePatternFormat> DATE_FORMATS = immutableEnumSet(EnumSet.allOf(DatePatternFormat.class));
	}

	private final String format;
	private DatePatternFormat(String format) {
		this.format = format;
	}

	/**
	 * Method getFormat.
	 * @return String
	 */
	public String getFormat() {
	  return format;
  }

	/**
	 * Method getDatePatternFormats.
	 * @return Set<DatePatternFormat>
	 */
	public static Set<DatePatternFormat> getDatePatternFormats() {
  	return DatePatternFormatsHolder.DATE_FORMATS;
  }

	public static DatePatternFormat findByFormat(final String formatString) {
		return Iterables.find(DatePatternFormatsHolder.DATE_FORMATS, new Predicate<DatePatternFormat>() {
			@Override	public boolean apply(DatePatternFormat dateFormat) {
				return dateFormat.format.equals(formatString);
			}
		}, USA_DATE);
	}
}