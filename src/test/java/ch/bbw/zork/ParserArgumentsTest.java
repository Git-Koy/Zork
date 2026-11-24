package ch.bbw.zork;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

/**
 * Tests parsing of combat commands and their arguments.
 */
public class ParserArgumentsTest {

	@Test
	public void parsesCommandWithoutArguments() {
		Command command = parser("attack\n").getCommand();

		assertEquals("attack", command.getCommandWord());
		assertEquals(0, command.getArgumentCount());
	}

	@Test
	public void parsesMultipleArguments() {
		Command command = parser("switch pikes archers\n").getCommand();

		assertEquals("switch", command.getCommandWord());
		assertEquals("pikes", command.getSecondWord());
		assertEquals("archers", command.getThirdWord());
	}

	@Test
	public void trimsLeadingTrailingAndRepeatedWhitespace() {
		Command command = parser("   start   East   Francia  \n").getCommand();

		assertEquals("East Francia", command.getArgumentString());
	}

	@Test
	public void blankInputReturnsUnknownCommand() {
		assertTrue(parser("   \n").getCommand().isUnknown());
	}

	@Test
	public void unrecognizedCommandReturnsUnknownCommand() {
		assertTrue(parser("conquer alba\n").getCommand().isUnknown());
	}

	@Test
	public void endOfInputReturnsQuitCommand() {
		Command command = parser("").getCommand();

		assertEquals("quit", command.getCommandWord());
	}

	@Test
	public void helpCommandListIncludesCombatCommands() {
		String commands = parser("").showCommands();

		assertTrue(commands.contains("start"));
		assertTrue(commands.contains("attack"));
		assertTrue(commands.contains("status"));
		assertTrue(commands.contains("troops"));
		assertTrue(commands.contains("switch"));
		assertTrue(commands.contains("equip"));
	}

	@Test
	public void recognizedCommandWithExtraWordsPreservesAllArguments() {
		Command command = parser("start East Francia\n").getCommand();

		assertEquals(2, command.getArgumentCount());
		assertEquals("East Francia", command.getArgumentString());
	}

	@Test
	public void readFailureReturnsUnknownCommand() {
		InputStream failingStream = new InputStream() {
			@Override
			public int read() throws IOException {
				throw new IOException("forced read failure");
			}
		};

		assertTrue(new Parser(failingStream).getCommand().isUnknown());
	}

	private Parser parser(String input) {
		return new Parser(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
	}
}
