package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public record ExitDB(int roomID) {
	/**
	 * Method: getExits
	 * Gets all exits in this room.
	 * @return The list of exits in this room.
	 */
	List<Exit> getExits() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Exit WHERE RoomID = ?", roomID());
			List<Exit> exits = new ArrayList<>();

			while (resultSet.next()) {
				Exit exit = new Exit();
				exit.setRoomID(roomID());
				exit.setDestinationID(resultSet.getInt("Destination"));
				exit.setDirection(directionFromInt(resultSet.getInt("Direction")));
				exits.add(exit);
			}

			resultSet.getStatement().close();
			return exits;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
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