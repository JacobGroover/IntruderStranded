package IntruderStranded.model;

import IntruderStranded.controller.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface RewardDB extends RoomDBInfoProvider {

	/**
	 * Method: getRewards
	 */
	default List<Item> getRewards() throws SQLException {
		ResultSet resultSet = DBService.getDB().queryPrepared("SELECT Item.* FROM Rewards LEFT JOIN Item ON Rewards.ItemID = Item.ItemID WHERE RoomID = ?", roomID());

		List<Item> rewards = new ArrayList<>();
		while (resultSet.next()) {
			Item item = new Item(resultSet.getInt("ItemID"));
			item.setItemName(resultSet.getString("ItemName"));
			item.setItemDescription(resultSet.getString("ItemDescription"));
		}

		resultSet.getStatement().close();
		return rewards;
	}

}