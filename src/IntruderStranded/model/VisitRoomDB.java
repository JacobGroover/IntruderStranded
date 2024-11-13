package IntruderStranded.model;

import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;

public record VisitRoomDB(int roomID, int playerID) {
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

	void setVisited() throws GameException {
		try {
			DBService.getDB().updatePrepared("INSERT OR REPLACE INTO VisitRoom (PlayerID, RoomID) VALUES (?, ?)", playerID(), roomID());
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}
}