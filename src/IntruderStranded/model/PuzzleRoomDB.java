package IntruderStranded.model;

import IntruderStranded.controller.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PuzzleRoomDB extends RoomDBInfoProvider {
	default List<Puzzle> getPuzzles() throws SQLException {
		ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Puzzle WHERE RoomID = ? AND PlayerID = ?", roomID(), playerID());
		List<Puzzle> puzzles = new ArrayList<>();

		while (resultSet.next()) {
			Puzzle puzzle = switch (resultSet.getInt("PuzzleType")) {
				case 0 -> new CombinationPuzzle();
				case 1 -> new UnscrambledWordsPuzzle();
			};

			puzzles.add(puzzle);
		}

		resultSet.getStatement().close();
		return puzzles;
	}

	default void removePuzzle(Puzzle puzzle) {
		throw new UnsupportedOperationException();
	}
}