package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: MonsterRoomDB
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 14, 2024
 *
 * This class handles getting and updating the monster data from the database.
 */
public record MonsterRoomDB(int roomID, int playerID) {
	/**
	 * Method: getMonsters
	 * Gets all monsters currently in this room.
	 * @return The list of monsters in this room.
	 */
	List<Monster> getMonsters() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT Monster.*, MonsterRoom.MonsterQuantity FROM MonsterRoom LEFT JOIN Monster ON MonsterRoom.MonsterID = Monster.MonsterID WHERE RoomID = ? AND PlayerID = ?", roomID(), playerID());
			List<Monster> monsters = new ArrayList<>();

			while (resultSet.next()) {
				Monster monster = new Monster(resultSet.getInt("MonsterID"), roomID(), playerID());
				monster.setName(resultSet.getString("Name"));
				monster.setHealth(resultSet.getInt("Health"));
				monster.setDamage(resultSet.getInt("Damage"));

				monsters.add(monster);
				int quantity = resultSet.getInt("MonsterQuantity");
				for (int i = 1; i < quantity; i++) {
					monsters.add(new Monster(monster)); // monsters are mutable, so use copy constructor
				}
			}

			resultSet.getStatement().close();
			return monsters;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: removeMonster
	 * Removes a monster from this room.
	 * @param monster The monster to remove.
	 */
	void removeMonster(Monster monster) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM MonsterRoom WHERE MonsterID = ? AND RoomID = ? AND PlayerID = ?", monster.getID(), roomID(), playerID());
			resultSet.next();
			int quantity = resultSet.getInt("MonsterQuantity");

			if (quantity <= 1) {
				DBService.getDB().updatePrepared("DELETE FROM MonsterRoom WHERE MonsterID = ? AND RoomID = ? AND PlayerID = ?", monster.getID(), roomID(), playerID());
			} else {
				DBService.getDB().updatePrepared("UPDATE MonsterRoom SET MonsterQuantity = ? WHERE MonsterID = ? AND RoomID = ? AND PlayerID = ?", quantity - 1, monster.getID(), roomID(), playerID());
			}

			resultSet.getStatement().close();
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}
}