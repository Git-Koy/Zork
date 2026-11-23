package ch.bbw.zork;

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

}
