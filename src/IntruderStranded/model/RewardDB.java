package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class: RewardDB
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 14, 2024
 *
 * This class handles getting the room rewards data from the database.
 */
public record RewardDB(int roomID) {

	/**
	 * Method: getRewards
	 * Gets all rewards in this room.
	 * @return The list of rewards in this room.
	 */
	List<Item> getRewards() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT Item.* FROM Reward LEFT JOIN Item ON Reward.ItemID = Item.ItemID WHERE RoomID = ?", roomID());
			return ItemDB.itemsFromResultSet(resultSet, false);
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}
}