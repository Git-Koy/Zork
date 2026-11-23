package ch.bbw.zork;

import java.util.LinkedHashMap;
import java.util.Map;

public class Room {
	private final String description;
	private final Map<String, Room> exits = new LinkedHashMap<String, Room>();

	public Room(String description) {
		this.description = description;
	}

	public void setExits(Room north, Room east, Room south, Room west) {
		exits.clear();
		setExit("north", north);
		setExit("east", east);
		setExit("south", south);
		setExit("west", west);
	}

	public void setExit(String direction, Room room) {
		if (room != null) {
			exits.put(direction, room);
		}
	}

	public String shortDescription() {
		return description;
	}

	public String longDescription() {
		return "You are in " + description + ".\n" + exitString();
	}

	private String exitString() {
		if (exits.isEmpty()) {
			return "Exits: none";
		}
		return "Exits: " + String.join(" ", exits.keySet());
	}

	public Room nextRoom(String direction) {
		return exits.get(direction);
	}
}
