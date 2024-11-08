package IntruderStranded.controller;

import java.util.List;

/**
 * Class: MathPuzzle
 * @author
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 25th, 2024
 * This class details the implementation for the MathPuzzle
 */
public class MathPuzzle extends Puzzle {

	private int numberX;
	private int answerNumber;

	/**
	 * No-argument Constructor for HangmanPuzzle class
	 * Calls setupPuzzle() method to assign the numberX, answerNumber, and puzzleCounter class attributes.
	 */
	public MathPuzzle(int id) {
		super(id);
		// TODO - implement MathPuzzle.MathPuzzle
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: run
	 * If puzzleCounter is -1, returns "What number completes the equation?\n" + numberX + " + _ = " +
	 * (numberX + answerNumber), and increments puzzleCounter.
	 * If puzzleCounter is not -1, check if the input parameter is equal to answerNumber.
	 * If it is not equal, increment the puzzleCounter and return "The number you've entered is incorrect,
	 * try again."
	 * If the puzzleCounter reaches 2, call setupPuzzle method and return "You lost the puzzle."
	 * If it is equal, return "You have successfully solved the puzzle!" and call getRewards method from
	 * the implemented RoomEvent interface.
	 * @param cmd
	 */
	@Override()
	String run(String cmd) {
		// TODO - implement MathPuzzle.run
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: setupPuzzle
	 * Generates a random integer between 0 and 9 and assigns it to the numberX class attribute.
	 * Generates another random number between 0 and 9 and assigns it to the answerNumber class attribute.
	 * Sets the puzzleCounter class attribute to -1.
	 */
	@Override()
	void setupPuzzle() {
		// TODO - implement MathPuzzle.setupPuzzle
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getRewards
	 * Gets the rewards from completing this puzzle.
	 */
	@Override()
	public List<Item> getRewards() {
		// TODO - implement MathPuzzle.getRewards
		throw new UnsupportedOperationException();
	}

}