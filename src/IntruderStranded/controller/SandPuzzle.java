package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;
import IntruderStranded.model.RewardDB;
import IntruderStranded.model.RoomDB;

import java.util.List;
import java.util.Scanner;

/**
 * Class: SandPuzzle
 * @author Hannah Jensen
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 12th, 2024
 * This class details the implementation for the SandPuzzle
 */
public class SandPuzzle extends Puzzle {

	private final RoomDB roomDB;


	public SandPuzzle(int id, int roomId, int playerId) {
		super(id);
		roomDB = new RoomDB(roomId, playerId);
		setupPuzzle();
	}

	/**
     * Method: run
     * returns "To throw this rope, you must align these numbers: The first is half of 18, The second is the number of seasons, The third is the sides of a hexagon."
     * Checks if the input parameter is equal to "946".
     * If it is not equal, return "The number you have entered is incorrect, try again."
     * If it is equal, return "You have input the correct number." and call getRewards method from the implemented RoomEvent interface.
     *
     */
	@Override()
    void run(String cmd) {
		Scanner input = new Scanner(System.in);
		if(puzzleCounter == -1) {
			System.out.println("To throw this rope, you must align these numbers: \n " +
					"The first is half of 18, The second is the number of seasons, The third is the sides of a hexagon.");
			puzzleCounter ++;
		}
		while(puzzleCounter <= 3) {
			String answer = input.nextLine();
			if (answer.equals("946")) {
				System.out.println("You have entered the correct number! You toss the rope to grab the missing item.");
				getRewards();
				break;
			} else if (puzzleCounter == 3) {
				System.out.println("You've lost the puzzle, and fallen into the sand.");
				break;
			} else {
				System.out.println("The number you've entered is incorrect, try again.");
				puzzleCounter ++;
			}
		}
		puzzleCounter = -1;
	}

	@Override()
	void setupPuzzle() {
		System.out.println("You encounter a part of their ship slowly sinking into a pool of moving sand. \n" +
				"The goal is to use a rope to retrieve the part before it disappears beneath the sand. \n" +
				"You a limited number of attempts to succeed before addition help is offered.");
		puzzleCounter = -1;
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
		System.out.println("You retrieved the Missing Wing part from the sand.");
        try {
            return RewardDB.getRewards();
        } catch (GameException e) {
            throw new RuntimeException(e);
        }
    }

}