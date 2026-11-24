package ch.bbw.zork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Reads terminal input and converts recognized commands into {@link Command}
 * instances.
 */
public class Parser {
	private final CommandWords validCommandWords;
	private final BufferedReader bufferedReader;

	/**
	 * Creates a parser that reads from the supplied stream.
	 *
	 * @param inputStream stream containing command lines
	 */
	public Parser(InputStream inputStream) {
		this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		this.validCommandWords = new CommandWords();
	}

	/**
	 * Reads and parses one command line.
	 *
	 * @return parsed command, or an unknown command when input is blank,
	 *         invalid, or cannot be read; end-of-input produces {@code quit}
	 */
	public Command getCommand() {
		System.out.print("> ");
		try {
			String inputLine = bufferedReader.readLine();
			if (inputLine == null) {
				return new Command("quit");
			}
			if (inputLine.trim().isEmpty()) {
				return new Command(null);
			}
			String[] tokens = inputLine.trim().split("\\s+");
			String commandWord = tokens[0];
			if (!validCommandWords.isCommand(commandWord)) {
				return new Command(null);
			}
			return new Command(commandWord, Arrays.copyOfRange(tokens, 1, tokens.length));
		} catch (IOException exc) {
			System.out.println("There was an error during reading: " + exc.getMessage());
			return new Command(null);
		}
	}

	/**
	 * Returns the recognized command words for the help display.
	 *
	 * @return space-separated command names
	 */
	public String showCommands() {
		return validCommandWords.showAll();
	}
}
