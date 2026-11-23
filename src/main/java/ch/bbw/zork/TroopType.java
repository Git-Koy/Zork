package ch.bbw.zork;

import java.util.Locale;

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

	TroopType(String displayName, TroopCategory category, int strength) {
		this.displayName = displayName;
		this.category = category;
		this.strength = strength;
	}

	public String getDisplayName() {
		return displayName;
	}

	public TroopCategory getCategory() {
		return category;
	}

	public int getStrength() {
		return strength;
	}

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

	public static TroopType fromName(String value) {
		if (value == null) {
			return null;
		}
		String normalized = value.trim().replace('_', ' ').toLowerCase(Locale.ENGLISH);
		for (TroopType type : values()) {
			if (type.displayName.toLowerCase(Locale.ENGLISH).equals(normalized)
					|| type.name().toLowerCase(Locale.ENGLISH).replace('_', ' ').equals(normalized)) {
				return type;
			}
		}
		return null;
	}
}
