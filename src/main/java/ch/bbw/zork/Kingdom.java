package ch.bbw.zork;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

/**
 * Kingdom metadata and its initial defending troop formation.
 */
public enum Kingdom {
	ALBA("Alba", new TroopType[] { TroopType.PIKES, TroopType.LEVIES }, new int[] { 14, 15 }),
	IRELAND("Ireland", new TroopType[] { TroopType.ARMORED_FOOTMEN, TroopType.LEVIES },
			new int[] { 12, 15 }),
	WALES("Wales", new TroopType[] { TroopType.ARCHERS, TroopType.LEVIES }, new int[] { 12, 15 }),
	ENGLAND("England", new TroopType[] { TroopType.LEVIES, TroopType.LONGBOWS, TroopType.HUSCARLS },
			new int[] { 12, 10, 8 }),
	FRANCE("France", new TroopType[] { TroopType.ARMORED_HORSEMEN, TroopType.LEVIES, TroopType.FOOTKNIGHTS },
			new int[] { 10, 12, 8 }),
	DENMARK("Denmark", new TroopType[] { TroopType.HUSCARLS, TroopType.LEVIES, TroopType.ARCHERS },
			new int[] { 10, 12, 10 }),
	EAST_FRANCIA("East Francia",
			new TroopType[] { TroopType.ARMORED_FOOTMEN, TroopType.FOOTKNIGHTS, TroopType.LEVIES,
					TroopType.ARCHERS },
			new int[] { 8, 8, 12, 8 }),
	CROATIA("Croatia", new TroopType[] { TroopType.ARMORED_HORSEMEN, TroopType.LIGHT_HORSEMEN },
			new int[] { 14, 14 }),
	BOHEMIA("Bohemia", new TroopType[] { TroopType.ARMORED_FOOTMEN, TroopType.PIKES },
			new int[] { 12, 12 }),
	BAVARIA("Bavaria", new TroopType[] { TroopType.HUSCARLS, TroopType.LEVIES },
			new int[] { 10, 12 });

	private final String displayName;
	private final TroopType[] troopTypes;
	private final int[] troopCounts;

	/**
	 * Defines a kingdom's display name and initial troop formation.
	 *
	 * @param displayName readable kingdom name
	 * @param troopTypes troop types in the initial formation
	 * @param troopCounts corresponding initial troop counts
	 */
	Kingdom(String displayName, TroopType[] troopTypes, int[] troopCounts) {
		this.displayName = displayName;
		this.troopTypes = troopTypes;
		this.troopCounts = troopCounts;
	}

	/**
	 * Returns the readable kingdom name.
	 *
	 * @return display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Returns a copy of the troop types in this kingdom's formation.
	 *
	 * @return defensive copy of the troop types
	 */
	public TroopType[] getTroopTypes() {
		return troopTypes.clone();
	}

	/**
	 * Returns the initial troop counts for this kingdom.
	 *
	 * @return a new map of troop types to counts
	 */
	public Map<TroopType, Integer> getTroops() {
		EnumMap<TroopType, Integer> troops = new EnumMap<TroopType, Integer>(TroopType.class);
		for (int index = 0; index < troopTypes.length; index++) {
			troops.put(troopTypes[index], troopCounts[index]);
		}
		return troops;
	}

	/**
	 * Finds a kingdom by display name or enum name, ignoring case and treating
	 * spaces, underscores, and hyphens as equivalent.
	 *
	 * @param value name to look up
	 * @return matching kingdom, or {@code null} if there is no match
	 */
	public static Kingdom fromName(String value) {
		if (value == null) {
			return null;
		}
		String normalized = normalize(value);
		for (Kingdom kingdom : values()) {
			if (normalize(kingdom.displayName).equals(normalized)
					|| normalize(kingdom.name()).equals(normalized)) {
				return kingdom;
			}
		}
		return null;
	}

	/**
	 * Normalizes separators and case for kingdom-name comparisons.
	 *
	 * @param value name to normalize
	 * @return normalized name
	 */
	private static String normalize(String value) {
		return value.trim().replace('_', ' ').replace('-', ' ').toLowerCase(Locale.ENGLISH);
	}
}
