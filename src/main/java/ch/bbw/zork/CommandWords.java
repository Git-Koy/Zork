package ch.bbw.zork;

import java.util.Arrays;
import java.util.List;

public class CommandWords {
	private final List<String> validCommands = Arrays.asList("go", "quit", "help", "start",
			"attack", "status", "troops", "switch", "items", "take", "map");

	public boolean isCommand(String commandWord) {
		return validCommands.contains(commandWord);
	}

	public String showAll() {
		return String.join(" ", validCommands);
	}
}
