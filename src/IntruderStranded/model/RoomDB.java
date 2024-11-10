package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class: RoomDB
 *
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 22, 2024
 * This class – Holds the Room data for the Intruder Stranded game.
 */
public record RoomDB(int roomID, int playerID) implements VisitRoomDB, ItemRoomDB, ExitDB, MonsterRoomDB, PuzzleRoomDB, RewardDB {
	public void updateRoom(Room room) throws GameException {
		if (room.getVisited()) {
			setVisited();
		}
	}

	public Room getRoom() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Room WHERE RoomID = ?", roomID);
			resultSet.next();
			Room room = new Room(this, roomID);
			room.setRoomName(resultSet.getString("RoomName"));
			room.setRoomDescription(resultSet.getString("RoomDescription"));
			room.setLevel(resultSet.getString("RoomLevel"));
			room.setHint(resultSet.getString("RoomHint"));
			resultSet.getStatement().close();
			return room;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}
}