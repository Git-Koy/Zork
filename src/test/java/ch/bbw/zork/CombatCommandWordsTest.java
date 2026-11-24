package ch.bbw.zork;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests registration of kingdom-combat commands.
 */
public class CombatCommandWordsTest {

	private final CommandWords commandWords = new CommandWords();

	@Test
	public void startIsRecognized() {
		assertTrue(commandWords.isCommand("start"));
	}

	@Test
	public void attackIsRecognized() {
		assertTrue(commandWords.isCommand("attack"));
	}

	@Test
	public void statusIsRecognized() {
		assertTrue(commandWords.isCommand("status"));
	}

	@Test
	public void troopsIsRecognized() {
		assertTrue(commandWords.isCommand("troops"));
	}

	@Test
	public void switchIsRecognized() {
		assertTrue(commandWords.isCommand("switch"));
	}

	@Test
	public void equipIsRecognized() {
		assertTrue(commandWords.isCommand("equip"));
	}

	@Test
	public void existingMovementAndInventoryCommandsRemainRecognized() {
		assertTrue(commandWords.isCommand("go"));
		assertTrue(commandWords.isCommand("pickup"));
		assertTrue(commandWords.isCommand("inventory"));
		assertTrue(commandWords.isCommand("drop"));
	}

	@Test
	public void unknownAndUppercaseCommandsAreRejected() {
		assertFalse(commandWords.isCommand("invade"));
		assertFalse(commandWords.isCommand("ATTACK"));
	}
}
