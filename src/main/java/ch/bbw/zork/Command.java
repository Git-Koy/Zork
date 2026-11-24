package ch.bbw.zork;

public class Command {
	private final String commandWord;
	private final String[] arguments;

	public Command(String commandWord) {
		this(commandWord, new String[0]);
	}

	public Command(String commandWord, String secondWord) {
		this(commandWord, secondWord == null ? new String[0] : new String[] { secondWord });
	}

	public Command(String commandWord, String[] arguments) {
		this.commandWord = commandWord;
		this.arguments = arguments.clone();
	}

	public String getCommandWord() {
		return commandWord;
	}

	public String getSecondWord() {
		return arguments.length == 0 ? null : arguments[0];
	}

	public String getThirdWord() {
		return arguments.length < 2 ? null : arguments[1];
	}

	public String getArgumentString() {
		return String.join(" ", arguments);
	}

	public int getArgumentCount() {
		return arguments.length;
	}

	public boolean isUnknown() {
		return commandWord == null;
	}

	public boolean hasSecondWord() {
		return arguments.length > 0;
	}

	public boolean hasThirdWord() {
		return arguments.length > 1;
	}
}
