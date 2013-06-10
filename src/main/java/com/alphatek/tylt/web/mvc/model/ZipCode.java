package com.alphatek.tylt.web.mvc.model;

import com.alphatek.tylt.support.util.StringUtils;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.io.Serializable;

@com.alphatek.tylt.web.mvc.model.validate.constraints.ZipCode(message = "{com.alphatek.tylt.web.mvc.model.ZipCode.message}")
public final class ZipCode implements Comparable<ZipCode>, Serializable {
	private static final String DELIMITER = "-";
	private String prefix;
	private String suffix;

	public ZipCode() {}

	public ZipCode(String prefix) {
		String[] zipCodeAspects = parse(prefix);
		this.prefix = zipCodeAspects[0];
		this.suffix = zipCodeAspects[1];
	}

	public ZipCode(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public ZipCode(ZipCode zipCode) {
		prefix = zipCode.prefix;
		suffix = zipCode.suffix;
	}

	public static ZipCode newInstance() {
		return new ZipCode();
	}

	public static ZipCode newInstance(String prefix) {
		return new ZipCode(prefix);
	}

	public static ZipCode newInstance(String prefix, String suffix) {
		return new ZipCode(prefix, suffix);
	}

	public static ZipCode newInstance(ZipCode zipCode) {
		return new ZipCode(zipCode);
	}

	private String[] parse(String zipCode) {
		Preconditions.checkNotNull(zipCode, "Unable to parse null Zip Code");
		Preconditions.checkArgument(!zipCode.isEmpty(), "Unable to parse empty Zip Code");

		String[] zipCodeAspects = zipCode.split(DELIMITER, 1);
		for (int i = 0; i < zipCodeAspects.length; i++) {
			if (zipCodeAspects[i].length() > 5) {
				zipCodeAspects[i] = zipCodeAspects[i].substring(0, 5);
			}
		}

		if (zipCodeAspects.length == 1) {
			zipCodeAspects = new String[] {zipCodeAspects[0], ""};
		}
		return zipCodeAspects;
	}

	public static String getDelimiter() {
		return DELIMITER;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFormattedZipCode() {
		if (StringUtils.isNullOrEmpty(suffix)) {
			return prefix;
		}
		return prefix + DELIMITER + suffix;
	}

	@Override public int compareTo(ZipCode zipCode) {
		int prefixDiff = prefix.compareTo(zipCode.prefix);
		if (prefixDiff != 0) { return prefixDiff; }
		return suffix.compareTo(zipCode.suffix);
	}

	@Override public int hashCode() {
		return Objects.hashCode(prefix, suffix);
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null || getClass() != obj.getClass()) { return false; }
		final ZipCode other = (ZipCode) obj;
		return Objects.equal(this.prefix, other.prefix) && Objects.equal(this.suffix, other.suffix);
	}

	@Override public String toString() {
		return Objects.toStringHelper(this)
			.add("prefix", prefix)
			.add("suffix", suffix)
			.toString();
	}

}