package ch.bbw.zork;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link Room} class: neighbours and items in a country.
 *
 * @author Koy
 */
public class RoomTest {

	private Room alba;
	private Room england;
	private Room france;

	/**
	 * Creates fresh countries before every test.
	 */
	@Before
	public void setUp() {
		alba = new Room("alba", "Kingdom of Alba");
		england = new Room("england", "Kingdom of England");
		france = new Room("france", "Kingdom of France");
	}

	/**
	 * Id and name given in the constructor are returned.
	 */
	@Test
	public void constructorSetsCountryAndDescription() {
		assertEquals("alba", alba.getCountry());
		assertEquals("Kingdom of Alba", alba.getDescription());
		assertEquals("Kingdom of Alba", alba.shortDescription());
	}

	/**
	 * addNeighbour connects both countries in both directions.
	 */
	@Test
	public void addNeighbourConnectsBothWays() {
		alba.addNeighbour(england);
		assertSame(england, alba.nextRoom("england"));
		assertSame(alba, england.nextRoom("alba"));
	}

	/**
	 * A country that is not a neighbour cannot be reached.
	 */
	@Test
	public void nextRoomReturnsNullForNonNeighbour() {
		alba.addNeighbour(england);
		assertNull(alba.nextRoom("france"));
		assertNull(alba.nextRoom("does-not-exist"));
	}

	/**
	 * Added items are in the country, removed items are gone.
	 */
	@Test
	public void addAndRemoveItem() {
		Item brick = new Item("brick", "Just a brick", 1);
		france.addItem(brick);
		assertTrue(france.getItems().contains(brick));
		france.removeItem(brick);
		assertFalse(france.getItems().contains(brick));
	}

	/**
	 * A country without items says so.
	 */
	@Test
	public void itemStringWithoutItems() {
		assertEquals(" which has no item", alba.itemString());
	}

	/**
	 * A country with one item names it.
	 */
	@Test
	public void itemStringWithOneItem() {
		alba.addItem(new Item("brick", "Just a brick", 1));
		assertEquals(" which has brick", alba.itemString());
	}

	/**
	 * Several items are listed with commas and "and".
	 */
	@Test
	public void itemStringWithThreeItems() {
		alba.addItem(new Item("holy-grail", "Holy Grail of Power", 100));
		alba.addItem(new Item("skeleton-crown", "Skeleton Crown of Conjuring", 3.5));
		alba.addItem(new Item("sun-shield", "Sun Shield of Light", 7.5));
		assertEquals(" which has the items holy-grail, skeleton-crown and sun-shield", alba.itemString());
	}

	/**
	 * The long description contains the name and the neighbours.
	 */
	@Test
	public void longDescriptionShowsNameAndNeighbours() {
		alba.addNeighbour(england);
		String description = alba.longDescription();
		assertTrue(description.startsWith("You are in Kingdom of Alba"));
		assertTrue(description.contains("Neighbours: england"));
	}
}
