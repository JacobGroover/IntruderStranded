package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class: InventoryDB
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 10, 2024
 *
 * This class handles getting and setting the inventory data from the database.
 */
public class InventoryDB {

	/**
	 * Method: getInventory
	 * Gets the inventory of the player with the given player id.
	 * @param playerID The id of the player.
	 */
	List<Item> getInventory(int playerID) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT Item.*, Inventory.ItemQuantity FROM Inventory LEFT JOIN Item ON Inventory.ItemID = Item.ItemID WHERE PlayerID = ?", playerID);
			return ItemDB.itemsFromResultSet(resultSet, true);
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: addItem
	 * Adds an item into the inventory of the player with the given player id.
	 * @param playerID The id of the player.
	 * @param item The item to add.
	 */
	void addItem(int playerID, Item item) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Inventory WHERE ItemID = ? AND PlayerID = ?", item.getItemID(), playerID);
			boolean exists = resultSet.next();

			if (exists) {
				int quantity = resultSet.getInt("ItemQuantity");
				DBService.getDB().updatePrepared("UPDATE Inventory SET Quantity = ?", quantity + 1);
			} else {
				DBService.getDB().updatePrepared("INSERT INTO Inventory (ItemID, PlayerID, ItemQuantity), (?, ?, ?)", item.getItemID(), playerID, 1);
			}

			resultSet.getStatement().close();
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: removeItem
	 * Removes an item from the inventory of the player with the given player id.
	 * @param playerID The id of the player.
	 * @param item The item to remove.
	 */
	void removeItem(int playerID, Item item) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Inventory WHERE ItemID = ? AND PlayerID = ?", item.getItemID(), playerID);
			resultSet.next();
			int quantity = resultSet.getInt("ItemQuantity");

			if (quantity <= 1) {
				DBService.getDB().updatePrepared("DELETE FROM Inventory WHERE ItemID = ?", item.getItemID());
			} else {
				DBService.getDB().updatePrepared("UPDATE Inventory SET ItemQuantity = ? WHERE ItemID = ?", quantity - 1, item.getItemID());
			}

			resultSet.getStatement().close();
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

}