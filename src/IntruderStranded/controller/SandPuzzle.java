package IntruderStranded.controller;

/**
 * Class: SandPuzzle
 * @author Hannah Jensen
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 12th, 2024
 * This class details the implementation for the SandPuzzle
 */
public class SandPuzzle extends Puzzle {

	public SandPuzzle(int id, int roomId, int playerId) {
		super(id, roomId, playerId);
		setupPuzzle();
	}

	/**
	 * Method: run
	 * returns "To throw this rope, you must align these numbers: The first is half of 18, The second is the number of seasons, The third is the sides of a hexagon."
	 * Checks if the input parameter is equal to "946".
	 * If it is not equal, return "The number you have entered is incorrect, try again."
	 * If it is equal, return "You have input the correct number."
	 *
	 * @return String
	 */
	@Override()
	String run(String cmd) {
		StringBuilder output = new StringBuilder();

		if(puzzleCounter == -1) {
			output.append("To throw this rope, you must align these numbers: \n " +
					"The first is half of 18, The second is the number of seasons, The third is the sides of a hexagon.");
			puzzleCounter ++;
		}
		else if (puzzleCounter == 3) {
			output.append("You've lost the puzzle, and fallen into the sand.");
			setupPuzzle();
		}
		else {
			if (cmd.equals("946")) {
				output.append("You have entered the correct number! You toss the rope to grab the missing item.");
				setIsCompleted(true);

			} else {
				output.append("The number you've entered is incorrect, try again.");
				puzzleCounter ++;
			}
		}

		return output.toString();
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




}