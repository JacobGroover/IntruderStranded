package IntruderStranded.controller;

import IntruderStranded.model.*;
import IntruderStranded.gameExceptions.*;

import java.util.ArrayList;

public class Player extends Entity {

	private int playerId;
	private String username;
	private int weapon;
	private Room currentRoom;
	private Room previousRoom;
	private PlayerDB pdb;
	private int score;

	/**
	 * One-argument Constructor for Player class
	 * Instantiates a Player object with the given playerId by calling PlayerDB.getPlayer method.
	 * @param playerId
	 */
	public Player(int playerId) {
		// TODO - implement Player.Player
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: addItem
	 * Removes item from currentRoom by calling currentRoom.removeItem method.
	 * Adds item to game session database Inventory table by calling
	 * PlayerDB.addItem method.
	 * @param item
	 */
	void addItem(Item item) {
		// TODO - implement Player.addItem
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: removeItem
	 * Removes item from game session database Inventory table by calling
	 * PlayerDB.removeItem method.
	 * Adds item to currentRoom by calling currentRoom.addItem method.
	 * @param item
	 */
	void removeItem(Item item) {
		// TODO - implement Player.removeItem
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: displayInventory
	 * Calls getInventory method and uses it to return a String representation of Item objects.
	 */
	String displayInventory() {
		// TODO - implement Player.displayInventory
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getInventory
	 * Returns an ArrayList of Item objects by calling PlayerDB.getInventory method.
	 */
	ArrayList<Item> getInventory() {
		// TODO - implement Player.getInventory
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param username
	 * @param password
	 */
	String checkLogin(String username, String password) throws GameException {
		// TODO - implement Player.checkLogin
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getCurrentRoom
	 * Getter for the currentRoom class attribute.
	 * Called by GameplayCommands class to access the player's current Room.
	 */
	public Room getCurrentRoom() {
		return this.currentRoom;
	}

	/**
	 * Method: getPreviousRoom
	 * Getter for the previousRoom class attribute.
	 * Called by GameplayCommands class to access the player's previous Room.
	 */
	public Room getPreviousRoom() {
		return this.previousRoom;
	}

	/**
	 * 
	 * @param item
	 */
	String useItem(Item item) {
		// TODO - implement Player.useItem
		throw new UnsupportedOperationException();
	}

	public int getScore() {
		return this.score;
	}

	/**
	 * 
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	public String getUsername() {
		return this.username;
	}

	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public int getWeapon() {
		return this.weapon;
	}

	/**
	 * 
	 * @param weapon
	 */
	public void setWeapon(int weapon) {
		this.weapon = weapon;
	}

	/**
	 * 
	 * @param currentRoom
	 */
	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}

	/**
	 * 
	 * @param previousRoom
	 */
	public void setPreviousRoom(Room previousRoom) {
		this.previousRoom = previousRoom;
	}

}