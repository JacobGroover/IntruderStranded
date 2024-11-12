package IntruderStranded.controller;

import IntruderStranded.gameExceptions.*;
import IntruderStranded.model.DBService;
import IntruderStranded.model.GameDBCreate;
import IntruderStranded.model.SQLiteDB;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

/**
 * Class: GameController
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 6th, 2024
 * 
 * This class – Is the UI to controller interface for Intruder Stranded.
 * All user interactions will be sent to this class to be sent on to Commands for further processing.
 * Uses Observer design pattern to sync game state with the commands object by implementing the Observer interface.
 */
public class GameController implements Observer<Commands> {

	private Commands commands;
	private String introText;

	private static final String DB_PATH = "IntruderStranded.db";

	/**
	 * No-Argument Constructor for the GameController class
	 * Instantiates the Commands object for the game as an Authentication object to force login authentication
	 * from the user after the user launches the game application.
	 * Calls getIntroText method to assign introText from AuthenticationCommands.
	 */
	public GameController() {
		commands = new AuthenticationCommands();
		commands.addObserver(this);
		storeIntroText();
	}

	/**
	 * Method : start
	 * Assigns the filepath for the database and starts DBService.
	 * Checks to see if the DB field exists and if not creates it
	 * by calling GameDBCreate buildTables().
	 */
	public void start() throws GameException {
		boolean notExists = Files.notExists(Path.of(DB_PATH));
        try {
            DBService.start(new SQLiteDB(DB_PATH, false));
        } catch (SQLException sqle) {
            throw new GameException(sqle.getMessage());
        }
        if (notExists) {
			GameDBCreate gdb = new GameDBCreate();
			gdb.buildTables();
		}
	}

	/**
	 * Method: displayIntroText
	 * Returns a String displaying the intro text. Used to display Login screen intro text before the game
	 * loop begins, and to append intro text after game state changes.
	 */
	public String displayIntroText() {
		String text = introText;
		introText = null;
		return text;
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
		// Instantiate String for storing return String from controller package
		StringBuilder response = new StringBuilder(commands.executeCommand(command));
		if (introText != null) {
			response.append('\n');
			response.append(displayIntroText());
		}
		return response.toString();
	}

	/**
	 * Method: getIntroText
	 * Calls commands.getIntroText() to retrieve the intro text for a Commands class.
	 * Stores the text in the introText class variable.
	 */
	private void storeIntroText() {introText = commands.getIntroText();}

	/**
	 * Method: onUpdate
	 * Updates the GameController to subscribe to the new game state by setting its commands class variable to
	 * this method's input parameter, then adding this GameController to the commands variable's list of observers by
	 * calling the commands.addObserver method.
	 * 
	 * Calls the GameController.getIntroText to update the introText String for the new game state.
	 * @param newGameState
	 */
	@Override()
	public void onUpdate(Commands newGameState) {
		commands = newGameState;
		commands.addObserver(this);
		storeIntroText();
	}

}