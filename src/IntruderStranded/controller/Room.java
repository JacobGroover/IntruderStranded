package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;
import IntruderStranded.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class: Room
 * @author Hannah Jensen
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 5, 2024
 * This class handles business logic for Room objects.
 */

public class Room {

	private int roomID;
	private String roomName;
	private String roomDescription;
	private String hint;
	private boolean visited;
	private RoomDB rdb;
	private boolean teleport;
	private List<Exit> exits;
	private List<RoomEvent> roomEvents;
	private String level;

	/**
	 * Two-argument Constructor for Room class
	 * 
	 * Sets roomID class attribute to input parameter.
	 * Instantiates a RoomDB object using this Room's roomID class attribute, assigns it to rdb.
	 * @param roomID
	 */
	public Room(RoomDB source, int roomID) {
		this.roomID = roomID;
		rdb = source;
	}

	/**
	 * Method: getById
	 * Gets a Room object by its ID.
	 * Calls rdb.getExits method to assign exits class attribute.
	 * Calls rdb.getRoomEvents method to assign roomEvents class attribute.
	 * Calls canTeleport method to determine if this Room has teleport exits. This sets the teleport class
	 * attribute to true or false.
	 * Calls rdb.getVisited to assign the visited class attribute.
	 * @param roomID
	 * @param playerID
	 * @return
	 */
	public static Room getById(int roomID, int playerID) throws GameException {
		RoomDB rdb = new RoomDB(roomID, playerID);
		Room room = rdb.getRoom();
		room.exits = rdb.getExits();
		room.roomEvents = new ArrayList<>(rdb.getMonsters());
		room.roomEvents.addAll(rdb.getPuzzles());
		room.teleport = room.canTeleport();
		room.visited = rdb.getVisited();
		return room;
	}

	/**
	 * Method: allowsTeleport
	 * Getter for teleport class attribute
	 */
	boolean allowsTeleport() {
		return this.teleport;
	}

	/**
	 * Returns a string representation of this room, with the room name, visited state, description, items and exits.
	 * Calls rdb.getItems method to get a list of items in the room.
	 */
	String display() throws GameException {
		String status;

		if (visited) {
			status = "(Visited)";
		} else {
			status = "(Not Visited)";
		}

		List<Item> items = rdb.getItems();
		String itemList = "";
		if (items.isEmpty()) {
			itemList = "No items in this room.";
		} else {
			for (Item item : items) {
				itemList += item.display() + ", ";
			}
			itemList = itemList.substring(0, itemList.length() - 2);
		}

		String exitList;
		List<Exit> physicalExits = exits.stream().filter(e -> !e.getDirection().isTeleport()).toList();
		if (physicalExits.size() == 1) {
			exitList = "You can go " + physicalExits.get(0).getDirection().toString().toLowerCase();
		} else {
			exitList = "You can go either " + physicalExits.stream().map(e -> e.getDirection().toString().toLowerCase()).collect(Collectors.joining(" or "));
		}

		String display = roomName + " " + status + "\nCurrent Level: " + level +
				"\n" + limitStringWidth(roomDescription, 90) + "\n" + itemList
				+ "\n" + exitList;

		return display;
	}

	/**
	 * Method: limitStringWidth
	 * Breaks up a string into lines with a limited number of characters.
	 * @param str The string to limit.
	 * @param lineLimit The minimum number of characters a line will have (except for the last line).
	 * @return The string with its width limited.
	 */
	private String limitStringWidth(String str, int lineLimit) {
		StringBuilder stringBuilder = new StringBuilder(str);
		int breakIndex = lineLimit;

		for (int i = 0; i != -1; i = stringBuilder.indexOf(" ", i + 1)) {
			if (i >= breakIndex) {
				stringBuilder.replace(i, i + 1, "\n");
				breakIndex += lineLimit;
			}
		}

		return stringBuilder.toString();
	}

	/**
	 * Method: canTeleport
	 * Iterates through the List of Exits and checks whether the List contains TEL Directions.
	 * Should be called from the Room constructor right after the Room data is retrieved from the database.
	 */
	boolean canTeleport()  {
		for (Exit exit : exits) {
			if (exit.getDirection().isTeleport()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method: leaveRoom
	 * Iterates through the List of Exits for this room to return the ID of the destination room in the given
	 * direction from this room. If there is no Exit corresponding to the given direction, a GameException
	 * will be thrown.
	 * @param direction The direction to go.
	 */
	int leaveRoom(Direction direction) throws GameException {
		for (Exit exit : exits) {
			if (exit.getDirection() == direction) {
				return exit.getDestinationID();
			}
		}

		throw new GameException("Invalid direction.");
	}

	/**
	 * Method: addItem
	 * Calls RoomDB.addItem method to add an item to the room.
	 * @param item
	 */
	void addItem(Item item) throws GameException {
		rdb.addItem(item);
	}

	/**
	 * Method: removeItem
	 * Calls RoomDB.removeItem to remove an item from this room.
	 * @param item
	 */
	void removeItem(Item item) throws GameException {
		rdb.removeItem(item);
	}

	List<RoomEvent> getRoomEvents() {
		return this.roomEvents;
	}

	public String getRoomName() {
		return this.roomName;
	}

	/**
	 * 
	 * @param roomName
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomDescription() {
		return this.roomDescription;
	}

	/**
	 * 
	 * @param roomDescription
	 */
	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public boolean getVisited() {
		return this.visited;
	}

	/**
	 * 
	 * @param visited
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * Method: getHint
	 * Gets the room hint.
	 * @return The room hint.
	 */
    public String getHint() {
        return hint;
    }

	/**
	 * Method: setHint
	 * Sets the room hint.
	 * @param hint The room hint.
	 */
    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
	 * Method: getID
	 * Gets the ID of the room.
	 */
    public int getID() {
        return roomID;
    }

	/**
	 * Method: setLevel
	 * Sets the level of the room.
	 * @param level The level to set.
	 */
	public void setLevel(String level) {
        this.level = level;
    }
}