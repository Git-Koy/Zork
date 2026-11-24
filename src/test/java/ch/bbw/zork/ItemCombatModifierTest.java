package ch.bbw.zork;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests combat modifiers added to inventory items.
 */
public class ItemCombatModifierTest {

	@Test
	public void bonusCategoryReceivesTenPercent() {
		Item item = item(TroopCategory.PIKEMEN, TroopCategory.ARCHERS);

		assertEquals(10, item.getPowerModifier(TroopCategory.PIKEMEN));
	}

	@Test
	public void penaltyCategoryReceivesFivePercentPenalty() {
		Item item = item(TroopCategory.PIKEMEN, TroopCategory.ARCHERS);

		assertEquals(-5, item.getPowerModifier(TroopCategory.ARCHERS));
	}

	@Test
	public void unrelatedCategoryHasNoModifier() {
		Item item = item(TroopCategory.PIKEMEN, TroopCategory.ARCHERS);

		assertEquals(0, item.getPowerModifier(TroopCategory.LEVIES));
	}

	@Test
	public void itemWithoutCombatCategoriesHasNoModifier() {
		Item item = new Item("brick", "Just a brick", 1);

		for (TroopCategory category : TroopCategory.values()) {
			assertEquals(0, item.getPowerModifier(category));
		}
	}

	@Test
	public void nullCategoryDoesNotMatchAnAbsentModifier() {
		Item item = new Item("brick", "Just a brick", 1);

		assertEquals(0, item.getPowerModifier(null));
	}

	@Test
	public void nullBonusCategoryDoesNotMatchNullLookup() {
		Item item = new Item("archer-token", "Archer token", 1, null, TroopCategory.CAVALRY);

		assertEquals(0, item.getPowerModifier(null));
		assertEquals(-5, item.getPowerModifier(TroopCategory.CAVALRY));
	}

	@Test
	public void combatModifiersDoNotAlterItemIdentityOrWeight() {
		Item item = new Item("sword", "Burnt Sword", 5.6,
				TroopCategory.CAVALRY, TroopCategory.PIKEMEN);

		assertEquals("sword", item.getName());
		assertEquals("Burnt Sword", item.getDescription());
		assertEquals(5.6, item.getWeight(), 0.001);
	}

	@Test
	public void eachConfiguredCategoryKeepsItsOwnEffect() {
		Item item = item(TroopCategory.CAVALRY, TroopCategory.HEAVY_INFANTRY);

		assertEquals(10, item.getPowerModifier(TroopCategory.CAVALRY));
		assertEquals(-5, item.getPowerModifier(TroopCategory.HEAVY_INFANTRY));
		assertEquals(0, item.getPowerModifier(TroopCategory.ARCHERS));
	}

	private Item item(TroopCategory bonus, TroopCategory penalty) {
		return new Item("test-item", "Test item", 1, bonus, penalty);
	}
}
