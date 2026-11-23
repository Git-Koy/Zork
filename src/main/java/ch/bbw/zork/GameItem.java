package ch.bbw.zork;

import java.util.Locale;

public enum GameItem {
	PICTISH_STANDARD("pictish_standard", "Pictish Standard", TroopCategory.PIKEMEN,
			TroopCategory.ARCHERS),
	SAINT_BRIGIDS_CROSS("saints_cross", "Saint Brigid's Cross", TroopCategory.LEVIES,
			TroopCategory.CAVALRY),
	WELSH_LONGBOW("welsh_longbow", "Welsh Longbow", TroopCategory.ARCHERS,
			TroopCategory.HEAVY_INFANTRY),
	ROYAL_MAIL("royal_mail", "Royal Mail", TroopCategory.HEAVY_INFANTRY,
			TroopCategory.CAVALRY),
	KNIGHTS_LANCE("knights_lance", "Knight's Lance", TroopCategory.CAVALRY,
			TroopCategory.PIKEMEN),
	DANE_AXE("dane_axe", "Dane Axe", TroopCategory.HEAVY_INFANTRY,
			TroopCategory.ARCHERS),
	IMPERIAL_SEAL("imperial_seal", "Imperial Seal", TroopCategory.LEVIES,
			TroopCategory.HEAVY_INFANTRY),
	HORSE_SPURS("horse_spurs", "Horse Spurs", TroopCategory.CAVALRY,
			TroopCategory.ARCHERS),
	BOHEMIAN_PIKEHEAD("bohemian_pikehead", "Bohemian Pikehead", TroopCategory.PIKEMEN,
			TroopCategory.LEVIES);

	private static final int BONUS_PERCENT = 10;
	private static final int PENALTY_PERCENT = 5;

	private final String id;
	private final String displayName;
	private final TroopCategory bonusCategory;
	private final TroopCategory penaltyCategory;

	GameItem(String id, String displayName, TroopCategory bonusCategory,
			TroopCategory penaltyCategory) {
		this.id = id;
		this.displayName = displayName;
		this.bonusCategory = bonusCategory;
		this.penaltyCategory = penaltyCategory;
	}

	public String getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getPowerModifier(TroopCategory category) {
		if (category == bonusCategory) {
			return BONUS_PERCENT;
		}
		if (category == penaltyCategory) {
			return -PENALTY_PERCENT;
		}
		return 0;
	}

	public static GameItem fromId(String value) {
		if (value == null) {
			return null;
		}
		String normalized = value.trim().toLowerCase(Locale.ENGLISH).replace(' ', '_');
		for (GameItem item : values()) {
			if (item.id.equals(normalized) || item.name().toLowerCase(Locale.ENGLISH).equals(normalized)) {
				return item;
			}
		}
		return null;
	}
}
