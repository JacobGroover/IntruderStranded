package IntruderStranded.model;

import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class: VisitRoomDB
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 14, 2024
 *
 * This class handles getting and setting the room visited data from the database.
 */
public record VisitRoomDB(int roomID, int playerID) {
	/**
	 * Method: getVisited
	 * Gets the visited status of this room.
	 * @return True if this room is marked as visited, otherwise false.
	 */
	boolean getVisited() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM VisitRoom WHERE PlayerID = ? AND RoomID = ?", playerID(), roomID());
			boolean visited = resultSet.next();
			resultSet.getStatement().close();
			return visited;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: setVisited
	 * Sets this room as visited.
	 */
	void setVisited() throws GameException {
		try {
			DBService.getDB().updatePrepared("INSERT OR REPLACE INTO VisitRoom (PlayerID, RoomID) VALUES (?, ?)", playerID(), roomID());
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}
}