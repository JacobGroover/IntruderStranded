package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public record ItemRoomDB(int roomID, int playerID) {
	List<Item> getItems() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT Item.*, ItemRoom.ItemQuantity FROM ItemRoom LEFT JOIN Item ON ItemRoom.ItemID = Item.ItemID WHERE PlayerID = ? AND RoomID = ?", playerID(), roomID());
			return ItemDB.itemsFromResultSet(resultSet, true);
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * method: addItem
	 * Adds an item to the room with the roomID matching the parameter passed in.
	 *
	 * @param item
	 */
	void addItem(Item item) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM ItemRoom WHERE ItemID = ? AND RoomID = ? AND PlayerID = ?", item.getItemID(), roomID(), playerID());
			boolean exists = resultSet.next();

			if (exists) {
				int quantity = resultSet.getInt("ItemQuantity");
				DBService.getDB().updatePrepared("UPDATE ItemRoom SET ItemQuantity = ?", quantity + 1);
			} else {
				DBService.getDB().updatePrepared("INSERT INTO ItemRoom (ItemID, RoomID, PlayerID, ItemQuantity), (?, ?, ?, ?)", item.getItemID(), roomID(), playerID(), 1);
			}

			resultSet.getStatement().close();
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * method: removeItem
	 * Removes an item from the room with the roomID matching the parameter passed in.
	 *
	 * @param item
	 */
	void removeItem(Item item) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM ItemRoom WHERE ItemID = ? AND RoomID = ? AND PlayerID = ?", item.getItemID(), roomID(), playerID());
			resultSet.next();
			int quantity = resultSet.getInt("ItemQuantity");

			if (quantity <= 1) {
				DBService.getDB().updatePrepared("DELETE FROM ItemRoom WHERE ItemID = ?", item.getItemID());
			} else {
				DBService.getDB().updatePrepared("UPDATE ItemRoom SET ItemQuantity = ? WHERE ItemID = ?", quantity - 1, item.getItemID());
			}

			resultSet.getStatement().close();
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}
}