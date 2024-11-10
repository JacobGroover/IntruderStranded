package IntruderStranded.controller;

import java.util.List;

/**
 * Class: UnscrambleWordsPuzzle
 * @author
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 25th, 2024
 * This class details the implementation for the UnscrambleWordsPuzzle
 */
public class UnscrambledWordsPuzzle extends Puzzle {

	private String answerWord;
	private String scrambledWord;

	/**
	 * No-argument Constructor for UnscrambledWordsPuzzle class
	 * Calls setupPuzzle() method to assign the answerWord, scrambledWord, and puzzleCounter class attributes.
	 */
	public UnscrambledWordsPuzzle(int id) {
		super(id);
		// TODO - implement UnscrambledWordsPuzzle.UnscrambledWordsPuzzle
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: run
	 * If puzzleCounter is -1, returns "Unscramble the planet's name: " + scrambledWord and increment puzzleCounter.
	 * If puzzleCounter is not -1, check if the input parameter is equal to answerWord.
	 * If it is not equal, increment the puzzleCounter and return "Incorrect word, try again."
	 * If the puzzleCounter reaches 3, set puzzleCounter to -1 and return "You lost the puzzle."
	 * If it is equal, return "You have successfully solved the puzzle!" and call getRewards method from
	 * the implemented RoomEvent interface.
	 * @param cmd
	 */
	@Override()
	String run(String cmd) {
		// TODO - implement UnscrambledWordsPuzzle.run
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: setupPuzzle
	 * Generates a random number between 1 and 8. Depending on which number is chosen assigns the
	 * answerWord class attribute to a planet name:
	 * 1 - MERCURY
	 * 2 - VENUS
	 * 3 - EARTH
	 * 4 - MARS
	 * 5 - JUPITER
	 * 6 - SATURN
	 * 7 - URANUS
	 * 8 - NEPTUNE
	 * 
	 * Once the word is assigned, a scrambled version of that word is then assigned to the scrambledWord
	 * class attribute.
	 * Sets the puzzleCounter class attribute to -1.
	 */
	void setupPuzzle() {
		// TODO - implement UnscrambledWordsPuzzle.setupPuzzle
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getRewards
	 * Gets the rewards from completing this puzzle.
	 */
	@Override()
	public List<Item> getRewards() {
		// TODO - implement UnscrambledWordsPuzzle.getRewards
		throw new UnsupportedOperationException();
	}

}