package ch.bbw.zork;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class Army {
	private final EnumMap<TroopType, Integer> troops = new EnumMap<TroopType, Integer>(TroopType.class);
	private final Set<TroopType> unlockedTroops = EnumSet.noneOf(TroopType.class);

	public Army(Map<TroopType, Integer> startingTroops) {
		for (Map.Entry<TroopType, Integer> troop : startingTroops.entrySet()) {
			if (troop.getValue() != null && troop.getValue() > 0) {
				troops.put(troop.getKey(), troop.getValue());
				unlockedTroops.add(troop.getKey());
			}
		}
	}

	public Map<TroopType, Integer> getTroops() {
		return new EnumMap<TroopType, Integer>(troops);
	}

	public Set<TroopType> getUnlockedTroops() {
		Set<TroopType> copy = EnumSet.noneOf(TroopType.class);
		copy.addAll(unlockedTroops);
		return copy;
	}

	public int getTroopCount(TroopType type) {
		Integer count = troops.get(type);
		return count == null ? 0 : count;
	}

	public int getTotalTroops() {
		int total = 0;
		for (Integer count : troops.values()) {
			total += count;
		}
		return total;
	}

	public void unlockTroop(TroopType type) {
		unlockedTroops.add(type);
	}

}
