package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface InventoryDB {

	/**
	 * @param playerID
	 */
	default List<Item> getInventory(int playerID) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT Item.*, Inventory.ItemQuantity FROM Inventory LEFT JOIN Item ON Inventory.ItemID = Item.ItemID WHERE PlayerID = ?", playerID);

			List<Item> items = new ArrayList<>();
			while (resultSet.next()) {
				Item item = new Item(resultSet.getInt("ItemID"));
				item.setItemName(resultSet.getString("ItemName"));
				item.setItemDescription(resultSet.getString("ItemDescription"));

				items.addAll(Collections.nCopies(resultSet.getInt("ItemQuantity"), item));
			}

			resultSet.getStatement().close();
			return items;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * @param playerID
	 * @param item
	 */
	default void addItem(int playerID, Item item) throws GameException {
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
	 * @param playerID
	 * @param item
	 */
	default void removeItem(int playerID, Item item) throws GameException {
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