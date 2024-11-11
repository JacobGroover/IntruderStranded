package IntruderStranded.controller;

import IntruderStranded.gameExceptions.*;

/**
 * Class: Authentication
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 8th, 2024
 * 
 * This class – Is the Commands subclass for Authentication Commands. Handles all user commands
 * sent from GameController and returns appropriate replies or exceptions.
 * Relevant after the player has launched the Intruder Stranded application, but before they
 * are logged in.
 */
public class AuthenticationCommands extends Commands {

	private int loginCounter;
	private boolean isLoggingIn;
	private boolean isCreatingAccount;
	private boolean isResettingPassword;
	private boolean isRetrievingUsername;
	private String username;
	private String password;
	private String email;

	/**
	 * Method: AuthenticationCommands
	 * No-Argument Constructor for the AuthenticationCommands class
	 * Calls parent no-argument constructor, then initializes counters to 0 and booleans to false.
	 * Sets username, password, and email Strings to null
	 */
	AuthenticationCommands() {
		super();
		loginCounter = 0;
		isLoggingIn = false;
		isCreatingAccount = false;
		isResettingPassword = false;
		isRetrievingUsername = false;
		username = null;
		password = null;
		email = null;
	}

	/**
	 * Method: executeCommand
	 * Validates and then executes a command received from the GameController.
	 * Valid Commands (case-insensitive):
	 * Login [calls the login method]
	 * Create Account [calls the createAccount method]
	 * Reset Password [calls the resetPassword method]
	 * Retrieve Username [calls the retrieveUsername method]
	 * Exit [calls the exit method]
	 * Help [calls the help method]
	 * 
	 * Routes commands based on true state of class booleans
	 * Throws an exception for an invalid command
	 * @param command
	 */
	@Override()
	String executeCommand(String command) throws GameException {
		if (!isLoggingIn && !isCreatingAccount && !isResettingPassword && !isRetrievingUsername) {
			switch (command) {
				case "LOGIN" -> {
					return login(command);
				}
				case "CREATE ACCOUNT" -> {
					return createAccount(command);
				}
				case "FORGOT PASSWORD" -> {
					return resetPassword(command);
				}
				case "RETRIEVE USERNAME" -> {
					return retrieveUsername(command);
				}
				case "HELP" -> {
					return help();
				}
				case "EXIT" -> {
					return exit(command);
				}
				default -> throw new GameException("Unrecognized command!");
			}
		} else if (command.equals("EXIT")) {
			return exit(command);
		} else if (isLoggingIn) {
			return login(command);
		} else if (isCreatingAccount) {
			return createAccount(command);
		} else if (isResettingPassword) {
			return resetPassword(command);
		} else if (isRetrievingUsername) {
			return retrieveUsername(command);
		} else {
			throw new GameException("Unrecognized command!");
		}
	}

	/**
	 * Method: login
	 * Facilitates player login.
	 * Prompts the player for their username, then prompts again for their password.
	 * If the entered username and password combination do not match an existing account, return
	 * "Login failed. Please try again." to the calling method.
	 * If it is the third time the player fails the login attempt, also print "If you have forgotten your player account
	 * please enter "Retrieve Username" to retrieve username, or "Forgot Password" to reset password."
	 * 
	 * On successful login, instantiates the player class attribute with the playerID from the database
	 * entry, then calls the changeGameState method to change the game state to
	 * new MainMenuCommands(player).
	 * 
	 * Uses the loginCounter variable to track login attempts. After third attempt, resets login counter to 0.
	 * Sets isLoggingIn boolean to true when method begins. Does not set it to false until third
	 * attempt fails or login is successful.
	 * @param command
	 */
	private String login(String command) throws GameException {
		// TODO - implement AuthenticationCommands.login
		throw new UnsupportedOperationException();
	}

	/**
	 * Method:createAccount
	 * Facilitates creation of account by player.
	 * Sets isCreatingAccount boolean to true.
	 * 
	 * If username class attribute is null:
	 * Prompts player for a username 4-10 characters long (inclusive).
	 * If username is not within that length, returns "Username must be between 4 and 10 characters long."
	 * After valid username entry:
	 * 
	 * If password is null, prompts player for a password 8-12 characters long (inclusive).
	 * On invalid password length, return "Password must be between 8 and 12 characters long."
	 * On valid password entry:
	 * 
	 * If email is null, player is prompted for an email address. If email address is more than 20
	 * characters or does not contain both a "." character and an "@" character, then return "Email
	 * must contain a '.' and '@' character, and be at most 20 characters long."
	 * On valid email entry, set isCreatingAccount boolean to false
	 * 
	 * On valid email entry, create the account by calling
	 * GameDBCreate.createAccount(username, password, email) and return
	 * "Successfully created account. Please login to continue."
	 * If an account with the same username or email already exists, then
	 * GameDBCreate.createAccount should throw a GameException "Account already exists,
	 * please try logging in."
	 * This GameException is caught in AuthenticationCommands createAccount method. When
	 * caught, sets username, password, and email back to null
	 * 
	 * 
	 * During this process, valid username, password, and email are stored in class Strings.
	 * When all are valid, stores them in the database, then sets them back to null
	 * @param command
	 */
	private String createAccount(String command) throws GameException {
		// TODO - implement AuthenticationCommands.createAccount
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: resetPassword
	 * Facilitates the player resetting their password.
	 * Prompts player for their username. If no account exists with the given username, return
	 * "Username does not exist." If username is found, return "Username found." and prompt for password.
	 * If password is not between 8 and 12 characters, return "Password must be between 8 and 12 characters long."
	 * If valid password entry, set the new password to the account and return "Successfully reset password."
	 * 
	 * Sets isResettingPassword boolean to true when method begins. Sets it back to false on valid
	 * password reset.
	 * @param command
	 */
	private String resetPassword(String command) throws GameException {
		// TODO - implement AuthenticationCommands.resetPassword
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: retrieveUsername
	 * Facilitates the player retrieving their username.
	 * Prompts player for email. If no username with the given email exists in the database, return
	 * "Username not found."
	 * If the username with the given email is found in the database, return "Your username is <username>."
	 * 
	 * Sets isRetrievingUsername boolean to true when method begins. Sets it back to false on valid
	 * username retrieval.
	 * @param command
	 */
	private String retrieveUsername(String command) throws GameException {
		// TODO - implement AuthenticationCommands.retrieveUsername
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: help
	 * returns a String to the player giving them the list of commands available:
	 * Login
	 * Create Account
	 * Reset Password
	 * Retrieve Username
	 * Exit
	 * Help
	 */
	@Override()
	String help() {
		// TODO - implement AuthenticationCommands.help
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: loadGame
	 * Overrides the parent method to ensure a game cannot be loaded from the Authentication game state.
	 */
	@Override()
	String loadGame() throws GameException {
		// TODO - implement AuthenticationCommands.loadGame
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getIntroText
	 * Returns a String to the calling method containing:
	 * Game title
	 * list of available commands
	 */
	protected String getIntroText() {
		// TODO - implement AuthenticationCommands.getIntroText
		throw new UnsupportedOperationException();
	}

}