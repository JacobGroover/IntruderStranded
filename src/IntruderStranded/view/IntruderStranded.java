package IntruderStranded.view;

import IntruderStranded.controller.*;

/**
 * Class: IntruderStranded
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 19th, 2024
 * 
 * This class – is the UI class for Intruder Stranded, a text-based adventure game. This class
 * will control all user aspects of this game.
 */
public class IntruderStranded {

	private Scanner input;
	private GameController gc;

	/**
	 * Method: IntruderStranded
	 * No-argument Constructor for the IntruderStranded class
	 * Creates an instance of the GameController class which is the interface into the controller package
	 */
	public IntruderStranded() {
		// TODO - implement IntruderStranded.IntruderStranded
		throw new UnsupportedOperationException();
	}

	/**
	 * Method playGame
	 * Allows the player to play the game.
	 * Prints an introduction message by calling GameController.getIntroText(), then starts game loop.
	 * Loops until the user chooses to exit.
	 * If an invalid command is entered, catches the exception from the controller and prints the message in that exception.
	 * Calls getCommand to get users input.
	 * Passes the user's command to GameController executeCommand method for processing, which will
	 * handle all user commands.
	 */
	private void playGame() {
		// TODO - implement IntruderStranded.playGame
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getCommand
	 * Prompts the user for their input and returns this to playGame method
	 */
	private String getCommand() {
		// TODO - implement IntruderStranded.getCommand
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: main
	 * Creates an instance of the IntruderStranded class to start the game.
	 * Calls the GameController start method to find the data file for the game.
	 * If the data file is not found, prints an exception message and exits.
	 * If the data file is found and successfully loaded, initializes the Scanner, calls the playGame method,
	 * and then closes the Scanner, exits the application.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO - implement IntruderStranded.main
		throw new UnsupportedOperationException();
	}

}