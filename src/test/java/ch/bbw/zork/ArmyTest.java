package ch.bbw.zork;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests army formation, unlocked troop types, and troop conversion.
 */
public class ArmyTest {

	@Test
	public void constructorKeepsPositiveStartingStacks() {
		Army army = new Army(troops(TroopType.PIKES, 12, TroopType.LEVIES, 15));

		assertEquals(12, army.getTroopCount(TroopType.PIKES));
		assertEquals(15, army.getTroopCount(TroopType.LEVIES));
	}

	@Test
	public void constructorIgnoresZeroAndNegativeStacks() {
		Army army = new Army(troops(TroopType.PIKES, 0, TroopType.LEVIES, -2));

		assertEquals(0, army.getTotalTroops());
		assertTrue(army.getUnlockedTroops().isEmpty());
	}

	@Test
	public void troopCountsAreReturnedAsADefensiveCopy() {
		Army army = new Army(troops(TroopType.PIKES, 12));
		Map<TroopType, Integer> copy = army.getTroops();
		copy.clear();

		assertEquals(12, army.getTroopCount(TroopType.PIKES));
	}

	@Test
	public void unlockedTypesAreReturnedAsADefensiveCopy() {
		Army army = new Army(troops(TroopType.PIKES, 12));
		Set<TroopType> copy = army.getUnlockedTroops();
		copy.clear();

		assertTrue(army.getUnlockedTroops().contains(TroopType.PIKES));
	}

	@Test
	public void missingTroopTypeHasZeroActiveSoldiers() {
		Army army = new Army(troops(TroopType.PIKES, 12));

		assertEquals(0, army.getTroopCount(TroopType.ARCHERS));
	}

	@Test
	public void totalCountsEveryActiveStack() {
		Army army = new Army(troops(TroopType.PIKES, 12, TroopType.LEVIES, 15));

		assertEquals(27, army.getTotalTroops());
	}

	@Test
	public void unlockingMakesNewTypeAvailableWithoutAddingSoldiers() {
		Army army = new Army(troops(TroopType.PIKES, 12));

		army.unlockTroop(TroopType.ARCHERS);
		army.unlockTroop(TroopType.ARCHERS);

		assertTrue(army.getUnlockedTroops().contains(TroopType.ARCHERS));
		assertEquals(12, army.getTotalTroops());
		assertEquals(0, army.getTroopCount(TroopType.ARCHERS));
	}

	@Test
	public void conversionAdjustsCountForStrengthAndAppliesLoss() {
		Army army = new Army(troops(TroopType.PIKES, 20));
		army.unlockTroop(TroopType.FOOTKNIGHTS);

		assertTrue(army.switchTroop(TroopType.PIKES, TroopType.FOOTKNIGHTS));
		assertEquals(0, army.getTroopCount(TroopType.PIKES));
		assertEquals(11, army.getTroopCount(TroopType.FOOTKNIGHTS));
	}

	@Test
	public void conversionFailsWhenSourceStackIsAbsent() {
		Army army = new Army(troops(TroopType.PIKES, 20));
		army.unlockTroop(TroopType.FOOTKNIGHTS);

		assertFalse(army.switchTroop(TroopType.ARCHERS, TroopType.FOOTKNIGHTS));
		assertEquals(20, army.getTroopCount(TroopType.PIKES));
	}

	@Test
	public void conversionFailsWhenTargetIsLocked() {
		Army army = new Army(troops(TroopType.PIKES, 20));

		assertFalse(army.switchTroop(TroopType.PIKES, TroopType.FOOTKNIGHTS));
		assertEquals(20, army.getTroopCount(TroopType.PIKES));
	}

	@Test
	public void conversionFailsWhenTargetIsAlreadyActive() {
		Army army = new Army(troops(TroopType.PIKES, 20, TroopType.LEVIES, 5));

		assertFalse(army.switchTroop(TroopType.PIKES, TroopType.LEVIES));
		assertEquals(20, army.getTroopCount(TroopType.PIKES));
		assertEquals(5, army.getTroopCount(TroopType.LEVIES));
	}

	@Test
	public void conversionFailsWhenResultWouldBeZero() {
		Army army = new Army(troops(TroopType.LEVIES, 1));
		army.unlockTroop(TroopType.ARMORED_HORSEMEN);

		assertFalse(army.switchTroop(TroopType.LEVIES, TroopType.ARMORED_HORSEMEN));
		assertEquals(1, army.getTroopCount(TroopType.LEVIES));
	}

	private Map<TroopType, Integer> troops(TroopType type, int count) {
		EnumMap<TroopType, Integer> result = new EnumMap<TroopType, Integer>(TroopType.class);
		result.put(type, count);
		return result;
	}

	private Map<TroopType, Integer> troops(TroopType firstType, int firstCount,
			TroopType secondType, int secondCount) {
		Map<TroopType, Integer> result = troops(firstType, firstCount);
		result.put(secondType, secondCount);
		return result;
	}
}
