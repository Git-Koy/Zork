package ch.bbw.zork;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

/**
 * Tests kingdom lookup and defensive access to initial troop formations.
 */
public class KingdomCombatTest {

	@Test
	public void everyKingdomHasAReadableNameAndTroopFormation() {
		for (Kingdom kingdom : Kingdom.values()) {
			assertTrue(kingdom.getDisplayName().length() > 0);
			assertTrue(kingdom.getTroops().size() > 0);
		}
	}

	@Test
	public void kingdomCountIncludesBavaria() {
		assertEquals(10, Kingdom.values().length);
		assertSame(Kingdom.BAVARIA, Kingdom.fromName("Bavaria"));
	}

	@Test
	public void lookupAcceptsDisplayNamesIgnoringCase() {
		assertSame(Kingdom.EAST_FRANCIA, Kingdom.fromName("eAsT fRaNcIa"));
		assertSame(Kingdom.ALBA, Kingdom.fromName("ALBA"));
	}

	@Test
	public void lookupAcceptsUnderscoresAndHyphens() {
		assertSame(Kingdom.EAST_FRANCIA, Kingdom.fromName("east_francia"));
		assertSame(Kingdom.EAST_FRANCIA, Kingdom.fromName("east-francia"));
	}

	@Test
	public void lookupAcceptsEnumIdentifiers() {
		assertSame(Kingdom.EAST_FRANCIA, Kingdom.fromName("EAST_FRANCIA"));
		assertSame(Kingdom.CROATIA, Kingdom.fromName("CROATIA"));
	}

	@Test
	public void lookupTrimsSurroundingWhitespace() {
		assertSame(Kingdom.FRANCE, Kingdom.fromName("  France  "));
	}

	@Test
	public void lookupRejectsNullAndUnknownNames() {
		assertNull(Kingdom.fromName(null));
		assertNull(Kingdom.fromName("Atlantis"));
	}

	@Test
	public void troopTypesAreReturnedAsADefensiveCopy() {
		TroopType[] first = Kingdom.ALBA.getTroopTypes();
		first[0] = TroopType.ARCHERS;

		assertEquals(TroopType.PIKES, Kingdom.ALBA.getTroopTypes()[0]);
		assertNotSame(first, Kingdom.ALBA.getTroopTypes());
	}

	@Test
	public void troopFormationMapsAreIndependentCopies() {
		Map<TroopType, Integer> first = Kingdom.ALBA.getTroops();
		first.clear();

		assertEquals(2, Kingdom.ALBA.getTroops().size());
		assertEquals(Integer.valueOf(12), Kingdom.ALBA.getTroops().get(TroopType.PIKES));
	}

	@Test
	public void startingFormationsHavePositiveCountsForEveryKingdom() {
		for (Kingdom kingdom : Kingdom.values()) {
			for (Integer count : kingdom.getTroops().values()) {
				assertTrue(kingdom.name(), count > 0);
			}
		}
	}
}
