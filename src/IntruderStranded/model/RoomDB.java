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
	int roomID;
	VisitRoomDB visitRoomDB;
	ItemRoomDB itemRoomDB;
	ExitDB exitDB;
	MonsterRoomDB monsterRoomDB;
	PuzzleRoomDB puzzleRoomDB;
	RewardDB rewardDB;

	public RoomDB(int roomID) {
		this.roomID = roomID;
		exitDB = new ExitDB(roomID);
		rewardDB = new RewardDB(roomID);
	}

	public RoomDB(int roomID, int playerID) {
		this.roomID = roomID;
		visitRoomDB = new VisitRoomDB(roomID, playerID);
		itemRoomDB = new ItemRoomDB(roomID, playerID);
		exitDB = new ExitDB(roomID);
		monsterRoomDB = new MonsterRoomDB(roomID, playerID);
		puzzleRoomDB = new PuzzleRoomDB(roomID, playerID);
		rewardDB = new RewardDB(roomID);
	}

	public Room getRoom() throws GameException {
		try {
			ResultSet resultSet = DBService.getDB().queryPrepared("SELECT * FROM Room WHERE RoomID = ?", roomID);
			resultSet.next();
			Room room = new Room(this, roomID);
			room.setRoomName(resultSet.getString("RoomName"));
			room.setRoomDescription(resultSet.getString("RoomDescription"));
			room.setLevel(resultSet.getString("RoomLevel"));
			room.setHint(resultSet.getString("RoomHint"));
			resultSet.getStatement().close();
			return room;
		} catch (SQLException exception) {
			throw new GameException(exception.getMessage());
		}
	}

	public boolean getVisited() throws GameException {
		return visitRoomDB.getVisited();
	}

	public void setVisited() throws GameException {
		visitRoomDB.setVisited();
	}

	public List<Puzzle> getPuzzles() throws GameException {
		return puzzleRoomDB.getPuzzles();
	}

	public void removePuzzle(Puzzle puzzle) throws GameException {
		puzzleRoomDB.removePuzzle(puzzle);
	}

	public List<Item> getRewards() throws GameException {
		return rewardDB.getRewards();
	}

	public List<Item> getItems() throws GameException {
		return itemRoomDB.getItems();
	}

	public void addItem(Item item) throws GameException {
		itemRoomDB.addItem(item);
	}

	public void removeItem(Item item) throws GameException {
		itemRoomDB.removeItem(item);
	}

	public List<Monster> getMonsters() throws GameException {
		return monsterRoomDB.getMonsters();
	}

	public void removeMonster(Monster monster) throws GameException {
		monsterRoomDB.removeMonster(monster);
	}

	public List<Exit> getExits() throws GameException {
		return exitDB.getExits();
	}
}