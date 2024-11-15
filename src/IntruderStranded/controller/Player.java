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
	private Weapon equippedWeapon;
	private Room currentRoom;
	private Room previousRoom;
	private int score;
	private static final PlayerDB pdb = new PlayerDB();
	private static final int INVENTORY_CAPACITY = 10;
	private static final int BASE_DAMAGE = 10;

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

	@Override
	public int getDamage() {
		int damage = BASE_DAMAGE;

		if (equippedWeapon != null) {
			damage += equippedWeapon.getDamage();
		}

		return damage;
	}

	/**
	 * Method: addItem
	 * Adds item to game session database Inventory table by calling
	 * PlayerDB.addItem method.
	 * @param item
	 */
	void addItem(Item item) throws GameException {
		pdb.addItem(getID(), item);
	}

	/**
	 * Method: discardItem
	 * Removes item from game session database Inventory table by calling
	 * PlayerDB.removeItem method.
	 * Adds item to currentRoom by calling currentRoom.addItem method.
	 * @param item
	 */
	void discardItem(Item item) throws GameException {
		removeItem(item);
		currentRoom.addItem(item);
	}

	/**
	 * Method: removeItem
	 * Removes an item from this player's inventory.
	 * @param item The item to remove.
	 */
	void removeItem(Item item) throws GameException {
		if (item.equals(equippedWeapon)) {
			equippedWeapon = null;
		}

		pdb.removeItem(getID(), item);
	}

	/**
	 * Method: displayInventory
	 * Calls getInventory method and uses it to return a String representation of Item objects.
	 */
	String displayInventory() throws GameException {
		StringBuilder display = new StringBuilder();
		display.append("Score: ").append(score).append('\n');
		display.append("Your STATS: ").append(getStatus()).append('\n');
		display.append("Item List:\n");
		List<Item> inventory = getInventory().stream().distinct().toList();

		for (int index = 0; index < 10; index++) {
			if (index >= inventory.size()) {
				display.append("empty,\n");
				continue;
			}

			Item item = inventory.get(index);
			long quantity = inventory.stream().filter(i -> i.equals(item)).count();
			if (quantity > 1) {
				display.append(quantity).append(' ');
			}

			display.append(item.getItemName()).append(",\n");
		}

		return display.toString();
	}

	Item getInventoryItemByName(String name) throws GameException {
		return getInventory().stream()
				.filter(i -> i.getItemName().equalsIgnoreCase(name))
				.findFirst()
				.orElseThrow(() -> new GameException("Item does not exist"));
	}

	/**
	 * Method: getInventory
	 * Returns an ArrayList of Item objects by calling PlayerDB.getInventory method.
	 */
	private List<Item> getInventory() throws GameException {
		return pdb.getInventory(getID());
	}

	boolean canAddToInventory(Item item) throws GameException {
		if (getInventory().contains(item)) {
			return true;
		}

        return getInventory().stream().distinct().count() < INVENTORY_CAPACITY;
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
	 * Method: checkUsernameField
	 * Calls PlayerDB to check if a specific username exists in the database. Used when a
	 * player attempts to recover password from AuthenticationCommands.
	 * @param username The text to look for in the username field.
	 * @return Boolean indicating if the text was found in the given field
	 * @throws GameException
	 */
	static boolean checkUsernameField(String username) throws GameException {
		return pdb.checkUsernameField(username);
	}

	/**
	 * Method: checkEmailField
	 * Calls PlayerDB to check if a specific email associated with a username
	 * exists in the database. Used when a player attempts to recover username
	 * from AuthenticationCommands.
	 * @param email The text to look for in the email field.
	 * @return Boolean indicating if the text was found in the given field
	 * @throws GameException
	 */
	static boolean checkEmailField(String email) throws GameException {
		return pdb.checkEmailField(email);
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
	 * @return Username associated with the email
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
		if (item instanceof Weapon weapon) {
			this.equippedWeapon = weapon;
			return "You are now equipped with " + item.getItemName() + "!\nYour STATS: " + getStatus();
		}

		switch (item.getConsumableType()) {
			case NONE -> {
				return "You cannot use this item";
			}
			case MED_PACK -> {
				removeItem(item);
				setHealth(Math.min(getHealth() + 20, 100));
				return "You used the " + item.getItemName() + "\nYour STATS: " + getStatus();
			}
			case FREEZING_POTION -> {
				return "You can only use this item in a battle";
			}
		}

		return item.display();
	}

	boolean hasItem(String itemName) throws GameException {
		return getInventory().stream().anyMatch(i -> i.getItemName().equalsIgnoreCase(itemName));
	}

	void update() throws GameException {
		pdb.updatePlayer(this);
	}

	void addScore(int score) {
		this.score += score;
	}

	int getScore() {
		return score;
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

	public Weapon getEquippedWeapon() {
		return this.equippedWeapon;
	}

	/**
	 * 
	 * @param equippedWeapon
	 */
	public void setEquippedWeapon(Weapon equippedWeapon) {
		this.equippedWeapon = equippedWeapon;
	}

	/**
	 * 
	 * @param currentRoom
	 */
	public void setCurrentRoom(Room currentRoom) {
		previousRoom = this.currentRoom;
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