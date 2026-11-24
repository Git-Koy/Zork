package ch.bbw.zork;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Stores the player's active troop counts and the troop types unlocked for conversion.
 */
public class Army {
	private final EnumMap<TroopType, Integer> troops = new EnumMap<TroopType, Integer>(TroopType.class);
	private final Set<TroopType> unlockedTroops = EnumSet.noneOf(TroopType.class);

	/**
	 * Creates an army from the supplied starting troop counts.
	 *
	 * @param startingTroops troop types and positive starting counts
	 */
	public Army(Map<TroopType, Integer> startingTroops) {
		for (Map.Entry<TroopType, Integer> troop : startingTroops.entrySet()) {
			if (troop.getValue() != null && troop.getValue() > 0) {
				troops.put(troop.getKey(), troop.getValue());
				unlockedTroops.add(troop.getKey());
			}
		}
	}

	/**
	 * Returns a copy of the active troop counts.
	 *
	 * @return a defensive copy of the troop counts
	 */
	public Map<TroopType, Integer> getTroops() {
		return new EnumMap<TroopType, Integer>(troops);
	}

	/**
	 * Returns a copy of all troop types available for conversion.
	 *
	 * @return a defensive copy of unlocked troop types
	 */
	public Set<TroopType> getUnlockedTroops() {
		Set<TroopType> copy = EnumSet.noneOf(TroopType.class);
		copy.addAll(unlockedTroops);
		return copy;
	}

	/**
	 * Returns the active count for a troop type.
	 *
	 * @param type troop type to look up
	 * @return its active count, or zero when absent
	 */
	public int getTroopCount(TroopType type) {
		Integer count = troops.get(type);
		return count == null ? 0 : count;
	}

	/**
	 * Returns the sum of all active troop counts.
	 *
	 * @return total active soldiers
	 */
	public int getTotalTroops() {
		int total = 0;
		for (Integer count : troops.values()) {
			total += count;
		}
		return total;
	}

	/**
	 * Makes a troop type available for conversion.
	 *
	 * @param type troop type to unlock
	 */
	public void unlockTroop(TroopType type) {
		unlockedTroops.add(type);
	}

	/**
	 * Converts one active troop stack to an unlocked type, with a five percent
	 * conversion loss and strength-based count adjustment.
	 *
	 * @param from active troop type to convert
	 * @param to unlocked troop type to receive
	 * @return {@code true} if the conversion was applied
	 */
	public boolean switchTroop(TroopType from, TroopType to) {
		int oldCount = getTroopCount(from);
		if (oldCount == 0 || !unlockedTroops.contains(to) || troops.containsKey(to)) {
			return false;
		}
		int survivingCount = oldCount - Math.round(oldCount * 0.05f);
		int replacementCount = survivingCount * from.getStrength() / to.getStrength();
		if (replacementCount <= 0) {
			return false;
		}
		troops.remove(from);
		troops.put(to, replacementCount);
		return true;
	}
}
