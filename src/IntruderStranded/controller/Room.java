package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;
import IntruderStranded.model.*;
import java.util.*;

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
	private boolean visited;
	private RoomDB rdb;
	private boolean teleport;
	private Collection<ArrayList<Exit>> exits;
	private ArrayList<RoomEvent> roomEvents;
	private int level;
	private int playerId;

	/**
	 * One-argument Constructor for Room class
	 * 
	 * Sets roomID class attribute to input parameter.
	 * Instantiates a RoomDB object using this Room's roomID class attribute, assigns it to rdb.
	 * Calls rdb.getExits method to assign exits class attribute.
	 * Calls rdb.getRoomEvents method to assign roomEvents class attribute.
	 * Calls canTeleport method to determine if this Room has teleport exits. This sets the teleport class
	 * attribute to true or false.
	 * Calls rdb.getVisited to assign the visited class attribute.
	 * @param roomID
	 */
	public Room(int roomID) throws GameException{
		this.roomID = roomID;
		RoomDB rdb = new RoomDB(roomID, playerId);
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
	String display() {
		String status;

		if (visited) {
			status = "(Visited)";
		} else {
			status = "(Not Visited)";
		}

		ArrayList<Item> items = rdb.getItems();
		String itemList = "";
		if (items.isEmpty()) {
			itemList = "No items in this room.";
		} else {
			for (Item item : items) {
				itemList += item.display() + ", ";
			}
			itemList = itemList.substring(0, itemList.length() - 2);
		}


		String display = roomName + " " + status + "\n Current Level: " + level +
				"\n" + roomDescription + "\n" + itemList;

		return display;
	}

	/**
	 * Method: canTeleport
	 * Iterates through the List of Exits and checks whether the List contains TEL Directions.
	 * Should be called from the Room constructor right after the Room data is retrieved from the database.
	 */
	boolean canTeleport()  {
		for(ArrayList<Exit> exitList : exits) {
			for (Exit exit : exitList) {
				Direction direction = exit.getDirection();
				if (direction == Direction.TEL0IN || direction == Direction.TEL0OUT ||
						direction == Direction.TEL1 || direction == Direction.TEL2) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method: leaveRoom
	 * Iterates through the List of Exits for this room to return the ID of the destination room in the given
	 * direction from this room. If there is no Exit corresponding to the given direction, a GameException
	 * will be thrown.
	 * @param command
	 */
	int leaveRoom(String command) throws GameException {
		Direction direction = Direction.parseDirection(command);

		for(ArrayList<Exit> exitList : exits) {
			for (Exit exit : exitList) {
				if (exit.getDirection() == direction) {
					return exit.getDestinationID();
				}
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

	ArrayList<RoomEvent> getRoomEvents() {
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

}