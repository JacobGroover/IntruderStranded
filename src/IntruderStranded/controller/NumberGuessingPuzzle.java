package IntruderStranded.controller;

import java.util.Random;
import java.util.Scanner;

/**
 * Class: NumberGuessingPuzzle
 * @author Hannah Jensens
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 11th, 2024
 * This class details the implementation for the NumberGuessingPuzzle
 */
public class NumberGuessingPuzzle extends Puzzle {

	private int answerNumber;

    /**
	 * No-argument Constructor for NumberGuessingPuzzle class
	 * Calls setupPuzzle() method to assign the answerNumber and puzzleCounter class attributes.
	 */
	public NumberGuessingPuzzle(int id, int roomID, int playerID) {
        super(id, roomID, playerID);
        setupPuzzle();
	}


	/**
	 * Method: run
	 * If puzzleCounter is -1, returns "Guess a number between 1 and 10: " and increment puzzleCounter.
	 * If puzzleCounter is not -1, check if the input parameter is equal to answerNumber.
	 * If it is not equal, increment the puzzleCounter and return "Incorrect number, try again."
	 * If the puzzleCounter reaches 3, call setupPuzzle method and return "You lost the puzzle."
	 * If it is equal, return "You have successfully solved the puzzle!" and call getRewards method from
	 * the implemented RoomEvent interface.
	 *
	 * @return String
	 */
	@Override()
	public String run(String cmd) {
		StringBuilder output = new StringBuilder();

		if(puzzleCounter == -1) {
			output.append("Guess a number between 1 and 10:");
			puzzleCounter ++;
		}
		else if (puzzleCounter == 3) {
			output.append("You lost this puzzle.");
			setupPuzzle();
		}
		else {
			int guess = Integer.parseInt(cmd);
			if (guess == answerNumber) {
				output.append("You have successfully solved the puzzle!");
				setCompleted();
			}
			else {
				output.append("Incorrect number, try again!");
				puzzleCounter++;
			}
		}
		return output.toString();
	}

	/**
	 * Method: setupPuzzle
	 * Generates a random number between 1 and 10. Assigns the answerNumber class attribute to
	 * equal the random number.
	 * 
	 * Sets the puzzleCounter class attribute to -1.Random random = new Random();
	 * 		answerNumber = random.nextInt(10) + 1;
	 * 		puzzleCounter = -1;
	 */
	@Override()
	void setupPuzzle() {
		Random random = new Random();
		answerNumber = random.nextInt(10) + 1;
		puzzleCounter = -1;
	}

	@Override
	String getHint() {
		return "Pick a number between 1-10";
	}

}