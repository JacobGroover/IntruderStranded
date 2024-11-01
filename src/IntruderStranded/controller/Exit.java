package IntruderStranded.controller;

public class Exit {

	private Direction direction;
	private int roomID;
	private int destinationID;

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
	public void setDestinationID(int destinationID) {
		this.destinationID = destinationID;
	}

}