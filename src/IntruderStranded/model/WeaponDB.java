package IntruderStranded.model;

import IntruderStranded.controller.Weapon;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WeaponDB {
    Weapon getWeapon(int itemID) throws SQLException {
        ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Item WHERE ItemID = ?", itemID);
        return (Weapon) ItemDB.itemsFromResultSet(resultSet, false).getFirst();
    }
}
