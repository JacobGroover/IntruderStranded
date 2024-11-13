package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public record RewardDB(int roomID) {

	/**
	 * Method: getRewards
	 */
	List<Item> getRewards() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT Item.* FROM Rewards LEFT JOIN Item ON Rewards.ItemID = Item.ItemID WHERE RoomID = ?", roomID());

			List<Item> rewards = new ArrayList<>();
			while (resultSet.next()) {
				Item item = new Item(resultSet.getInt("ItemID"));
				item.setItemName(resultSet.getString("ItemName"));
				item.setItemDescription(resultSet.getString("ItemDescription"));
			}

			resultSet.getStatement().close();
			return rewards;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

}