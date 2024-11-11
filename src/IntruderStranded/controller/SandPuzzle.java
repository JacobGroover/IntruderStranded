package IntruderStranded.controller;

import java.util.List;

/**
 * Class: SandPuzzle
 * @author
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 25th, 2024
 * This class details the implementation for the SandPuzzle
 */
public class SandPuzzle extends Puzzle {

	public SandPuzzle(int id) {
		super(id);
	}

	/**
     * Method: run
     * returns "To throw this rope, you must align these numbers: The first is half of 18, The second is the number of seasons, The third is the sides of a hexagon."
     * Checks if the input parameter is equal to "946".
     * If it is not equal, return "The number you have entered is incorrect, try again."
     * If it is equal, return "You have input the correct number." and call getRewards method from the implemented RoomEvent interface.
     *
     * @param cmd
     */
	@Override()
    void run(String cmd) {
		// TODO - implement SandPuzzle.run
		throw new UnsupportedOperationException();
	}

	@Override()
	void setupPuzzle() {
		// TODO - implement SandPuzzle.setupPuzzle
		throw new UnsupportedOperationException();
	}

	@Override
	String getHint() {
		return "The first number is 9 and the second number is 4";
	}

	/**
	 * Method: getRewards
	 * Gets the rewards from completing this puzzle.
	 */
	@Override()
	public List<Item> getRewards() {
		// TODO - implement SandPuzzle.getRewards
		throw new UnsupportedOperationException();
	}

}