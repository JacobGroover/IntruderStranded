package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;

public class Exit {

	private Direction direction;
	private int roomID;
	private int destinationID;
	private String destinationName;

	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * 
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getRoomID() {
		return this.roomID;
	}

	/**
	 * 
	 * @param roomID
	 */
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public int getDestinationID() {
		return this.destinationID;
	}

	/**
	 * 
	 * @param destinationID
	 */
	public void setDestinationID(int destinationID) throws GameException {
		this.destinationID = destinationID;
		destinationName = Room.getNameFromId(destinationID);
	}

	public String display() {
		return "\"" + direction.getTitleCaseString() + "\" - " + destinationName;
	}
}