package com.alphatek.tylt.web.mvc.model;

import com.alphatek.tylt.construct.Buildable;
import com.alphatek.tylt.util.StringUtils;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public final class ZipCode implements Buildable<ZipCode.Builder> {
	private static final String DELIMITER = "-";
	@NotEmpty(message = "{com.alphatek.tylt.web.mvc.model.ZipCode.prefix[NotEmpty.message]}")
	@Pattern(regexp = "^\\d{5}$", message = "{com.alphatek.tylt.web.mvc.mode.zipCode.prefix[Pattern.message]}")
	private final String prefix;
	@Pattern(regexp = "^\\d{4}$", message = "{com.alphatek.tylt.web.mvc.mode.zipCode.suffix[Pattern.message]}")
	private final String suffix;

	private ZipCode(Builder builder) {
		prefix = builder.prefix;
		suffix = builder.suffix;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static ZipCode parse(String zipCode) {
		Preconditions.checkNotNull(zipCode, "Unable to parse null Zip Code");
		Preconditions.checkArgument(!zipCode.isEmpty(), "Unable to parse empty Zip Code");

		ZipCode.Builder builder = ZipCode.newBuilder();
		if (zipCode.contains(DELIMITER)) {
			String[] zipCodeAspects = zipCode.split(DELIMITER);
			builder.prefix(zipCodeAspects[0]).suffix(zipCodeAspects[1]);
		} else {
			builder.prefix(zipCode);
		}
		return builder.build();
	}

	@Override	public Builder asNewBuilder() {
		return new Builder(this);
	}

	public static final class Builder implements com.alphatek.tylt.construct.Builder<ZipCode> {
		@NotEmpty(message = "{com.alphatek.tylt.web.mvc.model.ZipCode.prefix[NotEmpty.message]}")
		@Pattern(regexp = "^\\d{5}$", message = "{com.alphatek.tylt.web.mvc.model.ZipCode.prefix[Pattern.message]}")
		private String prefix;
		@Pattern(regexp = "^\\d{4}$", message = "{com.alphatek.tylt.web.mvc.model.ZipCode.suffix[Pattern.message]}")
		private String suffix;

		private Builder() {}

		private Builder(ZipCode zipCode) {
			prefix = zipCode.prefix;
			suffix = zipCode.suffix;
		}

		public ZipCode.Builder prefix(String prefix) {
			this.prefix = prefix;
			return this;
		}

		public ZipCode.Builder suffix(String suffix) {
			this.suffix = suffix;
			return this;
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

		@Override	public ZipCode build() {
			return new ZipCode(this);
		}

		@Override public int hashCode() {
			return Objects.hashCode(prefix, suffix);
		}

		@Override public boolean equals(Object obj) {
			if (this == obj) { return true; }
			if (obj == null || getClass() != obj.getClass()) { return false; }
			final Builder other = (Builder) obj;
			return Objects.equal(this.prefix, other.prefix) && Objects.equal(this.suffix, other.suffix);
		}

		@Override public String toString() {
			return Objects.toStringHelper(this)
				.add("prefix", prefix)
				.add("suffix", suffix)
				.toString();
		}

	}

	public String getPrefix() {
		return prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getFormattedZipCode() {
		if (StringUtils.isNullOrEmpty(suffix)) {
			return prefix;
		}
		return prefix + DELIMITER + suffix;
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