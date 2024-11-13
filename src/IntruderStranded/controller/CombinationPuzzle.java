package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class: CombinationPuzzle
 * @author Hannah Jensen
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 12th, 2024
 * This class details the implementation for the CombinationPuzzle
 */
public class CombinationPuzzle extends Puzzle {

	private String answerNumber;
	private List<String> guessArray;
	private List<String> answerArray;

	/**
	 * No-argument Constructor for CombinationPuzzle class
	 * Calls setupPuzzle() method to assign the numberX, answerNumber, and puzzleCounter class attributes.
	 */
	public CombinationPuzzle(int id, int roomID, int playerID) {
		super(id, roomID, playerID);
		setupPuzzle();
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
	 * When a correct letter is guessed, guessArray is updated to show everywhere that digit appears
	 * in answerNumber.
	 * If the puzzleCounter reaches 5, call setupPuzzle method and return "You lost the puzzle."
	 * If all digits are guessed, return "You have successfully solved the puzzle!" and call getRewards
	 * method from the implemented RoomEvent interface.
	 *
	 * @return String
	 */
	@Override()
    public String run(String cmd) {
		StringBuilder output = new StringBuilder();


		if (puzzleCounter == -1) {
			output.append("To open the chest, enter a number between 0 and 9 to guess\n" +
					"the combination: ___");
			puzzleCounter++;
		} else if (puzzleCounter >= 5) {
			output.append("You've lost the puzzle, and cannot open the chest.");
			setupPuzzle();
		} else {

			try {
				int guessInt = Integer.parseInt(cmd);
				boolean guessIsHot = isHot(guessInt);

				if (guessInt < 0 || guessInt > 9) {
					throw new GameException("Please enter a number between 0 and 9.");
				}

				boolean correctGuess = false;

				for (int i = 0; i < answerNumber.length(); i++) {
					if (answerNumber.charAt(i) == cmd.charAt(i)) {
						guessArray.set(i, answerNumber.substring(i, i + 1));
						correctGuess = true;
					}
				}
				puzzleCounter++;


				if (!correctGuess) {
					output.append("Incorrect number, try again. \n You have " + (puzzleCounter - 1) + " guesses left.");
					if (guessIsHot) {
						output.append("You were close to a digit in the combination");
					} else {
						output.append("You were not close to a digit in the combination.");
					}
					output.append(guessArray);
					output.append("Enter a number between 0 and 9:");

				} else if (answerArray.equals(guessArray)) {
					setIsCompleted(true);
					output.append("You've solved the puzzle, and can now open the chest!");

				} else {
					output.append(guessArray);
					output.append("Enter a number between 0 and 9:");
				}

			} catch (NumberFormatException | GameException ex) {
				System.out.println("Please enter a valid number.");
			}
		}


		return output.toString();
	}

	/**
	 * Method: isHot
	 * @return boolean
	 * Checks to see if guessInt is +- one of the numbers in answerArray
	 */
	private boolean isHot(int guessInt) {
        for (String s : answerArray) {
            int number = Integer.parseInt(s);
            if (number + 1 == guessInt || number - 1 == guessInt) {
                return true;
            }
        }
		return false;
	}

	/**
	 * Method: setupPuzzle
	 * Generates a random integer between 100 and 999 and assigns it to the answerNumber class attribute after converting it to a String.
	 * answerNumber is put into an ArrayList, answerArray.
	 * guessArray is set to "_", "_", "_"
	 * Sets the puzzleCounter class attribute to -1.
	 */
	@Override()
	void setupPuzzle() {
		Random random = new Random();
		answerNumber = String.valueOf(random.nextInt(900) + 100);
		puzzleCounter = -1;

		answerArray = new ArrayList<>();
		for(int i = 0; i < answerNumber.length(); i++) {
			answerArray.add(String.valueOf(answerNumber.charAt(i)));
		}

		guessArray = new ArrayList<>();
		guessArray.add("_");
		guessArray.add("_");
		guessArray.add("_");

	}

	/**
	 * Method: getHint
	 * @return String hint
	 * Returns a hint for the puzzle
	 */
	@Override
	String getHint() {
		return "Pick a number between 0-9";
	}

}