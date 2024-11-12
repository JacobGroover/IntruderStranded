package IntruderStranded.controller;

import IntruderStranded.gameExceptions.*;

/**
 * Class: MainMenu
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 12th, 2024
 * 
 * This class – Is the Commands subclass for Main Menu Commands. Handles all user commands sent
 * from GameController and returns appropriate replies or exceptions.
 * Relevant while the player is logged in, but not yet in an active game session.
 */
public class MainMenuCommands extends Commands {

	/**
	 * 1-argument constructor for MainMenuCommands
	 * @param player
	 */
	public MainMenuCommands(Player player) {
		super(player);
	}

	/**
	 * Method: executeCommand
	 * Validates and then executes a command received from the GameController.
	 * Valid Commands (case-insensitive):
	 * Start new game commands - New Game, New [calls the newGame method]
	 * Load a saved game commands - Load Game, Load [calls the loadGame method]
	 * Exit [calls the exit method]
	 * Help [calls the help method]
	 * 
	 * Throws an exception for an invalid command
	 * @param command
	 */
	@Override()
	String executeCommand(String command) throws GameException {
		return switch (command) {
			case "NEW" -> newGame();
			case "LOAD" -> loadGame();
			case "HELP" -> help();
			case "EXIT" -> exit(command);
			default -> "Unrecognized command!";
		};
	}

	/**
	 * Method: newGame
	 * Starts a new game by calling GameDBCreate.newGame method.
	 * Calls the changeGameState method and changes the game state to GameplayCommands.
	 */
	private String newGame() throws GameException {
		// TODO - implement MainMenuCommands.newGame
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: help
	 * returns a String to the player giving them the list of commands available:
	 * New Game
	 * Load Game
	 * Exit
	 * Help
	 */
	@Override()
	String help() {
		return """
				Main Menu Commands
				
				New - Start a new game
				Load - Load a saved game
				Exit - Exits the application
				Help - This command, displays available commands
				""";
	}

	/**
	 * Method: getIntroText
	 * Returns a String to the calling method containing:
	 * Welcome message and Game title
	 * list of available commands
	 */
	protected String getIntroText() {
		return """
                Intruder Stranded

                Please select an option "New" Game or "Load" Game
                If you need help, please enter "HELP" to find more commands.
                Please enter "exit" to end the game.
                
                """;
	}

}