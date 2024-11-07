package IntruderStranded.model;

import IntruderStranded.controller.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerDB implements InventoryDB {

	/**
	 * 
	 * @param player
	 */
	public void updatePlayer(Player player) throws SQLException {
		DBService.getDB().updatePrepared("UPDATE Player SET Health = ?, PreviousRoom = ?, CurrentRoom = ?, Weapon = ?",
				player.getHealth(), player.getPreviousRoom().getID(), player.getCurrentRoom().getID(), player.getWeapon());
	}

	/**
	 * 
	 * @param playerID
	 */
	public Player getPlayer(int playerID) throws SQLException {
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
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param email
	 */
	public void addPlayer(String username, String password, String email) throws SQLException {
		DBService.getDB().updatePrepared("INSERT INTO Player (Username, Password, Email) VALUES (?, ?, ?)", username, password, email);
	}

	/**
	 * 
	 * @param username
	 * @param password
	 */
	public boolean checkLogin(String username, String password) throws SQLException {
		ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Player WHERE Username = ? AND Password = ?", username, password);
		boolean exists = resultSet.next();
		resultSet.getStatement().close();
		return exists;
	}

}