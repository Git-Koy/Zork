package ch.bbw.zork;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link Player} class: carrying, weight limit and dropping items.
 * The maximum carry weight is 101 kg.
 *
 * @author Koy
 */
public class PlayerTest {

	private Player player;
	private Item brick;
	private Item grail;

	/**
	 * Creates a fresh player and items before every test.
	 */
	@Before
	public void setUp() {
		player = new Player();
		brick = new Item("brick", "Just a brick", 1);
		grail = new Item("holy-grail", "Holy Grail of Power", 100);
	}

	/**
	 * A new player carries nothing.
	 */
	@Test
	public void newPlayerHasEmptyInventory() {
		assertTrue(player.getItems().isEmpty());
		assertEquals("Your inventory is empty (0 kg).", player.inventoryString());
	}

	/**
	 * A light item is added to the inventory.
	 */
	@Test
	public void addItemWithinLimitSucceeds() {
		assertTrue(player.addItem(brick));
		assertTrue(player.getItems().contains(brick));
	}

	/**
	 * Items up to exactly the maximum weight can be carried.
	 */
	@Test
	public void itemsUpToMaxWeightFit() {
		assertTrue(player.addItem(grail));
		assertTrue(player.addItem(brick));
		assertEquals(2, player.getItems().size());
	}

	/**
	 * An item that exceeds the maximum weight is refused and not added.
	 */
	@Test
	public void addItemOverLimitFails() {
		player.addItem(grail);
		Item sword = new Item("burnt-sword", "Burnt Sword of Fire", 5.6);
		assertFalse(player.addItem(sword));
		assertFalse(player.getItems().contains(sword));
	}

	/**
	 * Removing a carried item works and returns true.
	 */
	@Test
	public void removeCarriedItemSucceeds() {
		player.addItem(brick);
		assertTrue(player.removeItem(brick));
		assertTrue(player.getItems().isEmpty());
	}

	/**
	 * Removing an item the player does not carry returns false.
	 */
	@Test
	public void removeUnknownItemFails() {
		assertFalse(player.removeItem(brick));
	}

	/**
	 * After dropping an item its weight is freed again.
	 */
	@Test
	public void weightIsFreedAfterRemove() {
		player.addItem(grail);
		player.removeItem(grail);
		Item anvil = new Item("anvil", "Heavy anvil", 100);
		assertTrue(player.addItem(anvil));
	}

	/**
	 * Several items are listed with commas, "and" and the total weight.
	 */
	@Test
	public void inventoryStringListsAllItems() {
		player.addItem(brick);
		player.addItem(new Item("sun-shield", "Sun Shield of Light", 7.5));
		player.addItem(new Item("skeleton-crown", "Skeleton Crown of Conjuring", 3.5));
		assertEquals("Your inventory has Just a brick, Sun Shield of Light and "
				+ "Skeleton Crown of Conjuring (12.0 kg).", player.inventoryString());
	}
}
