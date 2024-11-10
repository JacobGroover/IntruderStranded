package IntruderStranded.controller;

import IntruderStranded.model.*;
import IntruderStranded.gameExceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class: Room
 * @author Hannah Jensen
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 5, 2024
 * This class handles business logic for Player objects.
 */

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
		this.pdb = new PlayerDB();
		Player player = pdb.getPlayer(playerId);
	}



	/**
	 * Method: addItem
	 * Removes item from currentRoom by calling currentRoom.removeItem method.
	 * Adds item to game session database Inventory table by calling
	 * PlayerDB.addItem method.
	 * @param item
	 */
	void addItem(Item item) throws GameException {
		currentRoom.removeItem(item);
		pdb.addItem(playerId, item);
	}

	/**
	 * Method: removeItem
	 * Removes item from game session database Inventory table by calling
	 * PlayerDB.removeItem method.
	 * Adds item to currentRoom by calling currentRoom.addItem method.
	 * @param item
	 */
	void removeItem(Item item) throws GameException {
		pdb.removeItem(playerId, item);
		currentRoom.addItem(item);

	}

	/**
	 * Method: displayInventory
	 * Calls getInventory method and uses it to return a String representation of Item objects.
	 */
	String displayInventory() {
		List<Item> inventory = getInventory();
		String inventoryList = "INV \n";

		for(Item item : inventory) {
			inventoryList += item.display() + "\n";
		}

		return inventoryList;
	}

	/**
	 * Method: getInventory
	 * Returns an ArrayList of Item objects by calling PlayerDB.getInventory method.
	 */
	List<Item> getInventory() {
		List<Item> inventory = pdb.getInventory(playerId);
		return inventory;
	}

	/**
	 * Method: checkLogin
	 * Checks if a player exists in the database with a username and password equal to
	 * the given username and password.
	 * @param username The username to check for.
	 * @param password The password to check for.
	 * @return An empty optional if the login is invalid, otherwise, an optional containing the
	 * id of the player with that username and password.
	 */
	Optional<Integer> checkLogin(String username, String password) throws GameException {
		return pdb.checkLogin(username, password);
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
		pdb.removeItem(playerId, item);
		return item.display();
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