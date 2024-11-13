package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public record RewardDB(int roomID) {

	/**
	 * Method: getRewards
	 */
	List<Item> getRewards() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT Item.* FROM Rewards LEFT JOIN Item ON Rewards.ItemID = Item.ItemID WHERE RoomID = ?", roomID());
			return ItemDB.itemsFromResultSet(resultSet, false);
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

}