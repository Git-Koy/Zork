package ch.bbw.zork;

import java.util.Arrays;
import java.util.List;

/**
 * Recognizes and lists the commands available in the game.
 *
 * @author Michael Kolling (template, 1999)
 * @author Rinaldo Lanza (refactoring, 2020)
 */

public class CommandWords {

	private final List<String> validCommands = Arrays.asList("go", "quit", "help", "back", "map",
		"pickup", "inventory", "drop", "start", "attack", "status", "troops", "switch", "equip");

	/**
	 * Checks whether the given word is a valid command.
	 *
	 * @param commandWord the word entered by the player
	 * @return true if the word is a known command, otherwise {@code false}
	 */
	public boolean isCommand(String commandWord) {
		return validCommands.stream()
				.filter( c -> c.equals(commandWord) )
				.count()>0;
	}

	/**
	 * Returns all valid commands as one string, separated by spaces.
	 *
	 * @return list of all available commands
	 */
	public String showAll() {
		return String.join(" ", validCommands);
	}

}