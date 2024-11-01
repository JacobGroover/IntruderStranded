package IntruderStranded.model;

import IntruderStranded.gameExceptions.*;

/**
 * Class: GameDBCreate
 * Builds tables for the main database. Overloaded methods with playerID int parameter build a database entries for the individual player associated with that playerID.
 */
public class GameDBCreate {

	SQLiteDB sdb;

	public GameDBCreate() throws GameException {
		// TODO - implement GameDBCreate.GameDBCreate
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates a new GameDBCreate object with the given player id.
	 * @param playerId
	 */
	public GameDBCreate(int playerId) throws GameException {
		// TODO - implement GameDBCreate.GameDBCreate
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: buildTables
	 * Builds database tables from text files for the static database tables.
	 * Should only be called when GameController.start() method identifies that no database exists.
	 * Call the following methods to build tables:
	 * buildRoom()
	 * buildItem()
	 * buildExit()
	 * buildReward()
	 * buildWeapon()
	 * buildMonster()
	 * buildPlayer() - Creates an empty Player table in the database
	 * buildMonsterRoom() - Creates an empty MonsterRoom table in the database
	 * buildItemRoom() - Creates an empty ItemRoom table
	 * buildPuzzle() - Creates an empty Puzzle table
	 * buildVisitRoom() - Creates an empty VisitRoom table
	 * buildInventory() - Creates an empty Inventory table
	 */
	public void buildTables() throws GameException {
		// TODO - implement GameDBCreate.buildTables
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: newGame
	 * Builds temporary database tables for current session based on playerID input parameter.
	 * Should be called when newGame method is called from MainMenuCommands.
	 * 
	 * Calls the following methods to build tables:
	 * 
	 * newPlayer(id) - Adds TempPlayer table to database with default data for columns
	 * (i.e., currentRoom = 1, health = 100, etc.)
	 * 
	 * newMonsterRoom(id) - Adds TempMonsterRoom table to database with default data for columns.
	 * 
	 * newItemRoom(id) - Adds TempItemRoom table to database with default data for columns.
	 * 
	 * newPuzzle(id) - Adds TempPuzzle table to database with default data for columns.
	 * @param playerId
	 */
	public void newGame(int playerId) throws GameException {
		// TODO - implement GameDBCreate.newGame
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: saveGame
	 * Saves data from Temp tables to their permanent counterparts for the given playerId
	 * (i.e., from TempPlayer to Player, etc.)
	 * Saves from and to the following database tables:
	 * TempPlayer -> Player
	 * TempInventory -> Inventory
	 * TempItemRoom -> ItemRoom
	 * TempVisitRoom -> VisitRoom
	 * TempMonsterRoom -> MonsterRoom
	 * TempPuzzle -> Puzzle
	 * @param playerId
	 */
	public void saveGame(int playerId) throws GameException {
		// TODO - implement GameDBCreate.saveGame
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: loadGame
	 * Retrieves data from player-related tables
	 * Retrieves data from player-related tables and stores in their temporary counterparts for the
	 * given playerId (i.e., from Player to TempPlayer, etc.).
	 * Loads from and to the following database tables:
	 * Player -> TempPlayer
	 * Inventory -> TempInventory
	 * ItemRoom -> TempItemRoom
	 * VisitRoom -> TempVisitRoom
	 * MonsterRoom -> TempMonsterRoom
	 * Puzzle -> TempPuzzle
	 * @param playerId
	 */
	public void loadGame(int playerId) throws GameException {
		// TODO - implement GameDBCreate.loadGame
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: createAccount
	 * Checks if the given username and email exist in the database. If they do, throws a GameException.
	 * 
	 * If they do not exist:
	 * Adds database entry based on username, password, and email input parameters.
	 * This will set the default state for that player. Should be called when a new player account
	 * is created from AuthenticationCommands createAccount method.
	 * 
	 * Generates a playerID to be used for the other methods and stores the username, password, and
	 * email in the row associated with that playerID in the Player table of the database.
	 * @param username
	 * @param password
	 * @param email
	 */
	public void createAccount(String username, String password, String email) throws GameException {
		// TODO - implement GameDBCreate.createAccount
		throw new UnsupportedOperationException();
	}

	private void buildRoom() throws GameException {
		// TODO - implement GameDBCreate.buildRoom
		throw new UnsupportedOperationException();
	}

	private void buildItem() throws GameException {
		// TODO - implement GameDBCreate.buildItem
		throw new UnsupportedOperationException();
	}

	private void buildExit() throws GameException {
		// TODO - implement GameDBCreate.buildExit
		throw new UnsupportedOperationException();
	}

	private void buildReward() throws GameException {
		// TODO - implement GameDBCreate.buildReward
		throw new UnsupportedOperationException();
	}

	private void buildWeapon() throws GameException {
		// TODO - implement GameDBCreate.buildWeapon
		throw new UnsupportedOperationException();
	}

	private void buildMonster() throws GameException {
		// TODO - implement GameDBCreate.buildMonster
		throw new UnsupportedOperationException();
	}

	private void buildPlayer() {
		// TODO - implement GameDBCreate.buildPlayer
		throw new UnsupportedOperationException();
	}

	private void buildMonsterRoom() {
		// TODO - implement GameDBCreate.buildMonsterRoom
		throw new UnsupportedOperationException();
	}

	private void buildItemRoom() {
		// TODO - implement GameDBCreate.buildItemRoom
		throw new UnsupportedOperationException();
	}

	private void buildPuzzle() {
		// TODO - implement GameDBCreate.buildPuzzle
		throw new UnsupportedOperationException();
	}

	private void buildVisitRoom() {
		// TODO - implement GameDBCreate.buildVisitRoom
		throw new UnsupportedOperationException();
	}

	private void buildInventory() {
		// TODO - implement GameDBCreate.buildInventory
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param playerId
	 */
	private void newItemRoom(int playerId) throws GameException {
		// TODO - implement GameDBCreate.newItemRoom
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param playerId
	 */
	private void newPuzzle(int playerId) throws GameException {
		// TODO - implement GameDBCreate.newPuzzle
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param playerId
	 */
	private void newMonsterRoom(int playerId) throws GameException {
		// TODO - implement GameDBCreate.newMonsterRoom
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param playerId
	 */
	private void newPlayer(int playerId) throws GameException {
		// TODO - implement GameDBCreate.newPlayer
		throw new UnsupportedOperationException();
	}

}