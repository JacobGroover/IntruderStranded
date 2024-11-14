package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;

/**
 * Class: Exit
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 14, 2024
 *
 * This class handles business logic for exits.
 */
public class Exit {

	private Direction direction;
	private int roomID;
	private int destinationID;
	private String destinationName;

	/**
	 * Method: getDirection
	 * Gets the direction of the exit.
	 * @return The direction of the exit.
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Method: setDirection
	 * Sets the direction of the exit.
	 * @param direction The direction to set.
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Method: getRoomID
	 * Gets the id of the room this exit is located in.
	 * @return The room id of this exit.
	 */
	public int getRoomID() {
		return this.roomID;
	}

	/**
	 * Method: setRoomID
	 * Sets the id of the room this exit is located in.
	 * @param roomID The room id to set.
	 */
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	/**
	 * Method: getDestinationID
	 * Gets the id of the room this exit goes to.
	 * @return The destination room id of this exit.
	 */
	public int getDestinationID() {
		return this.destinationID;
	}

	/**
	 * Method: setDestinationID
	 * Sets the id of the room this exit goes to.
	 * @param destinationID The destination room id to set.
	 */
	public void setDestinationID(int destinationID) throws GameException {
		this.destinationID = destinationID;
		destinationName = Room.getNameFromId(destinationID);
	}

	/**
	 * Method: display
	 * Displays this exit.
	 * @return The display string of this exit.
	 */
	public String display() {
		String directionName = direction.name();
		return "\"" + directionName.charAt(0) + directionName.toLowerCase().substring(1)
				+ "\" - " + destinationName;
	}
}