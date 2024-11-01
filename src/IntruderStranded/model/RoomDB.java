package IntruderStranded.model;

import IntruderStranded.controller.*;

/**
 * Class: RoomDB
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 22, 2024
 * This class – Holds the Room data for the Intruder Stranded game.
 */
public class RoomDB {

	private int roomID;
	private int playerID;

	/**
	 * One-argument Constructor for RoomDB class
	 * 
	 * Sets roomID equal to the input parameter.
	 * @param roomID
	 * @param playerID
	 */
	public RoomDB(int roomID, int playerID) {
		// TODO - implement RoomDB.RoomDB
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param room
	 */
	public void updateRoom(Room room) {
		// TODO - implement RoomDB.updateRoom
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param roomID
	 */
	public Room getRoom(int roomID) {
		// TODO - implement RoomDB.getRoom
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: addItem
	 * Calls ItemRoomDB.addItem method to add an item to a room. Passes in the Item and the roomID
	 * class attribute.
	 * @param item
	 */
	public void addItem(Item item) {
		// TODO - implement RoomDB.addItem
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: removeItem
	 * Calls ItemRoomDB.removeItem method to add an item to a room. Passes in the Item and the roomID
	 * class attribute.
	 * @param item
	 */
	public void removeItem(Item item) {
		// TODO - implement RoomDB.removeItem
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getExits
	 * Calls ExitDB.getExits to retrieve the exits associated with a Room. Passes in roomID class attribute.
	 */
	public ArrayList<Exit> getExits() {
		// TODO - implement RoomDB.getExits
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getVisited
	 * Calls VisitRoomDB.getVisited to return the visited status of a room. Passes in roomID class attribute.
	 */
	public boolean getVisited() {
		// TODO - implement RoomDB.getVisited
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getVisited
	 * Calls VisitRoomDB.setVisited to assign the visited status of a room. Passes in roomID class attribute.
	 * @param visited
	 */
	public void setVisited(boolean visited) {
		// TODO - implement RoomDB.setVisited
		throw new UnsupportedOperationException();
	}

	public ArrayList<Item> getItems() {
		// TODO - implement RoomDB.getItems
		throw new UnsupportedOperationException();
	}

	public List<Puzzle> getPuzzles() {
		// TODO - implement RoomDB.getPuzzles
		throw new UnsupportedOperationException();
	}

	public List<Monster> getMonsters() {
		// TODO - implement RoomDB.getMonsters
		throw new UnsupportedOperationException();
	}

}