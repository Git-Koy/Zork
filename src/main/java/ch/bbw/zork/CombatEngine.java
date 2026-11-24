package ch.bbw.zork;

import java.util.Map;

/**
 * Calculates deterministic army strength and battle outcomes.
 */
public final class CombatEngine {
	/**
	 * Prevents construction of this utility class.
	 */
	private CombatEngine() {
	}

	/**
	 * Compares two armies, applying item modifiers only to the player's army.
	 *
	 * @param player player's army
	 * @param enemy defending army
	 * @param item player's equipped item, or {@code null}
	 * @return the calculated powers and outcome
	 */
	public static Result compare(Army player, Army enemy, Item item) {
		long playerPower = calculatePower(player, enemy, item);
		long enemyPower = calculatePower(enemy, player, null);
		return new Result(playerPower, enemyPower);
	}

	/**
	 * Sums troop strength after item, counter, and levy modifiers.
	 *
	 * @param army army whose power is calculated
	 * @param opponent opposing army used to determine counters
	 * @param item item modifier, or {@code null}
	 * @return total modified power
	 */
	private static long calculatePower(Army army, Army opponent, Item item) {
		long power = 0;
		for (Map.Entry<TroopType, Integer> troop : army.getTroops().entrySet()) {
			TroopType type = troop.getKey();
			int modifier = item == null ? 0 : item.getPowerModifier(type.getCategory());
			boolean hasCounter = false;
			boolean isCountered = false;
			for (TroopType enemyType : opponent.getTroops().keySet()) {
				hasCounter |= type.counters(enemyType);
				isCountered |= enemyType.counters(type);
			}
			if (hasCounter) {
				modifier += 25;
			}
			if (isCountered) {
				modifier -= 25;
			}
			if (type.getCategory() == TroopCategory.LEVIES) {
				modifier += 25;
			}
			power += (long) troop.getValue() * type.getStrength() * (100 + modifier) / 100;
		}
		return power;
	}

	/**
	 * Immutable result of a combat strength comparison.
	 */
	public static final class Result {
		private final long playerPower;
		private final long enemyPower;

		/**
		 * Creates a result containing both army power totals.
		 *
		 * @param playerPower player's calculated power
		 * @param enemyPower defender's calculated power
		 */
		private Result(long playerPower, long enemyPower) {
			this.playerPower = playerPower;
			this.enemyPower = enemyPower;
		}

		/**
		 * Returns the player's calculated power.
		 *
		 * @return player power
		 */
		public long getPlayerPower() {
			return playerPower;
		}

		/**
		 * Returns the defender's calculated power.
		 *
		 * @return defender power
		 */
		public long getEnemyPower() {
			return enemyPower;
		}

		/**
		 * Indicates whether both armies have equal power.
		 *
		 * @return {@code true} when the battle is a draw
		 */
		public boolean isDraw() {
			return playerPower == enemyPower;
		}

		/**
		 * Indicates whether the player's army has greater power.
		 *
		 * @return {@code true} when the player wins
		 */
		public boolean isPlayerVictory() {
			return playerPower > enemyPower;
		}
	}
}
