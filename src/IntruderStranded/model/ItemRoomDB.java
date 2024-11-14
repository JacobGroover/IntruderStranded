package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class: ItemRoomDB
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 14, 2024
 *
 * This class handles getting and updating the room item data from the database.
 */
public record ItemRoomDB(int roomID, int playerID) {
	/**
	 * Method: getItems
	 * Gets all items currently in this room.
	 * @return The list of items in this room.
	 */
	List<Item> getItems() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT Item.*, ItemRoom.ItemQuantity FROM ItemRoom LEFT JOIN Item ON ItemRoom.ItemID = Item.ItemID WHERE PlayerID = ? AND RoomID = ?", playerID(), roomID());
			return ItemDB.itemsFromResultSet(resultSet, true);
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: addItem
	 * Adds an item to this room.
	 * @param item The item to add.
	 */
	void addItem(Item item) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM ItemRoom WHERE ItemID = ? AND RoomID = ? AND PlayerID = ?", item.getItemID(), roomID(), playerID());
			boolean exists = resultSet.next();

			if (exists) {
				int quantity = resultSet.getInt("ItemQuantity");
				DBService.getDB().updatePrepared("UPDATE ItemRoom SET ItemQuantity = ?", quantity + 1);
			} else {
				DBService.getDB().updatePrepared("INSERT INTO ItemRoom (ItemID, RoomID, PlayerID, ItemQuantity) VALUES (?, ?, ?, ?)", item.getItemID(), roomID(), playerID(), 1);
			}

			resultSet.getStatement().close();
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: removeItem
	 * Removes an item from this room.
	 * @param item The item to remove.
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