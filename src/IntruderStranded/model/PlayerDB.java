package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerDB implements InventoryDB {

	/**
	 * @param player
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
	 * @param playerID
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
	 * @param username
	 * @param password
	 * @param email
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
	 * @param username
	 * @param password
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