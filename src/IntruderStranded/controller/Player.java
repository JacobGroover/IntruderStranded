package IntruderStranded.controller;

import IntruderStranded.model.*;
import IntruderStranded.gameExceptions.*;

import java.util.List;
import java.util.Optional;

/**
 * Class: Player
 * @author Hannah Jensen
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 5, 2024
 * This class handles business logic for Player objects.
 */
public class Player extends Entity {

	private String username;
	private int weapon;
	private Room currentRoom;
	private Room previousRoom;
	private int score;
	private static final PlayerDB pdb = new PlayerDB();

	/**
	 * One-argument Constructor for Player class
	 * Instantiates a Player object with the given playerId.
	 * @param playerId The player id to use.
	 */
	public Player(int playerId) {
		super(playerId);
	}

	/**
	 * Method: getById
	 * Gets a player by their id by calling the PlayerDB.getPlayer method.
	 * @param playerId The player id.
	 * @return The player object with the given id.
	 * @throws GameException
	 */
	public static Player getById(int playerId) throws GameException {
		return pdb.getPlayer(playerId);
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
		pdb.addItem(getID(), item);
	}

	/**
	 * Method: removeItem
	 * Removes item from game session database Inventory table by calling
	 * PlayerDB.removeItem method.
	 * Adds item to currentRoom by calling currentRoom.addItem method.
	 * @param item
	 */
	void removeItem(Item item) throws GameException {
		pdb.removeItem(getID(), item);
		currentRoom.addItem(item);
	}

	/**
	 * Method: displayInventory
	 * Calls getInventory method and uses it to return a String representation of Item objects.
	 */
	String displayInventory() throws GameException {
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
	List<Item> getInventory() throws GameException {
		List<Item> inventory = pdb.getInventory(getID());
		return inventory;
	}

	/**
	 * Method: createAccount
	 * Calls PlayerDB to create a new player with the given username, password, and email.
	 * Returns a boolean indicating true if the account was successfully created, or false if the account
	 * already exists.
	 * @param username The username to use.
	 * @param password The password to use.
	 * @param email The email to use.
	 * @throws GameException
	 */
	static boolean createAccount(String username, String password, String email) throws GameException {
		return pdb.addPlayer(username, password, email);
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
	static Optional<Integer> checkLogin(String username, String password) throws GameException {
		return pdb.checkLogin(username, password);
	}

	/**
	 * Method: checkAccountField
	 * Calls PlayerDB to check if a specific account field exists in the database. Used when a
	 * player attempts to recover password or username from AuthenticationCommands.
	 * @param input The text to look for in the given field.
	 * @param field The player field to check in the database. 1 for Username, 2 for Email.
	 * @return Boolean indicating if the text was found in the given field
	 * @throws GameException
	 */
	static boolean checkAccountField(String input, int field) throws GameException {
		return pdb.checkAccountField(input, field);
	}

	/**
	 * Method: updatePassword
	 * Updates the password for a player account. Called from AuthenticationCommands when
	 * a player updates password for a specific username they forgot the password for.
	 * @param username Username to update password for
	 * @param password New password
	 * @throws GameException
	 */
	static void updatePassword(String username, String password) throws GameException {
		pdb.updatePassword(username, password);
	}

	/**
	 * Method: retrieveUsername
	 * Calls PlayerDB to retrieve a username from database associated with a given email. Used by AuthenticationCommands
	 * to recover a username for a user.
	 * @param email Email associated with a username in database
	 * @return Username associated with the email param
	 * @throws GameException
	 */
	static String retrieveUsername(String email) throws GameException {
		return pdb.retrieveUsername(email);
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
	String useItem(Item item) throws GameException {
        // TODO: Fix implementation
		pdb.removeItem(getID(), item);
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