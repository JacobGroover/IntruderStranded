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
	private final int roomID;
	private String roomName;
	private String roomDescription;
	private String hint;
	private boolean visited;
	private final RoomDB rdb;
	private boolean allowsTeleport;
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
	 * Creates a copy of this Room object. Designed for use in subclasses.
	 * @param room The room to copy.
	 */
	protected Room(Room room) {
		this.roomID = room.roomID;
		this.roomName = room.roomName;
		this.roomDescription = room.roomDescription;
		this.hint = room.hint;
		this.visited = room.visited;
		this.rdb = room.rdb;
		this.allowsTeleport = room.allowsTeleport;
		this.exits = room.exits;
		this.roomEvents = room.roomEvents;
		this.level = room.level;
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

		if (room.roomName.equalsIgnoreCase("Dining Hall")) {
			room = new DiningHallRoom(room);
		}

		room.exits = rdb.getExits();
		room.roomEvents = new ArrayList<>(rdb.getMonsters());
		room.roomEvents.addAll(rdb.getPuzzles());
		room.allowsTeleport = room.canTeleport();
		room.visited = rdb.getVisited();
		return room;
	}

	/**
	 * Method: getNameFromId
	 * Gets the name of a room from its id.
	 * @param roomId The id of the room to get.
	 * @return The name of the room with the given id.
	 * @throws GameException
	 */
	public static String getNameFromId(int roomId) throws GameException {
		RoomDB rdb = new RoomDB(roomId);
		Room room = rdb.getRoom();
		return room.getRoomName();
	}

	/**
	 * Method: overrideInput
	 * Allows a room to override the handling of user input.
	 * @param commands The GameplayCommands instance calling this method.
	 * @param input The string entered by the user.
	 * @return An empty optional if the room does not want to override input handling, otherwise, an
	 * optional containing the string to display.
	 */
    Optional<String> overrideInput(GameplayCommands commands, String input) throws GameException {
		return Optional.empty();
	}

    /**
	 * Method: allowsTeleport
	 * Getter for teleport class attribute
	 */
	boolean allowsTeleport() {
		return this.allowsTeleport;
	}

	/**
	 * Method: canLeave
	 * Checks if a player can leave a room.
	 * @param player The current player.
	 * @return True if the player can currently leave this room, otherwise false.
	 */
	boolean canLeave(Player player) throws GameException {
		if (roomEvents.stream().anyMatch(e -> e instanceof Monster)) {
			return false;
		}

		if (!visited && roomName.equalsIgnoreCase("Cell") && !player.hasItem("Cell Door Key")) {
			return false;
		}

		return true;
	}

	/**
	 * Method: canEnter
	 * Checks if a player can enter a room.
	 * @param player The current player.
	 * @return True if the player can currently enter this room, otherwise false.
	 */
	boolean canEnter(Player player) throws GameException {
		String[] missingParts = { "Missing Wheel", "Missing Control Panel", "Missing Engine", "Missing Wing", "Missing Chair" };

		if (roomName.equalsIgnoreCase("Boss Room") && !player.hasItems(missingParts)) {
			return false;
		}

		return true;
	}

	/**
	 * Method: display
	 * Returns a string representation of this room, with the room name, visited state, description, items and exits.
	 * Calls rdb.getItems method to get a list of items in the room.
	 * @param player The current player.
	 * @return The string representation of this room.
	 */
	String display(Player player) throws GameException {
		String status = visited ? "(Visited)" : "(Not visited)";

		List<Item> items = rdb.getItems();
		String itemList = items.stream()
				.map(Item::display)
				.collect(Collectors.joining("\n"));

		if (!itemList.isEmpty()) {
			itemList = "\nItems in room:\n" + itemList;
		}

		String exitText = displayExits();

		if (allowsTeleport()) {
			exitText += "\nThe teleportation feature can be activated.";
		}

		return roomName + " " + status + "\nCurrent Level: Level " + level +
				"\n\n" + limitStringWidth(getRoomDescription(), 90) + itemList
				+ (canLeave(player) ? "\n" + exitText : "");
	}

	/**
	 * Method: displayExits
	 * Constructs the string for displaying the exits in this room.
	 * @return The string to display.
	 */
	String displayExits() {
		return exits.stream()
				.filter(e -> !e.getDirection().isTeleport())
				.map(Exit::display)
				.collect(Collectors.joining(", "));
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

		for (int i = 0; i < str.length(); i++) {
			if (stringBuilder.charAt(i) == '\n') {
				breakIndex = i + lineLimit;
			} else if (i >= breakIndex && stringBuilder.charAt(i) == ' ') {
				stringBuilder.replace(i, i + 1, "\n");
				breakIndex = i + lineLimit;
			}
		}

		return stringBuilder.toString();
	}

	/**
	 * Method: canTeleport
	 * Iterates through the List of Exits and checks whether the List contains TEL Directions.
	 * Should be called from the Room constructor right after the Room data is retrieved from the database.
	 */
	private boolean canTeleport()  {
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
	 * direction from this room. If there is no Exit corresponding to the given direction, or the player cannot
	 * leave the room yet, or the player cannot enter the destination room yet, a GameException will be thrown.
	 * Otherwise, this room will be marked as visited before returning the destination room.
	 * @param player The current player.
	 * @param direction The direction to go.
	 * @return The destination room.
	 */
	Room leaveRoom(Player player, Direction direction) throws GameException {
		if (!canLeave(player)) {
			throw new GameException("Can't leave room yet");
		}

		for (Exit exit : exits) {
			if (exit.getDirection() == direction) {
				Room destination = Room.getById(exit.getDestinationID(), player.getID());

				if (!visited) {
					player.addScore(5);
					setVisited();
				}

				if (!destination.canEnter(player)) {
					throw new GameException("Can't go here yet");
				}

				return destination;
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

	/**
	 * Method: getItems
	 * Gets the items in this room.
	 * @return The list of items in this room.
	 * @throws GameException
	 */
	List<Item> getItems() throws GameException {
		return rdb.getItems();
	}

	/**
	 * Method: getRoomEvents
	 * Gets all room events in this room.
	 * @return A list of room events.
	 */
	List<RoomEvent> getRoomEvents() {
		return this.roomEvents;
	}

	/**
	 * Method: getRoomName
	 * Gets the name of this room.
	 * @return The name of this room.
	 */
	public String getRoomName() {
		return this.roomName;
	}

	/**
	 * Method: setRoomName
	 * Sets the name of this room.
	 * @param roomName The name to set.
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * Method: getRoomDescription
	 * Gets the description of this room.
	 * @return The description of this room.
	 */
	public String getRoomDescription() {
		return this.roomDescription;
	}

	/**
	 * Method: setRoomDescription
	 * Sets the description of this room.
	 * @param roomDescription The description to set.
	 */
	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	/**
	 * Method: getVisited
	 * Gets the visited status of this room.
	 * @return True if this room has been visited, otherwise false.
	 */
	public boolean getVisited() {
		return this.visited;
	}

	/**
	 * Method: setVisited
	 * Sets this room as visited.
	 */
	public void setVisited() throws GameException {
		this.visited = true;
		rdb.setVisited();
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
	 * Method: getLevel
	 * Gets the level of the room.
	 * @return The level of the room.
	 */
	public String getLevel() {
		return level;
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