package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;
import IntruderStranded.model.RoomDB;

import java.util.List;

public abstract class Puzzle implements RoomEvent {
	private int id;
	int puzzleCounter;
	private final RoomDB roomDB;
	private boolean isCompleted;


	public Puzzle(int id, int roomID, int playerID) {
		this.id = id;
		roomDB = new RoomDB(roomID, playerID);
	}

	@Override
	public void delete() throws GameException {
		roomDB.removePuzzle(this);
	}

	/**
	 * Method: run
	 * Abstract method to be implemented in child classes. Runs the primary functionality of a puzzle.
	 *
	 * @return
	 */
	abstract String run(String cmd);

	/**
	 * Method: setupPuzzle
	 * Abstract method to be implemented in child classes. Sets up a puzzle to be run.
	 */
	abstract void setupPuzzle();

	abstract String getHint();

    public int getID() {
        return id;
    }

	/**
	 * Method: getRewards
	 * @return List<Item>
	 * Returns a List of rewards for solving the Puzzle from RoomDB
	 */
	public List<Item> getRewards() throws GameException {

            return roomDB.getRewards();
    }

	public boolean getIsCompleted() {
		return isCompleted;
	}

	protected void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
}