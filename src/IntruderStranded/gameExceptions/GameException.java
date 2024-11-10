package IntruderStranded.gameExceptions;

import java.io.*;

/**
 * Class: GameException
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 6th, 2024
 * 
 * This class – is the custom exception for the game. Inherits from IOException.
 */
public class GameException extends IOException {

	public GameException() {
		super();
	}

	/**
	 * 
	 * @param message
	 */
	public GameException(String message) {
		super(message);
	}

}