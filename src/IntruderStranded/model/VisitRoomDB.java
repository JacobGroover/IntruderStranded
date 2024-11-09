package IntruderStranded.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface VisitRoomDB extends RoomDBInfoProvider {
	default boolean getVisited() throws SQLException {
		ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM VisitRoom WHERE PlayerID = ? AND RoomID = ?", playerID(), roomID());
		boolean visited = resultSet.next();
		resultSet.getStatement().close();
		return visited;
	}

	default void setVisited() throws SQLException {
		DBService.getDB().updatePrepared("INSERT OR REPLACE INTO VisitRoom (PlayerID, RoomID) VALUES (?, ?)", playerID(), roomID());
	}
}