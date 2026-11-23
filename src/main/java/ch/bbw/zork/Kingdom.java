package ch.bbw.zork;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public enum Kingdom {
	ALBA("Alba", new TroopType[] { TroopType.PIKES, TroopType.LEVIES }, new int[] { 12, 15 }),
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
			new int[] { 12, 12 });

	private final String displayName;
	private final TroopType[] troopTypes;
	private final int[] troopCounts;

	Kingdom(String displayName, TroopType[] troopTypes, int[] troopCounts) {
		this.displayName = displayName;
		this.troopTypes = troopTypes;
		this.troopCounts = troopCounts;
	}

	public String getDisplayName() {
		return displayName;
	}

	public TroopType[] getTroopTypes() {
		return troopTypes.clone();
	}

	public Map<TroopType, Integer> getTroops() {
		EnumMap<TroopType, Integer> troops = new EnumMap<TroopType, Integer>(TroopType.class);
		for (int index = 0; index < troopTypes.length; index++) {
			troops.put(troopTypes[index], troopCounts[index]);
		}
		return troops;
	}

	public GameItem getItem() {
		switch (this) {
		case ALBA:
			return GameItem.PICTISH_STANDARD;
		case IRELAND:
			return GameItem.SAINT_BRIGIDS_CROSS;
		case WALES:
			return GameItem.WELSH_LONGBOW;
		case ENGLAND:
			return GameItem.ROYAL_MAIL;
		case FRANCE:
			return GameItem.KNIGHTS_LANCE;
		case DENMARK:
			return GameItem.DANE_AXE;
		case EAST_FRANCIA:
			return GameItem.IMPERIAL_SEAL;
		case CROATIA:
			return GameItem.HORSE_SPURS;
		default:
			return GameItem.BOHEMIAN_PIKEHEAD;
		}
	}

	public static Kingdom fromName(String value) {
		if (value == null) {
			return null;
		}
		String normalized = value.trim().toLowerCase(Locale.ENGLISH).replace('_', ' ');
		for (Kingdom kingdom : values()) {
			if (kingdom.displayName.toLowerCase(Locale.ENGLISH).equals(normalized)
					|| kingdom.name().toLowerCase(Locale.ENGLISH).replace('_', ' ').equals(normalized)) {
				return kingdom;
			}
		}
		return null;
	}
}
