package IntruderStranded.model;

import IntruderStranded.controller.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ExitDB extends RoomDBInfoProvider {
	/**
	 * Method: getExits
	 * Returns an ArrayList of exits associated with a roomID.
	 */
	default List<Exit> getExits() throws SQLException {
		ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Exit WHERE RoomID = ?", roomID());
		List<Exit> exits = new ArrayList<>();

		while (resultSet.next())
		{
			Exit exit = new Exit();
			exit.setRoomID(roomID());
			exit.setDestinationID(resultSet.getInt("Destination"));
			exit.setDirection(directionFromInt(resultSet.getInt("Direction")));
			exits.add(exit);
		}

		resultSet.getStatement().close();
		return exits;
	}

	/**
	 * Method: directionFromInt
	 * Converts an integer into its corresponding direction.
	 * @param direction The integer to convert into a direction.
	 * @return The direction value represented by the provided integer.
	 */
	private Direction directionFromInt(int direction) {
		return Direction.values()[direction];
	}
}