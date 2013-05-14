package com.alphatek.tylt.web.mvc.model;

import static com.google.common.collect.Sets.immutableEnumSet;

import com.alphatek.tylt.domain.CodeDescription;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Type-safe enum to hold a list of the states
 * @author jason.dimeo
 * @version $Revision 1.2 $
 */
public enum State {
	ALABAMA("AL", "Alabama"),
	ALASKA("AK", "Alaska"),
	AMERICAN_SAMOA("AS", "American Samoa"),
	ARIZONA("AZ", "Arizona"),
	ARKANSAS("AR", "Arkansas"),
	CALIFORNIA("CA", "California"),
	COLORADO("CO", "Colorado"),
	CONNECTICUT("CT", "Connecticut"),
	DELAWARE("DE", "Delaware"),
	DISTRICT_OF_COLUMBIA("DC", "District of Columbia"),
	FEDERATED_STATES_OF_MICRONESIA("FM", "Federated States of Micronesia"),
	FLORIDA("FL", "Florida"),
	GEORGIA("GA", "Georgia"),
	GUAM("GU", "Guam"),
	HAWAII("HI", "Hawaii"),
	IDAHO("ID", "Idaho"),
	ILLINIOS("IL", "Illinios"),
	INDIANA("IN", "Indiana"),
	IOWA("IA", "Iowa"),
	KANSAS("KS", "Kansas"),
	KENTUCKY("KY", "Kentucky"),
	LOUISIANA("LA", "Louisiana"),
	MAINE("ME", "Maine"),
	MARSHALL_ISLANDS("MH", "Marshall Islands"),
	MARYLAND("MD", "Maryland"),
	MASSACHUSETTS("MA", "Massachusetts"),
	MICHIGAN("MI", "Michigan"),
	MINNESOTA("MN", "Minnesota"),
	MISSISSIPPI("MS", "Mississippi"),
	MISSOURI("MO", "Missouri"),
	MONTANA("MT", "Montana"),
	NEBRASKA("NE", "Nebraska"),
	NEVADA("NV", "Nevada"),
	NEW_HAMPSHIRE("NH", "New Hampshire"),
	NEW_JERSEY("NJ", "New Jersey"),
	NEW_MEXICO("NM", "New Mexico"),
	NEW_YORK("NY", "New York"),
	NORTH_CAROLINA("NC", "North Carolina"),
	NORTH_DAKOTA("ND", "North Dakota"),
	NORTHERN_MARIANA_ISLANDS("MP", "North Mariana Islands"),
	OHIO("OH", "Ohio"),
	OKLAHOMA("OK", "Oklahoma"),
	OREGON("OR", "Oregon"),
	PALUA("PW", "Palau"),
	PENNSYLVANIA("PA", "Pennsylvania"),
	PUERTO_RICO("PR", "Puerto Rico"),
	RHODE_ISLAND("RI", "Rhode Island"),
	SOUTH_CAROLINA("SC", "South Carolina"),
	SOUTH_DAKOTA("SD", "South Dakota"),
	TENNESSEE("TN", "Tennessee"),
	TEXAS("TX", "Texas"),
	UTAH("UT", "Utah"),
	VERMONT("VT", "Vermont"),
	VIRGIN_ISLANDS("VI", "Virgin Islands"),
	VIRGINIA("VA", "Virginia"),
	WASHINGTON("WA", "Washington"),
	WEST_VIRGINIA("WV", "West Virginia"),
	WISCONSIN("WI", "Wisconsin"),
	WYOMING("WY", "Wyoming");

	private final CodeDescription<String> codeDescription;

	private static final class StatesHolder {
		static final Set<State> STATES_SET = immutableEnumSet(EnumSet.allOf(State.class));
	}

	private static final class StateMapHolder {
		private static final ImmutableMap.Builder<String, String> MAP_BUILDER = new ImmutableMap.Builder<>();
		static {
			for (State state : values()) {
				MAP_BUILDER.put(state.codeDescription.getCode(), state.codeDescription.getDescription());
			}
		}
		final static Map<String, String> STATES_MAP = MAP_BUILDER.build();
		final static Set<String> STATE_CODES = STATES_MAP.keySet();
		final static Collection<String> STATE_DISPLAY_NAMES = STATES_MAP.values();
	}

	/**
	 * Constructor for State.
	 * @param code String
	 * @param displayName String
	 */
	private State(String code, String displayName) {
		codeDescription = new CodeDescription<>(code, displayName);
	}

	/**
	 * Method getCode.
	 * @return String
	 */
	public String getCode() {
		return codeDescription.getCode();
	}

	/**
	 * Method getDisplayName.
	 * @return String
	 */
	public String getDisplayName() {
		return codeDescription.getDescription();
	}

	/**
	 * Method asCodeDescription.
	 * @return CodeDescription<String>
	 */
	public CodeDescription<String> asCodeDescription() {
		return codeDescription;
	}

	/**
	 * Method getStatesSet.
	 * @return Set<State>
	 */
	public static Set<State> getStatesSet() {
		return StatesHolder.STATES_SET;
	}

	/**
	 * Method getStateCodes.
	 * @return Set<String>
	 */
	public static Set<String> getStateCodes() {
		return StateMapHolder.STATE_CODES;
	}

	/**
	 * Method getStateDisplayNames.
	 * @return Collection<String>
	 */
	public static Collection<String> getStateDisplayNames() {
		return StateMapHolder.STATE_DISPLAY_NAMES;
	}

	/**
	 * Method getStatesMap.
	 * @return Map<>
	 */
	public static Map<String, String> getStatesMap() {
		return StateMapHolder.STATES_MAP;
	}

	/**
	 * Method find.
	 * @param code String
	 * @return State
	 */
	public static State findByCode(final String code) {
		return Iterables.find(StatesHolder.STATES_SET, new Predicate<State>() {
			@Override	public boolean apply(State input) {
				return input.codeDescription.getCode().equals(code);
			}
		}, null);
	}

	/**
	 * Method findAsCodeDefinition.
	 * @param code String
	 * @return CodeDefinition<String>
	 */
	public static CodeDescription<String> findAsCodeDescription(final String code) {
		State state = findByCode(code);
		if (state == null) {
	    return new CodeDescription<>("", "");
    }
		return state.codeDescription;
	}

	/**
	 * Method forName.
	 * @param displayName String
	 * @return State
	 */
	public static State forName(final String displayName) {
		return Iterables.find(StatesHolder.STATES_SET, new Predicate<State>() {
			@Override	public boolean apply(State input) {
				return input.codeDescription.getDescription().equals(displayName);
			}
		}, null);
	}
}