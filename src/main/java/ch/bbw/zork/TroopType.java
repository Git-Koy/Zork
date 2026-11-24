package ch.bbw.zork;

import java.util.Locale;

/**
 * Troop types, their strengths, combat categories, and category counters.
 */
public enum TroopType {
	PIKES("Pikes", TroopCategory.PIKEMEN, 3),
	LEVIES("Levies", TroopCategory.LEVIES, 1),
	ARMORED_FOOTMEN("Armored Footmen", TroopCategory.HEAVY_INFANTRY, 4),
	ARCHERS("Archers", TroopCategory.ARCHERS, 2),
	LONGBOWS("Longbows", TroopCategory.ARCHERS, 3),
	HUSCARLS("Huscarls", TroopCategory.HEAVY_INFANTRY, 4),
	ARMORED_HORSEMEN("Armored Horsemen", TroopCategory.CAVALRY, 5),
	FOOTKNIGHTS("Footknights", TroopCategory.HEAVY_INFANTRY, 5),
	LIGHT_HORSEMEN("Light Horsemen", TroopCategory.CAVALRY, 3);

	private final String displayName;
	private final TroopCategory category;
	private final int strength;

	/**
	 * Defines a troop type's display name, category, and base strength.
	 *
	 * @param displayName readable troop name
	 * @param category troop category
	 * @param strength base strength per soldier
	 */
	TroopType(String displayName, TroopCategory category, int strength) {
		this.displayName = displayName;
		this.category = category;
		this.strength = strength;
	}

	/**
	 * Returns the readable troop name.
	 *
	 * @return display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Returns this troop type's combat category.
	 *
	 * @return troop category
	 */
	public TroopCategory getCategory() {
		return category;
	}

	/**
	 * Returns the base strength of one soldier of this type.
	 *
	 * @return base strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * Tests whether this type counters another troop type.
	 *
	 * @param other troop type being tested
	 * @return {@code true} when this type counters {@code other}
	 */
	public boolean counters(TroopType other) {
		switch (category) {
		case CAVALRY:
			return other.category == TroopCategory.ARCHERS
					|| other.category == TroopCategory.HEAVY_INFANTRY;
		case PIKEMEN:
			return other.category == TroopCategory.CAVALRY;
		case HEAVY_INFANTRY:
		case ARCHERS:
			return other.category == TroopCategory.PIKEMEN;
		default:
			return false;
		}
	}

	/**
	 * Finds a troop type by display name or enum name, ignoring case and
	 * treating spaces, underscores, and hyphens as equivalent.
	 *
	 * @param value name to look up
	 * @return matching troop type, or {@code null} if there is no match
	 */
	public static TroopType fromName(String value) {
		if (value == null) {
			return null;
		}
		String normalized = normalize(value);
		for (TroopType type : values()) {
			if (normalize(type.displayName).equals(normalized)
					|| normalize(type.name()).equals(normalized)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Normalizes separators and case for troop-name comparisons.
	 *
	 * @param value name to normalize
	 * @return normalized name
	 */
	private static String normalize(String value) {
		return value.trim().replace('_', ' ').replace('-', ' ').toLowerCase(Locale.ENGLISH);
	}
}
