package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PuzzleRoomDB extends RoomDBInfoProvider {
	default List<Puzzle> getPuzzles() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Puzzle WHERE RoomID = ? AND PlayerID = ?", roomID(), playerID());
			List<Puzzle> puzzles = new ArrayList<>();

			while (resultSet.next()) {
				int id = resultSet.getInt("PuzzleID");

				Puzzle puzzle = switch (resultSet.getInt("PuzzleType")) {
					case 0 -> new CombinationPuzzle(id);
					case 1 -> new UnscrambledWordsPuzzle(id);
					case 2 -> new NumberGuessingPuzzle(id);
					case 3 -> new SandPuzzle(id);
					default -> throw new UnsupportedOperationException("Invalid Puzzle ID: " + id);
				};

				puzzles.add(puzzle);
			}

			resultSet.getStatement().close();
			return puzzles;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	default void removePuzzle(Puzzle puzzle) throws GameException {
		try {
			DBService.getDB().updatePrepared("DELETE FROM Puzzle WHERE PuzzleID = ?", puzzle.getID());
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}
}