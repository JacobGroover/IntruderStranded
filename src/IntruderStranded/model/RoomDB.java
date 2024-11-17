package IntruderStranded.model;

import IntruderStranded.controller.*;
import IntruderStranded.gameExceptions.GameException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Class: RoomDB
 *
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 22, 2024
 * This class – Holds the Room data for the Intruder Stranded game.
 */
public class RoomDB {
	private final int roomID;
	private final ExitDB exitDB;
	private final RewardDB rewardDB;
	private VisitRoomDB visitRoomDB;
	private ItemRoomDB itemRoomDB;
	private MonsterRoomDB monsterRoomDB;
	private PuzzleRoomDB puzzleRoomDB;

	/**
	 * Creates a new RoomDB object with a room id. This RoomDB can only be used
	 * to access room data which is not linked to any specific player.
	 * @param roomID The id of the room.
	 */
	public RoomDB(int roomID) {
		this.roomID = roomID;
		exitDB = new ExitDB(roomID);
		rewardDB = new RewardDB(roomID);
	}

	/**
	 * Creates a new RoomDB object with a room id and a player id.
	 * @param roomID The id of the room.
	 * @param playerID The id of the current player.
	 */
	public RoomDB(int roomID, int playerID) {
		this.roomID = roomID;
		visitRoomDB = new VisitRoomDB(roomID, playerID);
		itemRoomDB = new ItemRoomDB(roomID, playerID);
		exitDB = new ExitDB(roomID);
		monsterRoomDB = new MonsterRoomDB(roomID, playerID);
		puzzleRoomDB = new PuzzleRoomDB(roomID, playerID);
		rewardDB = new RewardDB(roomID);
	}

	/**
	 * Method: getRoom
	 * Gets this room from the database.
	 * @return This room.
	 */
	public Room getRoom() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Room WHERE RoomID = ?", roomID);
			resultSet.next();
			Room room = new Room(this, roomID);
			room.setRoomName(resultSet.getString("RoomName"));
			room.setRoomDescription(resultSet.getString("RoomDescription").replace("\\n", "\n"));
			room.setLevel(resultSet.getString("RoomLevel"));
			room.setHint(resultSet.getString("RoomHint"));
			resultSet.getStatement().close();
			return room;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	/**
	 * Method: getVisited
	 * Gets the visited status of this room.
	 * @return True if this room is marked as visited, otherwise false.
	 */
	public boolean getVisited() throws GameException {
		return visitRoomDB.getVisited();
	}

	/**
	 * Method: setVisited
	 * Sets this room as visited.
	 */
	public void setVisited() throws GameException {
		visitRoomDB.setVisited();
	}

	/**
	 * Method: getPuzzles
	 * Gets all puzzles currently in this room.
	 * @return The list of puzzles in this room.
	 */
	public List<Puzzle> getPuzzles() throws GameException {
		return puzzleRoomDB.getPuzzles();
	}

	/**
	 * Method: removePuzzle
	 * Removes a puzzle from this room.
	 * @param puzzle The puzzle to remove.
	 */
	public void removePuzzle(Puzzle puzzle) throws GameException {
		puzzleRoomDB.removePuzzle(puzzle);
	}

	/**
	 * Method: getRewards
	 * Gets all rewards in this room.
	 * @return The list of rewards in this room.
	 */
	public List<Item> getRewards() throws GameException {
		return rewardDB.getRewards();
	}

	/**
	 * Method: getItems
	 * Gets all items currently in this room.
	 * @return The list of items in this room.
	 */
	public List<Item> getItems() throws GameException {
		return itemRoomDB.getItems();
	}

	/**
	 * Method: addItem
	 * Adds an item to this room.
	 * @param item The item to add.
	 */
	public void addItem(Item item) throws GameException {
		itemRoomDB.addItem(item);
	}

	/**
	 * Method: removeItem
	 * Removes an item from this room.
	 * @param item The item to remove.
	 */
	public void removeItem(Item item) throws GameException {
		itemRoomDB.removeItem(item);
	}

	/**
	 * Method: getMonsters
	 * Gets all monsters currently in this room.
	 * @return The list of monsters in this room.
	 */
	public List<Monster> getMonsters() throws GameException {
		return monsterRoomDB.getMonsters();
	}

	/**
	 * Method: removeMonster
	 * Removes a monster from this room.
	 * @param monster The monster to remove.
	 */
	public void removeMonster(Monster monster) throws GameException {
		monsterRoomDB.removeMonster(monster);
	}

	/**
	 * Method: getExits
	 * Gets all exits in this room.
	 * @return The list of exits in this room.
	 */
	public List<Exit> getExits() throws GameException {
		return exitDB.getExits();
	}
}