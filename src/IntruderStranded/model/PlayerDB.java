package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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
			DBService.getDB().updatePrepared("UPDATE Player SET Health = ?, PreviousRoom = ?, CurrentRoom = ?, Weapon = ? WHERE PlayerID = ?",
					player.getHealth(), player.getPreviousRoom().getID(), player.getCurrentRoom().getID(), player.getWeapon(), player.getID());
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
	 * Checks the database to verify whether the player already exists. If the player exists, returns false
	 * indicating that a new account with that username and email cannot be created.
	 * If the player does not exist, adds the player to the database and returns true indicating that a new account
	 * with that username and email has been created.
	 * @param username The username for the player.
	 * @param password The password for the player.
	 * @return Boolean indicating whether the account was created or already exists
	 * @param email The email for the player.
	 */
	public boolean addPlayer(String username, String password, String email) throws GameException {
		try {
			DBService.getDB().updatePrepared("INSERT INTO Player (Username, Password, Email, CurrentRoom, PreviousRoom, Weapon, Health, Score) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
					username, password, email, 1, -1, -1, 100, 0);
			return true;
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
	 * @return An empty optional if the login is invalid, otherwise, an optional containing the
	 * id of the player with that username and password.
	 */
	public Optional<Integer> checkLogin(String username, String password) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Player WHERE Username = ? AND Password = ?", username, password);
			boolean exists = resultSet.next();

			if (!exists) {
				resultSet.getStatement().close();
				return Optional.empty();
			}

			int playerID = resultSet.getInt("PlayerID");
			resultSet.getStatement().close();
			return Optional.of(playerID);
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: checkUsernameField
	 * Check if a specific username exists in the database. Used when a player attempts to
	 * recover password from AuthenticationCommands
	 * @param username The text to look for in the given field.
	 * @return Boolean indicating whether the given username was found
	 * @throws GameException
	 */
	public boolean checkUsernameField(String username) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Player WHERE Username = ?", username);
			boolean exists = resultSet.next();
			resultSet.getStatement().close();
			return exists;
		} catch (SQLException sqle) {
			throw new GameException(sqle.getMessage());
		}
	}

	/**
	 * Method: checkEmailField
	 * Check if a specific username exists in the database. Used when a player attempts to
	 * recover password from AuthenticationCommands
	 * @param email The text to look for in the given field.
	 * @return Boolean indicating whether the given username was found
	 * @throws GameException
	 */
	public boolean checkEmailField(String email) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Player WHERE Email = ?", email);
			boolean exists = resultSet.next();
			resultSet.getStatement().close();
			return exists;
		} catch (SQLException sqle) {
			throw new GameException(sqle.getMessage());
		}
	}

	/**
	 * Method: updatePassword
	 * Updates the password for a player account. Used when AuthenticationCommands allows
	 * a player to update password for a specific username they forgot the password for.
	 * @param username String
	 * @param password String
	 * @throws GameException
	 */
	public void updatePassword(String username, String password) throws GameException {
		try {
			DBService.getDB().updatePrepared("UPDATE Player SET Password = ? WHERE Username = ?", password, username);
		} catch (SQLException sqle) {
			throw new GameException(sqle.getMessage());
		}
	}

	/**
	 * Method: retrieveUsername
	 * Retrieves a username from database associated with a given email. Used by AuthenticationCommands
	 * to recover a username for a user.
	 * @param email String
	 * @return String
	 * @throws GameException
	 */
	public String retrieveUsername(String email) throws GameException {
		String username = "";
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT Username FROM Player WHERE Email = ?", email);
			if (resultSet.next()) {
				username = resultSet.getString("Username");
				resultSet.getStatement().close();
			}
		} catch (SQLException sqle) {
			throw new GameException(sqle.getMessage());
		}
		return username;
	}

}