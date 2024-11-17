package IntruderStranded.model;

import IntruderStranded.controller.Weapon;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class: WeaponDB
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 13, 2024
 *
 * This class handles getting weapon data from the database.
 */
public class WeaponDB {
    /**
     * Method: getWeapon
     * Gets the weapon with the given item id from the database.
     * @param itemID The item id of the weapon.
     * @return The weapon with the given item id.
     */
    Weapon getWeapon(int itemID) throws SQLException {
        ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Item WHERE ItemID = ?", itemID);
        return (Weapon) ItemDB.itemsFromResultSet(resultSet, false).getFirst();
    }
}
