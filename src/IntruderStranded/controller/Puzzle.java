package IntruderStranded.controller;

public abstract class Puzzle implements RoomEvent {
	private int id;
	int puzzleCounter;

	public Puzzle(int id) {
		this.id = id;
	}

	/**
	 * Method: run
	 * Abstract method to be implemented in child classes. Runs the primary functionality of a puzzle.
	 * @param cmd
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
}