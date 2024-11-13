package IntruderStranded.model;

import IntruderStranded.gameExceptions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class: GameDBCreate
 * Builds tables for the main database. The method with playerID int parameter builds
 * database entries for the individual player associated with that playerID.
 */
public class GameDBCreate {
	private static final String MAIN_COMMANDS_PATH = "resources/main_db_commands.txt";
	private static final String PLAYER_COMMANDS_PATH = "resources/player_db_commands.txt";

	/**
	 * Method: executeSQLFromFile
	 * Executes SQL statements seperated by semicolons in a text file.
	 * @param path The path to the file.
	 * @param parameter A parameter to pass into each SQL statement, or null for no parameter.
	 * @throws GameException
	 */
	private void executeSQLFromFile(String path, Object parameter) throws GameException {
		String currentStatement = null;

		try {
			String[] statements = Files.readString(Path.of(path)).split(";\r?\n");

			for (String statement : statements) {
				if (!statement.isBlank()) {
					statement = statement.trim();

					if (parameter != null) {
						statement = statement.replace("?", parameter.toString());
					}

					currentStatement = statement;
					DBService.getDB().update(statement);
				}
			}
		} catch (IOException | SQLException exception) {
			if (currentStatement != null) {
				throw new GameException(exception.getMessage() + "\nWhile executing statement: " + currentStatement);
			} else {
				throw new GameException(exception.getMessage());
			}
		}
	}

	/**
	 * Method: buildTables
	 * Builds database tables from text files for the static database tables.
	 * Should only be called when GameController.start() method identifies that no database exists.
	 * Creates an empty Player, MonsterRoom, ItemRoom, Puzzle, VisitRoom, Exit, Item, Reward, Room, Weapon, and Inventory table in the database
	 */
	public void buildTables() throws GameException {
		executeSQLFromFile(MAIN_COMMANDS_PATH, null);

		try {
			DBService.getDB().commitTransaction();
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: newGame
	 * Creates a new game for the player with the given player id. If a game already exists
	 * for this player, it will be deleted and a fresh game will be created.
	 * @param playerId The player to create the game for.
	 */
	public void newGame(int playerId) throws GameException {
		executeSQLFromFile(PLAYER_COMMANDS_PATH, playerId);

		try {
			int monsters = Math.random() < 0.5 ? 2 : 3;
			DBService.getDB().updatePrepared("INSERT INTO MonsterRoom (PlayerID, RoomID, MonsterID, MonsterQuantity) VALUES (?, 18, 1, ?)", playerId, monsters);
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: gameExists
	 * Checks if the given player has created a new game and saved before.
	 * @param playerId The player ID to check.
	 * @return True if this player has created a new game and saved before, otherwise false.
	 * @throws GameException
	 */
	public boolean gameExists(int playerId) throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT PlayerID FROM (SELECT PlayerID FROM VisitRoom UNION ALL SELECT PlayerID FROM MonsterRoom) WHERE PlayerID = ?", playerId);
			boolean exists = resultSet.next();
			resultSet.getStatement().close();
			return exists;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}
}