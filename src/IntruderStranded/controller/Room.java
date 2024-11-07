package IntruderStranded.controller;

import IntruderStranded.model.*;
import java.util.*;

public class Room {

	private int roomID;
	private String roomName;
	private String roomDescription;
	private boolean visited;
	private RoomDB rdb;
	private boolean teleport;
	private Collection<ArrayList<Exit>> exits;
	private ArrayList<RoomEvent> roomEvents;

	Room() {
		// TODO - implement Room.Room
		throw new UnsupportedOperationException();
	}

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
	public Room(int roomID) {
		// TODO - implement Room.Room
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: allowsTeleport
	 * Getter for teleport class attribute
	 */
	boolean allowsTeleport() {
		// TODO - implement Room.allowsTeleport
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns a string representation of this room, with the room name, visited state, description, items and exits.
	 * Calls rdb.getItems method to get a list of items in the room.
	 */
	String display() {
		// TODO - implement Room.display
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: canTeleport
	 * Iterates through the List of Exits and checks whether the List contains TEL Directions.
	 * Should be called from the Room constructor right after the Room data is retrieved from the database.
	 */
	boolean canTeleport() {
		// TODO - implement Room.canTeleport
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: attemptTeleport
	 * Iterates through the List of Exits for this room to return the ID of the destination room in the given
	 * direction from this room. If there is no Exit corresponding to the given direction, a GameException
	 * will be thrown.
	 * @param command
	 */
	int leaveRoom(String command) {
		// TODO - implement Room.leaveRoom
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: addItem
	 * Calls RoomDB.addItem method to add an item to the room.
	 * @param item
	 */
	void addItem(Item item) {
		// TODO - implement Room.addItem
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: removeItem
	 * Calls RoomDB.removeItem to remove an item from this room.
	 * @param item
	 */
	void removeItem(Item item) {
		// TODO - implement Room.removeItem
		throw new UnsupportedOperationException();
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

    public int getID() {
        return roomID;
    }
}