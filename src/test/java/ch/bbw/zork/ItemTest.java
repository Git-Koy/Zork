package ch.bbw.zork;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link Item} class.
 *
 * @author Koy
 */
public class ItemTest {

	private Item brick;

	/**
	 * Creates a fresh item before every test.
	 */
	@Before
	public void setUp() {
		brick = new Item("brick", "Just a brick", 1.5);
	}

	/**
	 * The name given in the constructor is returned.
	 */
	@Test
	public void getNameReturnsName() {
		assertEquals("brick", brick.getName());
	}

	/**
	 * The description given in the constructor is returned.
	 */
	@Test
	public void getDescriptionReturnsDescription() {
		assertEquals("Just a brick", brick.getDescription());
	}

	/**
	 * The weight given in the constructor is returned.
	 */
	@Test
	public void getWeightReturnsWeight() {
		assertEquals(1.5, brick.getWeight(), 0.001);
	}

	/**
	 * Whole numbers are stored as weights without losing precision.
	 */
	@Test
	public void wholeNumberWeightIsStored() {
		Item grail = new Item("holy-grail", "Holy Grail of Power", 100);
		assertEquals(100.0, grail.getWeight(), 0.001);
	}

	/**
	 * An item created without troop categories gives no bonus or penalty.
	 */
	@Test
	public void itemWithoutCategoriesHasNoModifier() {
		for (TroopCategory category : TroopCategory.values()) {
			assertEquals(0, brick.getPowerModifier(category));
		}
	}

	/**
	 * Two items with different values keep their own data.
	 */
	@Test
	public void differentItemsKeepTheirOwnValues() {
		Item sword = new Item("burnt-sword", "Burnt Sword of Fire", 5.6);
		assertEquals("brick", brick.getName());
		assertEquals("burnt-sword", sword.getName());
		assertEquals(5.6, sword.getWeight(), 0.001);
	}
}
