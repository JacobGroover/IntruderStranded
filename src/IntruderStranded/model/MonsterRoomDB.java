package IntruderStranded.model;

import IntruderStranded.controller.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MonsterRoomDB extends RoomDBInfoProvider {
	default List<Monster> getMonsters() throws SQLException {
		ResultSet monstersResultSet = DBService.getDB().queryPrepared("SELECT * FROM MonsterRoom WHERE RoomID = ? AND PlayerID = ?", roomID(), playerID());
		List<Monster> monsters = new ArrayList<>();

		while (monstersResultSet.next()) {
			int monsterID = monstersResultSet.getInt("MonsterID");
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Monster WHERE MonsterID = ?", monsterID);
			resultSet.next();
			resultSet.getString("Name");
			resultSet.getInt("Health");

			resultSet.getStatement().close();
		}

		monstersResultSet.getStatement().close();
		return monsters;
	}

	default void removeMonster(Monster monster) throws SQLException {
		ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM MonsterRoom WHERE MonsterID = ?", monster.getID());
		resultSet.next();
		int quantity = resultSet.getInt("MonsterQuantity");

		if (quantity <= 1) {
			DBService.getDB().updatePrepared("DELETE FROM MonsterRoom WHERE MonsterID = ?", monster.getID());
		} else {
			DBService.getDB().updatePrepared("UPDATE MonsterRoom SET MonsterQuantity = ? WHERE MonsterID = ?", quantity - 1, monster.getID());
		}

		resultSet.getStatement().close();
	}
}