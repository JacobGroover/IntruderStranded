package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record MonsterRoomDB(int roomID, int playerID) {
	List<Monster> getMonsters() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT Monster.*, MonsterRoom.MonsterQuantity FROM MonsterRoom LEFT JOIN Monster ON MonsterRoom.MonsterID = Monster.MonsterID WHERE RoomID = ? AND PlayerID = ?", roomID(), playerID());
			List<Monster> monsters = new ArrayList<>();

			while (resultSet.next()) {
				Monster monster = new Monster(resultSet.getInt("MonsterID"), roomID(), playerID());
				monster.setName(resultSet.getString("Name"));
				monster.setHealth(resultSet.getInt("Health"));
				monster.setDamage(resultSet.getInt("Damage"));

				monsters.addAll(Collections.nCopies(resultSet.getInt("MonsterQuantity"), monster));
			}

			resultSet.getStatement().close();
			return monsters;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	void removeMonster(Monster monster) throws GameException {
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