package IntruderStranded.controller;

import IntruderStranded.gameExceptions.*;

/**
 * Class: GameController
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 19th, 2024
 * 
 * This class – Is the UI to controller interface for Intruder Stranded.
 * All user interactions will be sent to this class to be sent on to Commands for further processing.
 * Uses Observer design pattern to sync game state with the commands object by implementing the Observer interface.
 */
public class GameController implements Observer<Commands> {

	private Commands commands = new AuthenticationCommands();
	private String introText;

	/**
	 * Method: GameController
	 * No-Argument Constructor for the GameController class
	 * Instantiates the Commands object for the game as an Authentication object to force login authentication
	 * from the user after the user launches the game application.
	 */
	public GameController() {
		// TODO - implement GameController.GameController
		throw new UnsupportedOperationException();
	}

	/**
	 * Method : start
	 * Checks to see if the DB field exists and if not creates it
	 * by calling GameDBCreate buildTables().
	 */
	public void start() throws GameException {
		// TODO - implement GameController.start
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: executeCommand
	 * Handles the user input from IntruderStranded
	 * Determines the current GameState and Sends the user's command to the appropriate Commands
	 * child class for processing
	 * If introText String is not null, then appends it to the return before returning to IntruderStranded
	 * calling class.
	 * After appending introText, sets it back to null.
	 * throws an exception if the command is not valid
	 * @param command
	 */
	public String executeCommand(String command) throws GameException {
		// TODO - implement GameController.executeCommand
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getIntroText
	 * Calls commands.getIntroText() to retrieve the intro text for a Commands class.
	 * Stores the text in the introText class variable.
	 */
	private void getIntroText() {
		// TODO - implement GameController.getIntroText
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: onUpdate
	 * Updates the GameController to subscribe to the new game state by setting its commands class variable to this method's input parameter, then adding this GameController to the commands variable's list of observers by calling the commands.addObserver method.
	 * 
	 * Calls the GameController.getIntroText to update the introText String for the new game state.
	 * @param newGameState
	 */
	@Override()
	public void onUpdate(Commands newGameState) {
		// TODO - implement GameController.onUpdate
		throw new UnsupportedOperationException();
	}

}