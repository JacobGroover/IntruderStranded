package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MonsterRoomDB extends RoomDBInfoProvider {
	default List<Monster> getMonsters() throws GameException {
		try {
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
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	default void removeMonster(Monster monster) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM MonsterRoom WHERE MonsterID = ? AND RoomID = ? AND PlayerID = ?", monster.getID(), roomID(), playerID());
			resultSet.next();
			int quantity = resultSet.getInt("MonsterQuantity");

			if (quantity <= 1) {
				DBService.getDB().updatePrepared("DELETE FROM MonsterRoom WHERE MonsterID = ?", monster.getID());
			} else {
				DBService.getDB().updatePrepared("UPDATE MonsterRoom SET MonsterQuantity = ? WHERE MonsterID = ?", quantity - 1, monster.getID());
			}

			resultSet.getStatement().close();
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}
}