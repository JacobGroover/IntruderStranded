package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;
import IntruderStranded.model.RewardDB;
import IntruderStranded.model.RoomDB;

import java.util.*;

/**
 * Class: UnscrambleWordsPuzzle
 * @author Hannah Jensen
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 11th, 2024
 * This class details the implementation for the UnscrambleWordsPuzzle
 */
public class UnscrambledWordsPuzzle extends Puzzle {

	private String answerWord;
	private String scrambledWord;
	private final RoomDB roomDB;


	/**
	 * No-argument Constructor for UnscrambledWordsPuzzle class
	 * Calls setupPuzzle() method to assign the answerWord, scrambledWord, and puzzleCounter class attributes.
	 */
	public UnscrambledWordsPuzzle(int id, int roomID, int playerID) {
		super(id);
		roomDB = new RoomDB(roomID, playerID);
		setupPuzzle();
	}

	/**
     * Method: run
     * If puzzleCounter is -1, returns "Unscramble the planet's name: " + scrambledWord and increment puzzleCounter.
     * If puzzleCounter is not -1, check if the input parameter is equal to answerWord.
     * If it is not equal, increment the puzzleCounter and return "Incorrect word, try again."
     * If the puzzleCounter reaches 3, set puzzleCounter to -1 and return "You lost the puzzle."
     * If it is equal, return "You have successfully solved the puzzle!" and call getRewards method from
     * the implemented RoomEvent interface.
     *
     */
	@Override()
    void run(String cmd) {
		Scanner input = new Scanner(System.in);

		if (puzzleCounter == -1) {
			System.out.println("Unscramble the planets name: " + scrambledWord);
			puzzleCounter ++;
		}

		while(puzzleCounter <= 3) {
			String answer = input.nextLine();
			if (answer.equalsIgnoreCase(answerWord)) {
				System.out.println("You have successfully solved the puzzle!");
				getRewards();
				break;
			}else if (puzzleCounter == 3) {
				System.out.println("You have lost the puzzle.");
				puzzleCounter ++;
			}
			else {
				System.out.println("Incorrect word, try again.");
				puzzleCounter ++;
			}
		}

		setupPuzzle();
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
		Random random = new Random();

		List<String> planets = new ArrayList<>();
		planets.add("MERCURY");
		planets.add("VENUS");
		planets.add("EARTH");
		planets.add("MARS");
		planets.add("JUPITER");
		planets.add("SATURN");
		planets.add("URANUS");
		planets.add("NEPTUNE");

		answerWord = planets.get(random.nextInt(planets.size()));

		scrambledWord = scrambleWord(answerWord);

		puzzleCounter = -1;
	}

	public String scrambleWord(String word) {
		List<Character> charWord = new ArrayList<>();
		for(char c : word.toCharArray()) {
			charWord.add(c);
		}

		Collections.shuffle(charWord);

		StringBuilder scrambledWord = new StringBuilder();
		for(char c : charWord) {
			scrambledWord.append(c);
		}
		return scrambledWord.toString();
	}


	@Override
	String getHint() {
		return "The word is based on a planet in our solar system";
	}

	/**
	 * Method: getRewards
	 * Gets the rewards from completing this puzzle.
	 */
	@Override()
	public List<Item> getRewards() {
        try {
            return RewardDB.getRewards();
        } catch (GameException e) {
            throw new RuntimeException(e);
        }
    }

}