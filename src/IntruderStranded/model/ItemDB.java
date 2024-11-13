package IntruderStranded.model;

import IntruderStranded.controller.Item;
import IntruderStranded.controller.Weapon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Class: ItemDB
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 13, 2024
 *
 * This class handles getting items from database queries. This class cannot be instantiated or extended.
 */
public final class ItemDB {
    private ItemDB() {}

    /**
     * Method: itemsFromResultSet
     * Reads a list of items from a result set. Closes the result set before returning.
     * @param resultSet The result set to process.
     * @param withQuantity If true, adds multiples of items using the ItemQuantity column.
     * @return The list of items contained in the result set
     * @throws SQLException
     */
    public static List<Item> itemsFromResultSet(ResultSet resultSet, boolean withQuantity) throws SQLException {
        HashMap<Integer, Integer> weaponData = getWeaponData();
        List<Item> items = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("ItemID");

            Item item;
            if (weaponData.containsKey(id)) {
                Weapon weapon = new Weapon(id);
                weapon.setDamage(weaponData.get(id));
                item = weapon;
            } else {
                item = new Item(id);
            }

            item.setItemName(resultSet.getString("ItemName"));
            item.setItemDescription(resultSet.getString("ItemDescription"));
            int quantity = withQuantity ? resultSet.getInt("ItemQuantity") : 1;
            items.addAll(Collections.nCopies(quantity, item));
        }

        resultSet.getStatement().close();
        return items;
    }

    /**
     * Method: getWeaponData
     * Gets weapon data from the database.
     * @return A hashmap with a key of the item id and a value of the weapon damage.
     * @throws SQLException
     */
    private static HashMap<Integer, Integer> getWeaponData() throws SQLException {
        ResultSet resultSet = DBService.getDB().query("SELECT * FROM Weapon");
        HashMap<Integer, Integer> weaponData = new HashMap<>();

        while (resultSet.next()) {
            weaponData.put(resultSet.getInt("ItemID"), resultSet.getInt("Damage"));
        }

        resultSet.getStatement().close();
        return weaponData;
    }
}
