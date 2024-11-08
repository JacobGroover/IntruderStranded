package IntruderStranded.model;

import IntruderStranded.gameExceptions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

/**
 * Class: GameDBCreate
 * Builds tables for the main database. The method with playerID int parameter builds
 * database entries for the individual player associated with that playerID.
 */
public class GameDBCreate {
	private static final String MAIN_COMMANDS_PATH = "src/resources/main_db_commands.txt";
	private static final String PLAYER_COMMANDS_PATH = "src/resources/player_db_commands.txt";

	private void executeSQLFromFile(String path, Object... parameters) throws GameException {
		try {
			String[] statements = Files.readString(Path.of(path)).split(";");

			for (String statement : statements) {
				DBService.getDB().updatePrepared(statement.trim(), parameters);
			}
		} catch (IOException | SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: buildTables
	 * Builds database tables from text files for the static database tables.
	 * Should only be called when GameController.start() method identifies that no database exists.
	 * Creates an empty Player, MonsterRoom, ItemRoom, Puzzle, VisitRoom, Exit, Item, Reward, Room, Weapon, and Inventory table in the database
	 */
	public void buildTables() throws GameException {
		executeSQLFromFile(MAIN_COMMANDS_PATH);
	}

	/**
	 * Method: newGame
	 * @param playerId
	 */
	public void newGame(int playerId) throws GameException {
		executeSQLFromFile(PLAYER_COMMANDS_PATH, playerId);
	}
}