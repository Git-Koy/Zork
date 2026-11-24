package ch.bbw.zork;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests troop-name lookup and the combat counter relationships.
 */
public class TroopTypeCombatTest {

	@Test
	public void cavalryCountersArchers() {
		assertTrue(TroopType.ARMORED_HORSEMEN.counters(TroopType.ARCHERS));
		assertTrue(TroopType.LIGHT_HORSEMEN.counters(TroopType.LONGBOWS));
	}

	@Test
	public void cavalryCountersHeavyInfantry() {
		assertTrue(TroopType.ARMORED_HORSEMEN.counters(TroopType.HUSCARLS));
		assertTrue(TroopType.LIGHT_HORSEMEN.counters(TroopType.FOOTKNIGHTS));
	}

	@Test
	public void pikemenCounterCavalry() {
		assertTrue(TroopType.PIKES.counters(TroopType.ARMORED_HORSEMEN));
		assertFalse(TroopType.PIKES.counters(TroopType.ARCHERS));
	}

	@Test
	public void heavyInfantryCountersPikemen() {
		assertTrue(TroopType.HUSCARLS.counters(TroopType.PIKES));
		assertTrue(TroopType.FOOTKNIGHTS.counters(TroopType.PIKES));
	}

	@Test
	public void archersCounterPikemen() {
		assertTrue(TroopType.ARCHERS.counters(TroopType.PIKES));
		assertTrue(TroopType.LONGBOWS.counters(TroopType.PIKES));
	}

	@Test
	public void leviesDoNotCounterOtherTroops() {
		for (TroopType type : TroopType.values()) {
			assertFalse(TroopType.LEVIES.counters(type));
		}
	}

	@Test
	public void lookupAcceptsDisplayNamesAndAlternateSeparators() {
		assertSame(TroopType.ARMORED_FOOTMEN, TroopType.fromName("Armored Footmen"));
		assertSame(TroopType.ARMORED_FOOTMEN, TroopType.fromName("armored_footmen"));
		assertSame(TroopType.ARMORED_FOOTMEN, TroopType.fromName("ARMORED-FOOTMEN"));
	}

	@Test
	public void lookupAcceptsEnumIdentifiers() {
		assertSame(TroopType.ARMORED_HORSEMEN, TroopType.fromName("ARMORED_HORSEMEN"));
	}

	@Test
	public void lookupTrimsWhitespace() {
		assertSame(TroopType.FOOTKNIGHTS, TroopType.fromName("  Footknights  "));
	}

	@Test
	public void lookupIsCaseInsensitiveForSingleWordNames() {
		assertSame(TroopType.LEVIES, TroopType.fromName("lEvIeS"));
	}

	@Test
	public void lookupRejectsNullAndUnknownTroopNames() {
		assertNull(TroopType.fromName(null));
		assertNull(TroopType.fromName("Dragon"));
	}
}
