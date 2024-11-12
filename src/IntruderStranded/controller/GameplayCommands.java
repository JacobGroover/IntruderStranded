package IntruderStranded.controller;

import IntruderStranded.gameExceptions.*;
import IntruderStranded.model.SaveManager;

/**
 * Class: GameplayCommands
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: October 19th, 2024
 *
 * This class – Is the Commands subclass for Gameplay Commands. Handles all user commands sent
 * from GameController and returns appropriate replies or exceptions.
 * Relevant while the player is in an active game session.
 */
public class GameplayCommands extends Commands {

	private boolean isExiting;
	private boolean isManagingInventory;
	private boolean isTeleporting;
	private int teleportCounter;
	private String teleportLevel;
	private Puzzle currentPuzzle;
	private Monster currentMonster;

	/**
	 * Method: GameplayCommands
	 * One-Argument Constructor for the GameplayCommands class
	 * Calls parent one-argument constructor, then initializes booleans to false.
	 * @param player
	 */
	public GameplayCommands(Player player) {
		super(player);
	}

	/**
	 * Method: executeCommand
	 * Validates and then executes a command received from the GameController.
	 * Valid Commands (case-insensitive):
	 * Hint [calls the hint method]
	 * Look [calls the look method]
	 * Exit [calls the exit method]
	 * Help [calls the help method]
	 * Save Commands for saving game - Save Game, Save [calls the saveGame method]
	 * Load Commands for loading game - Load Game, Load [calls the loadGame method]
	 * Inventory commands - INV, Store, Use &lt;item&gt;, Discard &lt;item&gt;, Close [calls the inventory method]
	 * Movement Commands - TEL, North, South, East, West, Flee [calls the move method]
	 * Battle commands - Attack, Defend
	 *
	 * Throws an exception for an invalid command
	 * @param command
	 */
	@Override
	String executeCommand(String command) throws GameException {
		if (isExiting) {
			return exit(command);
		} else if (isManagingInventory) {
			return inventory(command);
		} else if (isTeleporting) {
			return teleport(command);
		} else if (currentPuzzle != null) {
			return currentPuzzle.run();
		} else if (Direction.parseDirection(command) != null) {
			return move(command);
		} else return switch (command) {
			case "HELP" -> help();
			case "HINT" -> hint();
			case "LOOK" -> look();
			case "SAVE" -> saveGame();
			case "INV" -> inventory(command);
			case "EXIT" -> exit(command);
			case "ATTACK" -> attack();
			case "DEFEND" -> defend();
			case "FLEE" -> flee();
			case "USE" -> useItem();
			case "TEL" -> teleport(command);
			default -> throw new GameException("Invalid command");
		};
	}

	/**
	 * Method: hint
	 * Provides a hint for the player.
	 * If the player is currently in a puzzle, returns that puzzle's hint. Otherwise returns the hint for the
	 * room the player is currently in.
	 */
	private String hint() throws GameException {
		// TODO - implement GameplayCommands.hint
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: look
	 * returns the description for the room the player is currently in.
	 */
	private String look() throws GameException {
		// TODO - implement GameplayCommands.look
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: saveGame
	 * Saves the player's game to the database by calling the SaveManager.saveGame method.
	 */
	private String saveGame() throws GameException {
		SaveManager.saveGame();
		return "Game Saved";
	}

	/**
	 * Method: Inventory
	 * Opens the player's inventory and allows them to interact with their inventory.
	 * If command String parameter equals "inv" then sets isManagingInventory boolean to true and
	 * return a String listing the contents of the inventory by calling player.displayInventory method.
	 *
	 * If isManagingInventory boolean is already true:
	 *
	 * If the command parameter equals "Use <item>" where <item> is an item in the player's inventory,
	 * then uses the item by calling player.useItem method. Otherwise returns a String indicating
	 * the item is not in the player's inventory.
	 *
	 * If the command parameter equals "Discard <item>" where <item> is an item in the player's
	 * inventory, then removes the item from inventory and adds it to the room by calling
	 * player.removeItem method and player.getCurrentRoom.addItem method. Otherwise, returns
	 * a String indicating the item is not in the player's inventory (this is checked using the
	 * player.getInventory method).
	 *
	 * If the command parameter equals "Help" then return a String listing the available commands (Use <item>, Discard <item>, Close, Help).
	 * If the command parameter equals "Close" then close the inventory menu by setting the isManagingInventory
	 * boolean to false.
	 * If the command parameter equals any other String, then throw a GameException indicating
	 * an unrecognized command.
	 * @param command
	 */
	private String inventory(String command) throws GameException {
		// TODO - implement GameplayCommands.inventory
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: move
	 * Validates movement commands and facilitates moving the player between rooms in the game.
	 * When a valid move is received, retrieves the new room details and returns them in a String.
	 * When an invalid move is received, throws a GameException indicating an invalid move.
	 * Calls player.getCurrentRoom.leaveRoom method to do this.
	 * @param command
	 */
	private String move(String command) throws GameException {
		int destinationId = player.getCurrentRoom().leaveRoom(command);
		player.setCurrentRoom(Room.getById(destinationId, player.getID()));
		return player.getCurrentRoom().display();
	}

	/**
	 * Method: help
	 * returns a String to the player giving them the list of commands available:
	 * Hint
	 * Look
	 * Exit
	 * Help
	 * Save Game
	 * Load Game
	 * INV (if in inventory, then only Store, Use &lt;item&gt;, Discard &lt;item&gt;, Close, and Exit commands are available)
	 * TEL (not available during room event, i.e. monster encounter or puzzle)
	 * North (not available during room event, i.e. monster encounter or puzzle)
	 * South (not available during room event, i.e. monster encounter or puzzle)
	 * East (not available during room event, i.e. monster encounter or puzzle)
	 * West (not available during room event, i.e. monster encounter or puzzle)
	 * Flee (only available during room event, i.e. monster encounter or puzzle)
	 */
	@Override
	String help() {
		if (isManagingInventory) {
			return """
            Store
            Use <item>
            Discard <item>
            Close
            Exit""";
		}
		else if (currentMonster != null || currentPuzzle != null) {
			return """
            Hint
            Look
            Exit
            Help
            Save Game
            Load Game
            INV
            Flee""";
		}

		return """
            Hint
            Look
            Exit
            Help
            Save Game
            Load Game
            INV
            TEL
            North
            South
            East
            West""";
	}

	/**
	 * Method: exit
	 * If isExiting boolean is false, sets isExiting boolean to true, then prompts the player by returning
	 * "Do you want to save your game?"
	 *
	 * If the isExiting boolean is already true, then reads the String command parameter.
	 * If the command parameter equals "yes" or "y" then sets the isExiting boolean to false, saves the game
	 * in the database and calls the changeGameState method, changing the game state to MainMenuCommands.
	 *
	 * If the command parameter equals "no" or "n" then sets the isExiting boolean to false, does NOT
	 * save the game, and calls the changeGameState method, changing the game state to MainMenuCommands.
	 * If the commands parameter is any other String, sets the isExiting boolean to false and throws a
	 * GameException for unrecognized command.
	 * @param command
	 */
	@Override
	String exit(String command) throws GameException {
		if (!isExiting) {
			isExiting = true;
			return "Do you want to save your game?";
		}

		isExiting = false;

		if (command.equals("YES") || command.equals("Y")) {
			SaveManager.saveGame();
			changeGameState(new MainMenuCommands(player));
			return "";
		}

		if (command.equals("NO") || command.equals("N")) {
			changeGameState(new MainMenuCommands(player));
			return "";
		}

		throw new GameException("Invalid command");
	}

	private String attack() {
		// TODO - implement GameplayCommands.attack
		throw new UnsupportedOperationException();
	}

	private String defend() {
		// TODO - implement GameplayCommands.defend
		throw new UnsupportedOperationException();
	}

	private String flee() {
		// TODO - implement GameplayCommands.flee
		throw new UnsupportedOperationException();
	}

	private String useItem() {
		// TODO - implement GameplayCommands.useItem
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: getIntroText
	 * Returns a String to the calling method containing:
	 * Welcome message and Game title
	 * list of available commands
	 *
	 * Room details for first room
	 */
	protected String getIntroText() {
		// TODO - implement GameplayCommands.getIntroText
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: teleport
	 * If teleportCounter = -1:
	 * Calls player.getCurrentRoom.isTeleport() method. If that method returns true, increment
	 * teleportCounter to 0. Otherwise, throws a GameException to calling method for unrecognized command.
	 *
	 * If teleportCounter = 0:
	 * Increments teleportCounter.
	 * Returns "Where would you like to teleport? Level -2(Cell), Level -1(Armory), Level 0(Inside),
	 * Level 0(Outside). Please enter a number."
	 *
	 * If teleportCounter = 1:
	 * If input parameter equals "0" or "-2" or "-1" Set teleportLevel class attribute to input parameter.
	 * If input parameter equals "0", increments teleportCounter and returns "Please enter
	 * "inside" or "outside" "
	 * If input parameter equals "-2" or "-1", increment teleportCounter (increments without returning anything).
	 * If anything other than -2, -1, or 0 is entered, throw a GameException with "This level does not exist."
	 *
	 * If teleportCounter = 2:
	 * If teleportLevel = "0" and input parameter is not "inside" or "outside", then return "Please enter "inside" or "outside" "
	 * If teleportLevel = "0" and input parameter equals "inside" or "outside", or teleportLevel
	 * = "-2" or "-1" then increment teleportCounter and return "Are you sure you want to teleport?"
	 *
	 * If teleportCounter = 3:
	 * If input parameter = "yes", call player.getCurrentRoom.leaveRoom() method and then
	 * assign previous room to current room, then assign the return of that method to current room.
	 *
	 * If input parameter = "no", then set teleportCounter to -1 and teleportLevel to null
	 *
	 * Otherwise return "Please enter yes or no."
	 * @param command
	 */
	String teleport(String command) {
		// TODO - implement GameplayCommands.teleport
		throw new UnsupportedOperationException();
	}

}