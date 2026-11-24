package ch.bbw.zork;

/**
 * A parsed command word and its zero or more arguments.
 */
public class Command {
	private final String commandWord;
	private final String[] arguments;

	/**
	 * Creates a command with no arguments.
	 *
	 * @param commandWord command name, or {@code null} for an unknown command
	 */
	public Command(String commandWord) {
		this(commandWord, new String[0]);
	}

	/**
	 * Creates a command with an optional single argument.
	 *
	 * @param commandWord command name
	 * @param secondWord first argument, or {@code null}
	 */
	public Command(String commandWord, String secondWord) {
		this(commandWord, secondWord == null ? new String[0] : new String[] { secondWord });
	}

	/**
	 * Creates a command with an argument array.
	 *
	 * @param commandWord command name
	 * @param arguments command arguments, which are copied
	 */
	public Command(String commandWord, String[] arguments) {
		this.commandWord = commandWord;
		this.arguments = arguments.clone();
	}

	/**
	 * Returns the command name.
	 *
	 * @return command name, or {@code null} for an unknown command
	 */
	public String getCommandWord() {
		return commandWord;
	}

	/**
	 * Returns the first argument.
	 *
	 * @return first argument, or {@code null} if absent
	 */
	public String getSecondWord() {
		return arguments.length == 0 ? null : arguments[0];
	}

	/**
	 * Returns the second argument.
	 *
	 * @return second argument, or {@code null} if absent
	 */
	public String getThirdWord() {
		return arguments.length < 2 ? null : arguments[1];
	}

	/**
	 * Joins all arguments with spaces.
	 *
	 * @return joined argument text, or an empty string when there are no arguments
	 */
	public String getArgumentString() {
		return String.join(" ", arguments);
	}

	/**
	 * Returns the number of command arguments.
	 *
	 * @return argument count
	 */
	public int getArgumentCount() {
		return arguments.length;
	}

	/**
	 * Indicates whether the command name is unknown.
	 *
	 * @return {@code true} when the command name is {@code null}
	 */
	public boolean isUnknown() {
		return commandWord == null;
	}

	/**
	 * Indicates whether at least one argument is present.
	 *
	 * @return {@code true} when an argument exists
	 */
	public boolean hasSecondWord() {
		return arguments.length > 0;
	}

	/**
	 * Indicates whether a second argument is present.
	 *
	 * @return {@code true} when at least two arguments exist
	 */
	public boolean hasThirdWord() {
		return arguments.length > 1;
	}
}
