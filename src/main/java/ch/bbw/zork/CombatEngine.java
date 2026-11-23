package ch.bbw.zork;

import java.util.Map;

public final class CombatEngine {
	private CombatEngine() {
	}

	public static Result compare(Army player, Army enemy, GameItem item) {
		long playerPower = calculatePower(player);
		long enemyPower = calculatePower(enemy);
		return new Result(playerPower, enemyPower);
	}

	private static long calculatePower(Army army) {
		long power = 0;
		for (Map.Entry<TroopType, Integer> troop : army.getTroops().entrySet()) {
			TroopType type = troop.getKey();
			power += (long) troop.getValue() * type.getStrength();
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
