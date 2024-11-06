package IntruderStranded.model;

import IntruderStranded.controller.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface ItemRoomDB extends RoomDBInfoProvider {
	default List<Item> getItems() throws SQLException {
		ResultSet resultSet = DBService.getDB().queryPrepared("SELECT Item.*, ItemRoom.ItemQuantity FROM ItemRoom LEFT JOIN Item ON ItemRoom.ItemID = Item.ItemID WHERE PlayerID = ? AND RoomID = ?", playerID(), roomID());

		List<Item> items = new ArrayList<>();
		while (resultSet.next()) {
			Item item = new Item(resultSet.getInt("ItemID"));
			item.setItemName(resultSet.getString("ItemName"));
			item.setItemDescription(resultSet.getString("ItemDescription"));

			items.addAll(Collections.nCopies(resultSet.getInt("ItemQuantity"), item));
		}

		resultSet.getStatement().close();
		return items;
	}

	/**
	 * method: addItem
	 * Adds an item to the room with the roomID matching the parameter passed in.
	 * @param item
	 */
	default void addItem(Item item) throws SQLException {
		ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM ItemRoom WHERE ItemID = ? AND RoomID = ? AND PlayerID = ?", item.getItemID(), roomID(), playerID());
		boolean exists = resultSet.next();

		if (exists) {
			int quantity = resultSet.getInt("ItemQuantity");
			DBService.getDB().updatePrepared("UPDATE ItemRoom SET Quantity = ?", quantity + 1);
		} else {
			DBService.getDB().updatePrepared("INSERT INTO ItemRoom (ItemID, RoomID, PlayerID, Quantity), (?, ?, ?, ?)", item.getItemID(), roomID(), playerID(), 1);
		}

		resultSet.getStatement().close();
	}

	/**
	 * method: removeItem
	 * Removes an item from the room with the roomID matching the parameter passed in.
	 * @param item
	 */
	default void removeItem(Item item) throws SQLException {
		ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM ItemRoom WHERE ItemID = ? AND RoomID = ? AND PlayerID = ?", item.getItemID(), roomID(), playerID());
		resultSet.next();
		int quantity = resultSet.getInt("ItemQuantity");

		if (quantity <= 1) {
			DBService.getDB().updatePrepared("DELETE FROM ItemRoom WHERE ItemID = ?", item.getItemID());
		} else {
			DBService.getDB().updatePrepared("UPDATE ItemRoom SET ItemQuantity = ? WHERE ItemID = ?", quantity - 1, item.getItemID());
		}

		resultSet.getStatement().close();
	}
}