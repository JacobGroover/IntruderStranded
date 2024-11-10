package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class: PlayerDB
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 10, 2024
 *
 * This class handles getting and setting player data from the database.
 */
public class PlayerDB implements InventoryDB {

	/**
	 * Method: updatePlayer
	 * Updates the row in the database corresponding to the given player object.
	 * @param player The player object whose information will be stored into the database.
	 */
	public void updatePlayer(Player player) throws GameException {
		try {
			DBService.getDB().updatePrepared("UPDATE Player SET Health = ?, PreviousRoom = ?, CurrentRoom = ?, Weapon = ?",
					player.getHealth(), player.getPreviousRoom().getID(), player.getCurrentRoom().getID(), player.getWeapon());
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: getPlayer
	 * Gets the player from the database with the given player id.
	 * @param playerID The id of the player to get.
	 */
	public Player getPlayer(int playerID) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Player WHERE PlayerID = ?", playerID);

			Player player = new Player(playerID);
			player.setUsername(resultSet.getString("Username"));
			player.setScore(resultSet.getInt("Score"));
			player.setWeapon(resultSet.getInt("Weapon"));
			player.setHealth(resultSet.getInt("Health"));

			RoomDB currentRoomDB = new RoomDB(resultSet.getInt("CurrentRoom"), playerID);
			RoomDB previousRoomDB = new RoomDB(resultSet.getInt("PreviousRoom"), playerID);
			player.setCurrentRoom(currentRoomDB.getRoom());
			player.setPreviousRoom(previousRoomDB.getRoom());

			resultSet.getStatement().close();
			return player;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: addPlayer
	 * Creates a new player in the database with the given username, password, and email.
	 * @param username The username for the player.
	 * @param password The password for the player.
	 * @param email The email for the player.
	 */
	public void addPlayer(String username, String password, String email) throws GameException {
		try {
			DBService.getDB().updatePrepared("INSERT INTO Player (Username, Password, Email, CurrentRoom, PreviousRoom, Weapon, Health) VALUES (?, ?, ?, ?, ?, ?, ?)",
					username, password, email, 1, -1, -1, 100);
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: checkLogin
	 * Checks if a player exists in the database with a username and password equal to
	 * the given username and password.
	 * @param username The username to check for.
	 * @param password The password to check for.
	 */
	public boolean checkLogin(String username, String password) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Player WHERE Username = ? AND Password = ?", username, password);
			boolean exists = resultSet.next();
			resultSet.getStatement().close();
			return exists;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

}