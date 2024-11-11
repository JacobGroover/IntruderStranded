package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;
import IntruderStranded.model.RewardDB;
import IntruderStranded.model.RoomDB;

import java.util.List;
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
	private final RoomDB roomDB;

    /**
	 * No-argument Constructor for NumberGuessingPuzzle class
	 * Calls setupPuzzle() method to assign the answerNumber and puzzleCounter class attributes.
	 */
	public NumberGuessingPuzzle(int id, int roomID, int playerID) {
        super(id);
        roomDB = new RoomDB(roomID, playerID);
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
     */
	@Override()
	public void run(String cmd) {
		Scanner input = new Scanner(System.in);

		if(puzzleCounter == -1) {
			System.out.println("Guess a number between 1 and 10:");
			puzzleCounter ++;
		}

		while(puzzleCounter <= 3) {
			int guess = input.nextInt();

			if (guess == answerNumber) {
				System.out.println("You have successfully solved the puzzle!");
				getRewards();
				break;
			} else if (puzzleCounter == 3) {
				System.out.println("You lost this puzzle.");
				break;
			}
			else {
				System.out.println("Incorrect number, try again!");
				puzzleCounter++;
			}
		}

		setupPuzzle();
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

	/**
	 * Method: getRewards
	 * Gets the rewards from completing this puzzle.
	 */
	@Override()
	public List<Item> getRewards()  {
        try {
            return RewardDB.getRewards();
        } catch (GameException e) {
            throw new RuntimeException(e);
        }
    }

}