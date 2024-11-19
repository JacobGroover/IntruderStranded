package IntruderStranded.controller;

import IntruderStranded.gameExceptions.*;

import java.util.Optional;

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
	 * Calls parent no-argument constructor
	 */
	AuthenticationCommands() {
		super();
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
            return switch (command) {
                case "LOGIN" -> login(command);
                case "CREATE ACCOUNT" -> createAccount(command);
                case "FORGOT PASSWORD" -> resetPassword(command);
                case "RETRIEVE USERNAME" -> retrieveUsername(command);
                case "HELP" -> help();
                case "EXIT" -> exit(command);
                default -> throw new GameException("Unrecognized command!");
            };
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
		if (!isLoggingIn) {
			isLoggingIn = true;
			return "\nUsername: \\b";
		} else if (username == null) {
			username = command;
			return "Password: \\b";
		} else {
			password = command;

			Optional<Integer> pID = Player.checkLogin(username, password);
			if (pID.isPresent()) {
				player = Player.getById(pID.get());
				changeGameState(new MainMenuCommands(player));		// No need to reset boolean and counter, since game state changes
				return "Login Successful";
			} else {
				StringBuilder text = new StringBuilder("Login Failed. Please Try Again.");
				username = null;
				password = null;
				loginCounter++;
				if (loginCounter == 3) {
					text.append("""
                            
                            If you have forgotten your user account please enter "Retrieve Username" to retrieve
                            username, or "Forgot Password" to reset password.
                            """);
					loginCounter = 0;
				}
				isLoggingIn = false;
				return text.toString();
			}
		}
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
	 * On valid email entry, sets isCreatingAccount boolean to false and
	 * sets username, password, and email back to null
	 * 
	 * On valid email entry, create the account by calling
	 * Player.createAccount(username, password, email) and return
	 * "Successfully created account. Please login to continue."
	 * If an account with the same username or email already exists, return
	 * "Account already exists, please try logging in."
	 *
	 * @param command
	 */
	private String createAccount(String command) throws GameException {
		String text = "";
		if (!isCreatingAccount) {
			isCreatingAccount = true;
			text += "\nUsername: \\b";
		} else if (username == null) {
			if (command.length() < 4 || command.length() > 10) {
				text += "Username must be between 4 and 10 characters long.\n\nUsername: \\b";
			} else {
				username = command;
				text += "Password: \\b";
			}
		} else if (password == null) {
			if (command.length() < 8 || command.length() > 12) {
				text += "Password must be between 8 and 12 characters long.\n\nPassword: \\b";
			} else {
				password = command;
				text += "Email: \\b";
			}
		} else if (email == null) {
			if (command.length() > 20 || !command.contains("@") || !command.contains(".")) {
				text += "Email must be 20 characters or less and contain a '.' and a '@'\n\nEmail: \\b";
			} else {
				email = command;
				if (Player.createAccount(username, password, email)) {
					text += "Successfully created account. Please login to continue.\n";
				} else {
					text += "Account already exists, please try logging in\n";
				}
				username = null;
				password = null;
				email = null;
				isCreatingAccount = false;
			}
		}
		return text;
	}

	/**
	 * Method: resetPassword
	 * Facilitates the player resetting their password.
	 * Prompts player for their username. If no account exists with the given username, return
	 * "Username does not exist." If username is found, return "Username found." and prompt for password.
	 * If password is not between 8 and 12 characters, return "Password must be between 8 and 12 characters long."
	 * If valid password entry, set the new password to the account and return "Successfully reset password."
	 * 
	 * Sets isResettingPassword boolean to true when method begins. Sets it back to false after a
	 * password reset attempt.
	 * @param command
	 */
	private String resetPassword(String command) throws GameException {
		String text = "";
		try {
			if (!isResettingPassword) {
				isResettingPassword = true;
				text += "\nPlease Enter Username: \\b";
			} else if (username == null) {
				if (Player.checkUsernameField(command)) {
					username = command;
					text += "Username Found.\nPlease enter new password: \\b";
				} else {
					isResettingPassword = false;
					text += "Username does not exist.";
				}
			} else if (password == null) {
				if (command.length() < 8 || command.length() > 12) {
					text += "Password must be between 8 and 12 characters long.\n\nPassword: \\b";
				} else {
					password = command;
					Player.updatePassword(username, password);
					text += "Successfully Reset Password\n";
					isResettingPassword = false;
					username = null;
					password = null;
				}
			}
		} catch (GameException ge) {
			throw new GameException("Failed to reset password\n");
		}
		return text;
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
		String text = "";
		if (!isRetrievingUsername) {
			isRetrievingUsername = true;
			text += "\nPlease Enter Email: \\b";
		} else if (email == null) {
			if (Player.checkEmailField(command)) {
				// retrieve username associated with email from database
				text += "Your username is \\b";
				text += Player.retrieveUsername(command);
			} else {
				text += "Cannot find Username.";
			}
			isRetrievingUsername = false;
		}
		return text;
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
		return """
				Account Management Commands
				
				Login - Enter your username and password
				Create Account - Sign up with username, password, and email
				Forgot Password - Reset user's password
				Retrieve Username - Get user's username with email
				Exit - Exits the application
				Help - This command, displays available commands
				""";
	}

	/**
	 * Method: getIntroText
	 * Returns a String to the calling method containing:
	 * Game title
	 * list of available commands
	 */
	protected String getIntroText() {
		return """
                Intruder Stranded

                Please enter the command "Login" or "Create Account"
                Forgot Password "Forgot Password"
                Forgot Username "Retrieve Username"
                If you need help. Please enter "HELP" to find more commands.
                """;
	}

}