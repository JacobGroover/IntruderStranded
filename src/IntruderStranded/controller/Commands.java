package IntruderStranded.controller;

import IntruderStranded.gameExceptions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: Commands
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 7th, 2024
 * 
 * This class – Handles commands from the user. The command is parsed, type of command determined and
 * then routed to correct methods to handle the command.
 * Uses Observer design pattern to sync game state with the GameController that called it by updating a List of Observer interfaces.
 */
public abstract class Commands {

	Player player;
	private final List<Observer<Commands>> observers;

	/**
	 * Method: Commands
	 * No-Argument Constructor for the Commands class
	 * Instantiates the observers List as an ArrayList
	 */
	Commands() {
		observers = new ArrayList<Observer<Commands>>();
	}

	/**
	 * 1-argument Constructor for Commands class
	 * calls no-argument constructor, then assigns the input parameter to the player class variable.
	 * @param player
	 */
	Commands(Player player) {
		this();
		this.player = player;
	}

	/**
	 * Method: executeCommand
	 * Validates and then executes a command received from the GameController. Validation depends on the
	 * current state of the game (i.e. the child class of Commands that is performing the validation)
	 * @param command
	 */
	abstract String executeCommand(String command) throws GameException;

	/**
	 * Method: help
	 * Abstract help method for when the player types the help command. Implementation in child classes.
	 */
	abstract String help();

	/**
	 * Method: exit
	 * Returns the String "Exit" indicating the player is exiting the game.
	 * @param command
	 */
	String exit(String command) throws GameException {
		if (command.equals("EXIT")) {
			return command;
		}
		throw new GameException("Unrecognized command!");
	}

	/**
	 * Method: changeGameState
	 * Parses through the List of observers and calls the onUpdate method for each observer.
	 * This will update all observers to listen for the new Command object passed in as a parameter, instead
	 * of this object.
	 * @param command
	 */
	void changeGameState(Commands command) {
		for (Observer<Commands> observer : observers) {
			observer.onUpdate(command);
		}
	}

	/**
	 * Method: addObserver
	 * Adds a new observer to the List of observers
	 * @param observer
	 */
	void addObserver(Observer<Commands> observer) {
		// TODO - implement Commands.addObserver
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: loadGame
	 * Loads a saved game from the database for the current player.
	 * If the database does not have a saved game for the current player, return "No save has been made."
	 * If the database has a saved game for the current player, then load that saved game by calling
	 * GameDBCreate.loadGame method, and call the changeGameState method to change the game state
	 * to GameplayCommands if it is not already.
	 */
	String loadGame() throws GameException {
		// TODO - implement Commands.loadGame
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getIntroText
	 * Abstract method to be implemented in child classes. Returns a String specific to the child class.
	 * Should be called when a new Commands child class is instantiated.
	 */
	protected abstract String getIntroText();

}