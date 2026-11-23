package ch.bbw.zork;

import java.util.Map;

public final class CombatEngine {
	private CombatEngine() {
	}

	public static Result compare(Army player, Army enemy, GameItem item) {
		long playerPower = calculatePower(player, enemy, item);
		long enemyPower = calculatePower(enemy, player, null);
		return new Result(playerPower, enemyPower);
	}

	private static long calculatePower(Army army, Army opponent, GameItem item) {
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

	public static final class Result {
		private final long playerPower;
		private final long enemyPower;

		private Result(long playerPower, long enemyPower) {
			this.playerPower = playerPower;
			this.enemyPower = enemyPower;
		}

		public long getPlayerPower() {
			return playerPower;
		}

		public long getEnemyPower() {
			return enemyPower;
		}

		public boolean isDraw() {
			return playerPower == enemyPower;
		}

		public boolean isPlayerVictory() {
			return playerPower > enemyPower;
		}
	}
}
