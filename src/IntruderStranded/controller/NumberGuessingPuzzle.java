package IntruderStranded.controller;

/**
 * Class: NumberGuessingPuzzle
 * @author
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 25th, 2024
 * This class details the implementation for the NumberGuessingPuzzle
 */
public class NumberGuessingPuzzle extends Puzzle {

	private int answerNumber;

	/**
	 * No-argument Constructor for NumberGuessingPuzzle class
	 * Calls setupPuzzle() method to assign the answerNumber and puzzleCounter class attributes.
	 */
	public NumberGuessingPuzzle(int id) {
        super(id);
        // TODO - implement NumberGuessingPuzzle.NumberGuessingPuzzle
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: run
	 * If puzzleCounter is -1, returns "Guess a number between 1 and 10: " and increment puzzleCounter.
	 * If puzzleCounter is not -1, check if the input parameter is equal to answerNumber.
	 * If it is not equal, increment the puzzleCounter and return "Incorrect number, try again."
	 * If the puzzleCounter reaches 3, call setupPuzzle method and return "You lost the puzzle."
	 * If it is equal, return "You have successfully solved the puzzle!" and call getRewards method from
	 * the implemented RoomEvent interface.
	 * @param cmd
	 */
	@Override()
	String run(String cmd) {
		// TODO - implement NumberGuessingPuzzle.run
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: setupPuzzle
	 * Generates a random number between 1 and 10. Assigns the answerNumber class attribute to
	 * equal the random number.
	 * 
	 * Sets the puzzleCounter class attribute to -1.
	 */
	@Override()
	void setupPuzzle() {
		// TODO - implement NumberGuessingPuzzle.setupPuzzle
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getRewards
	 * Gets the rewards from completing this puzzle.
	 */
	@Override()
	public java.util.List<Item> getRewards() {
		// TODO - implement NumberGuessingPuzzle.getRewards
		throw new UnsupportedOperationException();
	}

}