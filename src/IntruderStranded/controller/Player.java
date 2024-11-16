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
	 */
	public static Player getById(int playerId) throws GameException {
		return pdb.getPlayer(playerId);
	}

	/**
	 * Method: getDamage
	 * Gets the current damage of this player.
	 * @return The damage value.
	 */
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
	 * @param item The item to add.
	 */
	void addItem(Item item) throws GameException {
		pdb.addItem(getID(), item);
	}

	/**
	 * Method: discardItem
	 * Removes item from game session database Inventory table by calling
	 * PlayerDB.removeItem method.
	 * Adds item to currentRoom by calling currentRoom.addItem method.
	 * @param item The item to discard.
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
	 * @return The string to display.
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

	/**
	 * Method: getInventoryItemByName
	 * Gets an item in the player's inventory with the given name. Throws a GameException
	 * if no item with that name exists in the player's inventory.
	 * @param name The item name.
	 * @return The item.
	 */
	Item getInventoryItemByName(String name) throws GameException {
		return getInventory().stream()
				.filter(i -> i.getItemName().equalsIgnoreCase(name))
				.findFirst()
				.orElseThrow(() -> new GameException("Item does not exist"));
	}

	/**
	 * Method: getInventory
	 * Returns an ArrayList of Item objects by calling PlayerDB.getInventory method.
	 * @return The list of items.
	 */
	private List<Item> getInventory() throws GameException {
		return pdb.getInventory(getID());
	}

	/**
	 * Method: canAddToInventory
	 * Checks if an item can be added to the player's inventory.
	 * @param item The item to add.
	 * @return True if the item can be added, otherwise false.
	 */
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
	 * Method: useItem
	 * Uses an item and returns the result.
	 * @param item The item to use.
	 * @return The string to display.
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

	/**
	 * Method: hasItem
	 * Checks if the player has an item with the given name in their inventory.
	 * @param itemName The item name to check for.
	 * @return True if the player has an item with this name in their inventory, otherwise false.
	 */
	boolean hasItem(String itemName) throws GameException {
		return getInventory().stream().anyMatch(i -> i.getItemName().equalsIgnoreCase(itemName));
	}

	/**
	 * Method: update
	 * Updates this player in the database.
	 */
	void update() throws GameException {
		pdb.updatePlayer(this);
	}

	/**
	 * Method: addScore
	 * Adds the given number to this player's score.
	 * @param score The score to add.
	 */
	void addScore(int score) {
		this.score += score;
	}

	/**
	 * Method: getScore
	 * Gets the score of this player.
	 * @return The score of this player.
	 */
	int getScore() {
		return score;
	}

	/**
	 * Method: setScore
	 * Sets the score of this player.
	 * @param score The score to set.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Method: getUsername
	 * Gets the username of this player.
	 * @return The username of this player.
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Method: setUsername
	 * Sets the username of this player.
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Method: getEquippedWeapon
	 * Gets the equipped weapon of this player.
	 * @return The equipped weapon of this player.
	 */
	public Weapon getEquippedWeapon() {
		return this.equippedWeapon;
	}

	/**
	 * Method: setEquippedWeapon
	 * Sets the equipped weapon of this player.
	 * @param equippedWeapon The equipped weapon to set.
	 */
	public void setEquippedWeapon(Weapon equippedWeapon) {
		this.equippedWeapon = equippedWeapon;
	}

	/**
	 * Method: setCurrentRoom
	 * Sets the current room of this player.
	 * @param currentRoom The current room to set.
	 */
	public void setCurrentRoom(Room currentRoom) {
		previousRoom = this.currentRoom;
		this.currentRoom = currentRoom;
	}

	/**
	 * Method: setPreviousRoom
	 * Sets the previous room of this player.
	 * @param previousRoom The previous room to set.
	 */
	public void setPreviousRoom(Room previousRoom) {
		this.previousRoom = previousRoom;
	}

}