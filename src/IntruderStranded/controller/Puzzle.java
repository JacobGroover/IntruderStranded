package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;
import IntruderStranded.model.RoomDB;

import java.util.List;

/**
 * Class: Puzzle
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 14, 2024
 *
 * This class handles business logic for puzzles.
 */
public abstract class Puzzle implements RoomEvent {
	private int id;
	int puzzleCounter;
	private final RoomDB roomDB;
	private boolean isCompleted;

	/**
	 * Creates a new Puzzle with the given id, room id, and player id.
	 * @param id The id of the puzzle.
	 * @param roomID The room id of the puzzle.
	 * @param playerID The id of the current player.
	 */
	public Puzzle(int id, int roomID, int playerID) {
		this.id = id;
		roomDB = new RoomDB(roomID, playerID);
	}

	/**
	 * Method: delete
	 * Deletes this puzzle from the database.
	 */
	@Override
	public void delete() throws GameException {
		roomDB.removePuzzle(this);
	}

	/**
	 * Method: run
	 * Abstract method to be implemented in child classes. Runs the primary functionality of a puzzle.
	 *
	 * @return The string to display.
	 */
	abstract String run(String cmd);

	/**
	 * Method: setupPuzzle
	 * Abstract method to be implemented in child classes. Sets up a puzzle to be run.
	 */
	abstract void setupPuzzle();

	/**
	 * Method: getHint
	 * Gets the hint of this puzzle.
	 * @return The hint of this puzzle.
	 */
	abstract String getHint();

	/**
	 * Method: getID
	 * Gets the id of this puzzle.
	 * @return The id of this puzzle.
	 */
    public int getID() {
        return id;
    }

	/**
	 * Method: getRewards
	 * Returns a List of rewards for solving the Puzzle from RoomDB
	 * @return The list of rewards.
	 */
	public List<Item> getRewards() throws GameException {
		return roomDB.getRewards();
    }

	/**
	 * Method: getIsCompleted
	 * Gets if this puzzle has been completed.
	 * @return True if this puzzle has been completed, otherwise false.
	 */
	public boolean getIsCompleted() {
		return isCompleted;
	}

	/**
	 * Method: setCompleted
	 * Marks this puzzle as completed.
	 */
	protected void setCompleted() {
		this.isCompleted = true;
	}
}