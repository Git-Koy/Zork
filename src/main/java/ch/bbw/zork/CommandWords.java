package ch.bbw.zork;

/**
 * Recognizes and lists the commands available in the game.
 *
 * @author Michael Kolling (template, 1999)
 * @author Rinaldo Lanza (refactoring, 2020)
 */

import java.util.Arrays;
import java.util.List;

public class CommandWords {

	private final List<String> validCommands = Arrays.asList("go", "quit", "help", "back", "map",
		"pickup", "inventory", "drop", "start", "attack", "status", "troops", "switch", "equip");

	public boolean isCommand(String commandWord) {
		return validCommands.stream()
				.filter( c -> c.equals(commandWord) )
				.count()>0;
	}

	public String showAll() {
		return String.join(" ", validCommands);
	}

}