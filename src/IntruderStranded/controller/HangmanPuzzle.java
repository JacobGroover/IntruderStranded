package IntruderStranded.controller;

/**
 * Class: HangmanPuzzle
 * @author
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 25th, 2024
 * This class details the implementation for the HangmanPuzzle
 */
public class HangmanPuzzle extends Puzzle {

	private String answerWord;
	private String unknownWord;

	/**
	 * No-argument Constructor for HangmanPuzzle class
	 * Calls setupPuzzle() method to assign the answerWord, unknownWord, and puzzleCounter class attributes.
	 */
	HangmanPuzzle() {
		// TODO - implement HangmanPuzzle.HangmanPuzzle
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: run
	 * If puzzleCounter is -1, returns unknownWord + "\nEnter a letter to guess the word: " and increments puzzleCounter.
	 * If puzzleCounter is not -1, check if the input parameter equals a letter in answerWord. If it is not equal,
	 * increment the puzzleCounter and return "The letter you have guessed is not in the unknown word.
	 * You have " + (5 - puzzleCounter) + " guesses left."
	 * If the player enters text that is not one letter of the alphabet throw a GameException with the
	 * message, "Please enter a letter."
	 * After every guess, unknownWord is displayed again, as well as a list of letters the player has
	 * guessed so far (correct and incorrect).
	 * When a correct letter is guessed, unknownWord is updated to show everywhere that letter appears in answerWord.
	 * If the puzzleCounter reaches 5, call setupPuzzle method and return "You lost the puzzle."
	 * If it is equal, return "You have successfully solved the puzzle!" and call getRewards method from
	 * the implemented RoomEvent interface.
	 * @param cmd
	 */
	@Override()
	String run(String cmd) {
		// TODO - implement HangmanPuzzle.run
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: setupPuzzle
	 * Generates a random number between 1 and 12. Depending on which number is chosen assigns the
	 * answerWord class attribute to a word or phrase:
	 * 1 - ASTEROID
	 * 2 - ASTRONAUT
	 * 3 - BIG BANG
	 * 4 - BLACK HOLE
	 * 5 - COMET
	 * 6 - ECLIPSE
	 * 7 - GALAXY
	 * 8 - GRAVITY
	 * 9 - METEOR
	 * 10 - MILKY WAY
	 * 11 - SOLSTICE
	 * 12 - STARBUST
	 * 
	 * Once the word is assigned, that word is then replaced with underscores and saved to the
	 * unknownWord class attribute. (spaces are not assigned an underscore)
	 * Sets the puzzleCounter class attribute to -1.
	 */
	@Override()
	void setupPuzzle() {
		// TODO - implement HangmanPuzzle.setupPuzzle
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getRewards
	 * Gets the rewards from completing this puzzle.
	 */
	@Override()
	public java.util.List<Item> getRewards() {
		// TODO - implement HangmanPuzzle.getRewards
		throw new UnsupportedOperationException();
	}

}