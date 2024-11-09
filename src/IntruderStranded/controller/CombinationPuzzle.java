package IntruderStranded.controller;

import java.util.List;

/**
 * Class: CombinationPuzzle
 * @author
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 25th, 2024
 * This class details the implementation for the CombinationPuzzle
 */
public class CombinationPuzzle extends Puzzle {

	private String answerNumber;
	private String unknownNumber;

	/**
	 * No-argument Constructor for CombinationPuzzle class
	 * Calls setupPuzzle() method to assign the numberX, answerNumber, and puzzleCounter class attributes.
	 */
	public CombinationPuzzle(int id) {
		super(id);
		// TODO - implement CombinationPuzzle.CombinationPuzzle
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: run
	 * If puzzleCounter is -1, returns unknownNumber + "\nEnter a number between 0 and 9 to guess
	 * the combination: " and increments puzzleCounter.
	 * If puzzleCounter is not -1, check if the input parameter equals a digit in answerNumber. If it is
	 * not equal, increment the puzzleCounter and return "Incorrect number, try again. You have " +
	 * (5 - puzzleCounter) + " guesses left." If the input parameter was one of the numbers in the
	 * digit +/- 1, then also return "You were close to a digit in the combination." Otherwise,
	 * also return "You were not close to a digit in the combination."
	 * If the player enters text that is not a number between 0 and 9, throw a GameException with the
	 * message, "Please enter a number between 0 and 9."
	 * After every guess, unknownNumber is displayed again.
	 * When a correct letter is guessed, unknownNumber is updated to show everywhere that digit appears
	 * in answerNumber.
	 * If the puzzleCounter reaches 5, call setupPuzzle method and return "You lost the puzzle."
	 * If all digits are guessed, return "You have successfully solved the puzzle!" and call getRewards
	 * method from the implemented RoomEvent interface.
	 * @param cmd
	 */
	@Override()
	String run(String cmd) {
		// TODO - implement CombinationPuzzle.run
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: setupPuzzle
	 * Generates a random integer between 100 and 999 and assigns it to the answerNumber class attribute after converting it to a String.
	 * Once the number String is assigned, that number is then replaced with underscores and saved to the
	 * unknownNumber class attribute, separated by spaces.
	 * Sets the puzzleCounter class attribute to -1.
	 */
	@Override()
	void setupPuzzle() {
		// TODO - implement CombinationPuzzle.setupPuzzle
		throw new UnsupportedOperationException();
	}

	@Override
	String getHint() {
		return "Pick a number between 0-9";
	}

	/**
	 * Method: getRewards
	 * Gets the rewards from completing this puzzle.
	 */
	@Override()
	public List<Item> getRewards() {
		// TODO - implement CombinationPuzzle.getRewards
		throw new UnsupportedOperationException();
	}

}