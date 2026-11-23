package ch.bbw.zork;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class Game - the main class of the "Zork" game.
 *
 * Author:  Michael Kolling, 1.1, March 2000
 * refactoring: Rinaldo Lanza, September 2020
 */

public class Game {

	private Parser parser;
	private Room currentRoom;
	private Room alba, ireland, wales, england, france, denmark, east_francia, croatia, bohemia;
	private Kingdom currentKingdom;
	private Army playerArmy;
	private GameItem equippedItem;
	private Set<Kingdom> conquered = EnumSet.noneOf(Kingdom.class);

	public Game() {

		parser = new Parser(System.in);

		alba = new Room("Kingdom of Alba.");
		ireland = new Room("Kingdom of Ireland.");
		wales = new Room("Kingdom of Wales");
		england = new Room("Kingdom of England.");
		france = new Room("Kingdom of France.");
		denmark = new Room("Kingdom of Denmark.");
		east_francia = new Room("Kingdom of East Francia.");
		croatia = new Room("Kingdom of Croatia.");
		bohemia = new Room("Kingdom of Bohemia.");

		initializeWorld();
	}

	/**
	 *  Main play routine.  Loops until end of play.
	 */
	public void play() {
		printWelcome();

		// Enter the main command loop.  Here we repeatedly read commands and
		// execute them until the game is over.
		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing.  Good bye.");
	}

	private void initializeWorld() {
		connect(Kingdom.IRELAND, "east", Kingdom.WALES, "west");
		connect(Kingdom.WALES, "north", Kingdom.ALBA, "south");
		connect(Kingdom.WALES, "east", Kingdom.ENGLAND, "west");
		connect(Kingdom.ENGLAND, "south", Kingdom.FRANCE, "north");
		connect(Kingdom.FRANCE, "east", Kingdom.EAST_FRANCIA, "west");
		connect(Kingdom.DENMARK, "south", Kingdom.EAST_FRANCIA, "north");
		connect(Kingdom.EAST_FRANCIA, "east", Kingdom.BOHEMIA, "west");
		connect(Kingdom.EAST_FRANCIA, "south", Kingdom.CROATIA, "north");
	}

	private void connect(Kingdom first, String firstDirection, Kingdom second, String secondDirection) {
		roomFor(first).setExit(firstDirection, roomFor(second));
		roomFor(second).setExit(secondDirection, roomFor(first));
	}

	private Room roomFor(Kingdom kingdom) {
		switch (kingdom) {
		case ALBA:
			return alba;
		case IRELAND:
			return ireland;
		case WALES:
			return wales;
		case ENGLAND:
			return england;
		case FRANCE:
			return france;
		case DENMARK:
			return denmark;
		case EAST_FRANCIA:
			return east_francia;
		case CROATIA:
			return croatia;
		default:
			return bohemia;
		}
	}

	private Kingdom kingdomFor(Room room) {
		for (Kingdom kingdom : Kingdom.values()) {
			if (roomFor(kingdom) == room) {
				return kingdom;
			}
		}
		return null;
	}

	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to Zork!");
		System.out.println("Zork is a simple adventure game.");
		System.out.println("Type 'help' if you need help.");
		System.out.println();
		System.out.println("Choose your starting kingdom with: start <kingdom>");
	}

	private boolean processCommand(Command command) {
		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help")) {
			printHelp();

		} else if (commandWord.equals("go")) {
			goRoom(command);

		} else if (commandWord.equals("quit")) {
			if (command.hasSecondWord()) {
				System.out.println("Quit what?");
			} else {
				return true; // signal that we want to quit
			}
		} else if (commandWord.equals("start")) {
			startGame(command);
		} else if (commandWord.equals("attack")) {
			attackCurrentKingdom(command);
		} else if (commandWord.equals("status")) {
			printStatus();
		} else if (commandWord.equals("troops")) {
			printTroops();
		} else if (commandWord.equals("switch")) {
			switchTroops(command);
		} else if (commandWord.equals("items")) {
			printItems();
		} else if (commandWord.equals("take")) {
			takeItem(command);
		} else if (commandWord.equals("map")) {
			printMap();
		}
		return false;
	}

	private void printHelp() {
		System.out.println();
		System.out.println("Your command words are:");
		System.out.println(parser.showCommands());
	}

	private void startGame(Command command) {
		if (playerArmy != null) {
			System.out.println("You have already chosen a starting kingdom.");
			return;
		}
		Kingdom startingKingdom = Kingdom.fromName(command.getArgumentString());
		if (startingKingdom == null) {
			System.out.println("Choose one of: " + kingdomNames() + ".");
			return;
		}
		currentKingdom = startingKingdom;
		currentRoom = roomFor(startingKingdom);
		playerArmy = new Army(startingKingdom.getTroops());
		conquered.add(startingKingdom);
		System.out.println(currentRoom.longDescription());
		System.out.println("Your army is ready. Use 'status' to inspect it.");
	}

	private String kingdomNames() {
		List<String> names = new ArrayList<String>();
		for (Kingdom kingdom : Kingdom.values()) {
			names.add(kingdom.getDisplayName());
		}
		return String.join(", ", names);
	}

	private void goRoom(Command command) {
		if (playerArmy == null) {
			System.out.println("Choose a starting kingdom first with 'start <kingdom>'.");
			return;
		}
		if (command.getArgumentCount() != 1) {
			System.out.println("Go where?");
			return;
		}

		String direction = command.getSecondWord();

		// Try to leave current room.
		Room nextRoom = currentRoom.nextRoom(direction);

		if (nextRoom == null) {
			System.out.println("There is no door!");
		} else {
			currentRoom = nextRoom;
			currentKingdom = kingdomFor(currentRoom);
			System.out.println(currentRoom.longDescription());
			System.out.println(conquered.contains(currentKingdom) ? "This kingdom is under your control."
					: "This kingdom is unconquered. Use 'attack' to fight.");
		}
	}

	private void attackCurrentKingdom(Command command) {
		if (playerArmy == null) {
			System.out.println("Choose a starting kingdom first.");
			return;
		}
		if (command.getArgumentCount() != 0) {
			System.out.println("Use: attack");
			return;
		}
		if (conquered.contains(currentKingdom)) {
			System.out.println("You already control this kingdom.");
			return;
		}

		Kingdom enemyKingdom = currentKingdom;
		Army enemy = new Army(enemyKingdom.getTroops());
		CombatEngine.Result result = CombatEngine.compare(playerArmy, enemy, equippedItem);
		System.out.println("Your army strength: " + result.getPlayerPower());
		System.out.println(enemyKingdom.getDisplayName() + " army strength: " + result.getEnemyPower());

		if (result.isDraw()) {
			System.out.println("The battle is a draw. Switch troop types or equip another item and try again.");
			return;
		}
		if (!result.isPlayerVictory()) {
			System.out.println("The defending kingdom wins.");
			return;
		}

		conquered.add(enemyKingdom);
		for (TroopType type : enemyKingdom.getTroopTypes()) {
			playerArmy.unlockTroop(type);
		}
		System.out.println(enemyKingdom.getDisplayName() + " has been conquered.");
		if (conquered.size() == Kingdom.values().length) {
			System.out.println("You have conquered every kingdom. You win!");
		}
	}

	private void printStatus() {
		if (playerArmy == null) {
			System.out.println("Choose a starting kingdom first.");
			return;
		}
		System.out.println("Location: " + currentKingdom.getDisplayName()
				+ (conquered.contains(currentKingdom) ? " (controlled)" : " (unconquered)"));
		System.out.println("Army: " + playerArmy.getTotalTroops() + " soldiers in "
				+ playerArmy.getTroops().size() + " troop types.");
		System.out.println("Conquered: " + conquered.size() + "/" + Kingdom.values().length + ".");
		System.out.println("Equipped item: "
				+ (equippedItem == null ? "none" : equippedItem.getDisplayName()));
	}

	private void printTroops() {
		if (playerArmy == null) {
			System.out.println("Choose a starting kingdom first.");
			return;
		}
		System.out.println("Your troops:");
		for (Map.Entry<TroopType, Integer> troop : playerArmy.getTroops().entrySet()) {
			System.out.println("  " + troop.getKey().getDisplayName() + ": " + troop.getValue());
		}
		System.out.println("Unlocked types: " + troopNames(playerArmy.getUnlockedTroops()));
	}

	private String troopNames(Set<TroopType> troopTypes) {
		List<String> names = new ArrayList<String>();
		for (TroopType type : troopTypes) {
			names.add(type.getDisplayName());
		}
		return String.join(", ", names);
	}

	private void switchTroops(Command command) {
		if (playerArmy == null) {
			System.out.println("Choose a starting kingdom first.");
			return;
		}
		if (command.getArgumentCount() != 2) {
			System.out.println("Use: switch <old_type> <new_type> (use underscores in multiword names).");
			return;
		}
		TroopType from = TroopType.fromName(command.getSecondWord());
		TroopType to = TroopType.fromName(command.getThirdWord());
		if (from == null || to == null || !playerArmy.switchTroop(from, to)) {
			System.out.println("That troop switch is not available. Use 'troops' to see unlocked types.");
			return;
		}
		System.out.println("Your troops are now using " + to.getDisplayName() + ".");
	}

	private void printItems() {
		if (playerArmy == null) {
			System.out.println("Choose a starting kingdom first.");
			return;
		}
		System.out.println("Equipped item: "
				+ (equippedItem == null ? "none" : describeItem(equippedItem)));
		GameItem localItem = currentKingdom.getItem();
		if (conquered.contains(currentKingdom) && equippedItem != localItem) {
			System.out.println("Available here: " + localItem.getId() + " - "
					+ describeItem(localItem));
		}
	}

	private void takeItem(Command command) {
		if (playerArmy == null || !conquered.contains(currentKingdom)) {
			System.out.println("Items can only be changed in a kingdom you control.");
			return;
		}
		if (command.getArgumentCount() != 1) {
			System.out.println("Use: take <item_id>.");
			return;
		}
		GameItem item = GameItem.fromId(command.getSecondWord());
		if (item == null || item != currentKingdom.getItem()) {
			System.out.println("That item is not available here. Use 'items' to see it.");
			return;
		}
		equippedItem = item;
		System.out.println("Equipped " + describeItem(item) + ".");
	}

	private String describeItem(GameItem item) {
		List<String> effects = new ArrayList<String>();
		for (TroopCategory category : TroopCategory.values()) {
			int modifier = item.getPowerModifier(category);
			if (modifier != 0) {
				effects.add((modifier > 0 ? "+" : "") + modifier + "% "
						+ category.name().toLowerCase().replace('_', ' ') + " power");
			}
		}
		return item.getDisplayName() + " (" + String.join(", ", effects) + ")";
	}

	private void printMap() {
		if (playerArmy == null) {
			System.out.println("Choose a starting kingdom first.");
			return;
		}
		System.out.println("Nearby kingdoms:");
		for (String direction : new String[] { "north", "east", "south", "west" }) {
			Room neighbor = currentRoom.nextRoom(direction);
			if (neighbor != null) {
				Kingdom kingdom = kingdomFor(neighbor);
				System.out.println("  " + direction + ": " + kingdom.getDisplayName()
						+ (conquered.contains(kingdom) ? " (controlled)" : " (unconquered)"));
			}
		}
	}
}
