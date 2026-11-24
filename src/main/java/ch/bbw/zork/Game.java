package ch.bbw.zork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
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
	private Room lastRoom;
	private Room alba, ireland, wales, england, france, denmark, east_francia, croatia, bohemia, bavaria, ocean;
	private Item holy_grail, skeleton_crown, sun_shield, brick, burnt_sword;
	private List<Room> rooms;
	private Player player;
	private Kingdom currentKingdom;
	private Army playerArmy;
	private Item equippedItem;
	private Set<Kingdom> conquered = EnumSet.noneOf(Kingdom.class);

	public Game() {
		parser = new Parser(System.in);

		alba = new Room("alba", "Kingdom of Alba");
		ireland = new Room("ireland", "Kingdom of Ireland");
		wales = new Room("wales", "Kingdom of Wales");
		england = new Room("england", "Kingdom of England");
		france = new Room("france", "Kingdom of France");
		denmark = new Room("denmark", "Kingdom of Denmark");
		east_francia = new Room("east-francia", "Kingdom of East Francia");
		croatia = new Room("croatia", "Kingdom of Croatia");
		bohemia = new Room("bohemia", "Kingdom of Bohemia");
		bavaria = new Room("bavaria", "Kingdom of Bavaria");
		ocean = new Room("ocean", "The open ocean");

		holy_grail = new Item("holy-grail", "Holy Grail of Power", 100);
		skeleton_crown = new Item("skeleton-crown", "Skeleton Crown of Conjuring", 3.5);
		sun_shield = new Item("sun-shield", "Sun Shield of Light", 7.5);
		brick = new Item("brick", "Just a brick", 1);
		burnt_sword = new Item("burnt-sword", "Burnt Sword of Fire", 5.6);

		player = new Player();
		rooms = new ArrayList<Room>(Arrays.asList(alba, ireland, wales, england, france, denmark,
				east_francia, croatia, bohemia, bavaria, ocean));

		alba.addNeighbour(england);
		alba.addNeighbour(wales);
		england.addNeighbour(wales);
		france.addNeighbour(east_francia);
		denmark.addNeighbour(east_francia);
		east_francia.addNeighbour(bavaria);
		east_francia.addNeighbour(croatia);
		bavaria.addNeighbour(bohemia);
		bavaria.addNeighbour(croatia);
		ocean.addNeighbour(ireland);
		ocean.addNeighbour(wales);
		ocean.addNeighbour(alba);
		ocean.addNeighbour(england);
		ocean.addNeighbour(france);
		ocean.addNeighbour(denmark);
		ocean.addNeighbour(east_francia);
		ocean.addNeighbour(croatia);

		alba.addItem(holy_grail);
		alba.addItem(skeleton_crown);
		alba.addItem(sun_shield);
		wales.addItem(brick);
		wales.addItem(burnt_sword);

		currentRoom = null;
		lastRoom = null;
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printWelcome();

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.
		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing. Good bye.");
	}

	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to Zork!");
		System.out.println("Choose your starting kingdom with: start <kingdom>");
		System.out.println("Available kingdoms: " + kingdomNames() + ".");
		System.out.println("Type 'help' if you need help.");
	}

	private boolean processCommand(Command command) {
		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help")) {
			printHelp();
		} else if (commandWord.equals("start")) {
			startGame(command);
		} else if (commandWord.equals("attack")) {
			attackCurrentKingdom(command);
		} else if (commandWord.equals("go")) {
			goRoom(command);
		} else if (commandWord.equals("quit")) {
			if (command.hasSecondWord()) {
				System.out.println("Quit what?");
			} else {
				return true;
			}
		} else if (commandWord.equals("back")) {
			back(command);
		} else if (commandWord.equals("map")) {
			map();
		} else if (commandWord.equals("pickup")) {
			pickUpItem(command);
		} else if (commandWord.equals("drop")) {
			dropItem(command);
		} else if (commandWord.equals("inventory")) {
			showInventory();
		}
		return false;
	}

	private void printHelp() {
		System.out.println("Choose a kingdom and explore the connected lands.");
		System.out.println();
		System.out.println("Your command words are:");
		System.out.println(parser.showCommands());
	}

	private void startGame(Command command) {
		if (currentKingdom != null) {
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
		case BOHEMIA:
			return bohemia;
		default:
			return bavaria;
		}
	}

	private Kingdom kingdomFor(Room room) {
		return room == null ? null : Kingdom.fromName(room.getCountry());
	}

	private void goRoom(Command command) {
		if (currentRoom == null) {
			System.out.println("Choose a starting kingdom first with 'start <kingdom>'.");
			return;
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
			if (currentKingdom == null) {
				System.out.println("There is no kingdom to attack at sea.");
				return;
			}
			if (conquered.contains(currentKingdom)) {
				System.out.println("You already control this kingdom.");
				return;
			}

			Army enemy = new Army(currentKingdom.getTroops());
			CombatEngine.Result result = CombatEngine.compare(playerArmy, enemy, equippedItem);
			System.out.println("Your army strength: " + result.getPlayerPower());
			System.out.println(currentKingdom.getDisplayName() + " army strength: " + result.getEnemyPower());
			if (result.isDraw()) {
				System.out.println("The battle is a draw. Switch troop types or equip an item and try again.");
			} else if (result.isPlayerVictory()) {
				conquered.add(currentKingdom);
				for (TroopType type : currentKingdom.getTroopTypes()) {
					playerArmy.unlockTroop(type);
				}
				System.out.println(currentKingdom.getDisplayName() + " has been conquered.");
				System.out.println("New troop types unlocked. Use 'troops' to view them.");
			} else {
				System.out.println("The defending kingdom wins. Your army remains intact.");
			}
		}
		if (command.getArgumentCount() != 1) {
			System.out.println("Go where? Enter a neighboring country name.");
			return;
		}

		Room nextRoom = currentRoom.nextRoom(command.getSecondWord());
		if (nextRoom == null) {
			System.out.println("That is not an option! Check your neighbours.");
			return;
		}
		lastRoom = currentRoom;
		currentRoom = nextRoom;
		currentKingdom = kingdomFor(currentRoom);
		System.out.println(currentRoom.longDescription());
		if (currentKingdom == null) {
			System.out.println("You are at sea. There is no kingdom to conquer here.");
		} else if (conquered.contains(currentKingdom)) {
			System.out.println("This kingdom is under your control.");
		} else {
			System.out.println("This kingdom is unconquered. Use 'attack' to fight.");
		}
	}

	private void back(Command command) {
		if (command.getArgumentCount() == 0 && lastRoom != null) {
			Room previousRoom = currentRoom;
			currentRoom = lastRoom;
			lastRoom = previousRoom;
			currentKingdom = kingdomFor(currentRoom);
			System.out.println(currentRoom.longDescription());
		} else if (currentRoom == null) {
			System.out.println("Choose a starting kingdom first.");
		} else {
			System.out.println("There is nowhere to go back to.");
		}
	}

	private void map() {
		if (currentRoom == null) {
			System.out.println("Choose a starting kingdom first.");
			return;
		}
		for (int index = 0; index < rooms.size(); index++) {
			Room room = rooms.get(index);
			String here = room == currentRoom ? " (You are here)" : "";
			System.out.println((index + 1) + ". " + room.getDescription() + room.itemString() + here + ".");
		}
	}

	private void pickUpItem(Command command) {
		if (currentRoom == null) {
			System.out.println("Choose a starting kingdom first.");
			return;
		}
		if (command.getArgumentCount() != 1) {
			System.out.println("Use: pickup <item-id>.");
			return;
		}
		Item itemToAdd = findItem(currentRoom.getItems(), command.getSecondWord());
		if (itemToAdd == null) {
			System.out.println("There is no such item here.");
		} else if (player.addItem(itemToAdd)) {
			currentRoom.removeItem(itemToAdd);
			System.out.println("Added " + itemToAdd.getDescription() + " to the inventory.");
		} else {
			System.out.println("You can't pick this up! It's too heavy!");
		}
	}

	private void dropItem(Command command) {
		if (currentRoom == null) {
			System.out.println("Choose a starting kingdom first.");
			return;
		}
		if (command.getArgumentCount() != 1) {
			System.out.println("Use: drop <item-id>.");
			return;
		}
		Item itemToDrop = findItem(player.getItems(), command.getSecondWord());
		if (itemToDrop == null) {
			System.out.println("There is no such item in your inventory.");
			return;
		}
		player.removeItem(itemToDrop);
		currentRoom.addItem(itemToDrop);
		System.out.println("Removed " + itemToDrop.getDescription() + " from the inventory.");
	}

	private Item findItem(List<Item> items, String name) {
		for (Item item : items) {
			if (item.getName().equalsIgnoreCase(name)) {
				return item;
			}
		}
		return null;
	}

	private void showInventory() {
		player.showInventory();
	}
}
