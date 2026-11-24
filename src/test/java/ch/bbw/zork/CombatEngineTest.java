package ch.bbw.zork;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests deterministic strength calculation and its battle result.
 */
public class CombatEngineTest {

	@Test
	public void equalUnmodifiedArmiesDraw() {
		CombatEngine.Result result = CombatEngine.compare(army(TroopType.PIKES, 2),
				army(TroopType.PIKES, 2), null);

		assertEquals(6, result.getPlayerPower());
		assertEquals(6, result.getEnemyPower());
		assertTrue(result.isDraw());
		assertFalse(result.isPlayerVictory());
	}

	@Test
	public void troopCountsAreMultipliedByBaseStrength() {
		CombatEngine.Result result = CombatEngine.compare(army(TroopType.ARCHERS, 7),
				army(TroopType.PIKES, 3), null);

		assertEquals(17, result.getPlayerPower());
		assertEquals(9, result.getEnemyPower());
		assertTrue(result.isPlayerVictory());
	}

	@Test
	public void levyBonusIsIncludedInPower() {
		CombatEngine.Result result = CombatEngine.compare(army(TroopType.LEVIES, 4),
				new Army(Collections.<TroopType, Integer>emptyMap()), null);

		assertEquals(5, result.getPlayerPower());
		assertEquals(0, result.getEnemyPower());
	}

	@Test
	public void pikesCounterCavalryAndCavalryIsCountered() {
		CombatEngine.Result result = CombatEngine.compare(army(TroopType.PIKES, 10),
				army(TroopType.ARMORED_HORSEMEN, 5), null);

		assertEquals(37, result.getPlayerPower());
		assertEquals(18, result.getEnemyPower());
	}

	@Test
	public void equippedItemModifierAppliesOnlyToPlayer() {
		Item pikeItem = new Item("pike-item", "Pike item", 1,
				TroopCategory.PIKEMEN, TroopCategory.ARCHERS);
		CombatEngine.Result result = CombatEngine.compare(army(TroopType.PIKES, 10),
				army(TroopType.PIKES, 10), pikeItem);

		assertEquals(33, result.getPlayerPower());
		assertEquals(30, result.getEnemyPower());
	}

	@Test
	public void counterBonusIsAppliedOnlyOnceForMultipleCounterTypes() {
		Army player = army(TroopType.ARMORED_HORSEMEN, 10);
		Army enemy = army(TroopType.ARCHERS, 2, TroopType.HUSCARLS, 2);

		CombatEngine.Result result = CombatEngine.compare(player, enemy, null);

		assertEquals(62, result.getPlayerPower());
	}

	@Test
	public void weakerPlayerDoesNotWin() {
		CombatEngine.Result result = CombatEngine.compare(army(TroopType.PIKES, 1),
				army(TroopType.ARMORED_HORSEMEN, 5), null);

		assertFalse(result.isDraw());
		assertFalse(result.isPlayerVictory());
		assertEquals(3, result.getPlayerPower());
		assertEquals(18, result.getEnemyPower());
	}

	@Test
	public void equalModifiedPowersRemainADraw() {
		Item pikeItem = new Item("pike-item", "Pike item", 1,
				TroopCategory.PIKEMEN, TroopCategory.ARCHERS);
		CombatEngine.Result result = CombatEngine.compare(army(TroopType.PIKES, 10),
				army(TroopType.PIKES, 11), pikeItem);

		assertEquals(33, result.getPlayerPower());
		assertEquals(33, result.getEnemyPower());
		assertTrue(result.isDraw());
	}

	private Army army(TroopType type, int count) {
		return new Army(Collections.singletonMap(type, count));
	}

	private Army army(TroopType firstType, int firstCount, TroopType secondType, int secondCount) {
		java.util.EnumMap<TroopType, Integer> troops =
				new java.util.EnumMap<TroopType, Integer>(TroopType.class);
		troops.put(firstType, firstCount);
		troops.put(secondType, secondCount);
		return new Army(troops);
	}
}
