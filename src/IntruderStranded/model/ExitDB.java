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

		}

		resultSet.getStatement().close();
		return exits;
	}

}