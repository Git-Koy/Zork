package ch.bbw.zork;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests multi-argument commands used by kingdom and troop commands.
 */
public class CommandArgumentsTest {

	@Test
	public void singleArgumentConstructorKeepsItsArgument() {
		Command command = new Command("start", "Ireland");

		assertEquals("start", command.getCommandWord());
		assertEquals("Ireland", command.getSecondWord());
		assertEquals("Ireland", command.getArgumentString());
		assertEquals(1, command.getArgumentCount());
	}

	@Test
	public void nullSingleArgumentActsLikeNoArguments() {
		Command command = new Command("help", (String) null);

		assertFalse(command.hasSecondWord());
		assertFalse(command.hasThirdWord());
		assertEquals("", command.getArgumentString());
	}

	@Test
	public void argumentArrayProvidesMultipleWords() {
		Command command = new Command("switch", new String[] { "pikes", "archers" });

		assertEquals("pikes", command.getSecondWord());
		assertEquals("archers", command.getThirdWord());
		assertEquals("pikes archers", command.getArgumentString());
		assertEquals(2, command.getArgumentCount());
	}

	@Test
	public void argumentArrayIsCopiedOnConstruction() {
		String[] arguments = { "east", "francia" };
		Command command = new Command("start", arguments);
		arguments[0] = "changed";

		assertEquals("east", command.getSecondWord());
	}

	@Test
	public void commandWithoutArgumentsHasNullWordAccessors() {
		Command command = new Command("attack");

		assertNull(command.getSecondWord());
		assertNull(command.getThirdWord());
		assertEquals(0, command.getArgumentCount());
	}

	@Test
	public void unknownCommandIsRecognized() {
		assertTrue(new Command(null).isUnknown());
		assertFalse(new Command("go").isUnknown());
	}

	@Test
	public void wordPresenceChecksUseArgumentCount() {
		assertFalse(new Command("start").hasSecondWord());
		assertTrue(new Command("start", new String[] { "Alba" }).hasSecondWord());
		assertFalse(new Command("start", new String[] { "Alba" }).hasThirdWord());
		assertTrue(new Command("switch", new String[] { "pikes", "archers" }).hasThirdWord());
	}

	@Test
	public void arbitraryArgumentCountIsReported() {
		Command command = new Command("custom", new String[] { "one", "two", "three" });

		assertEquals(3, command.getArgumentCount());
		assertEquals("one", command.getSecondWord());
		assertEquals("two", command.getThirdWord());
		assertEquals("one two three", command.getArgumentString());
	}
}
