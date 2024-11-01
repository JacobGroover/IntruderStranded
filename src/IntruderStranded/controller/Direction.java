package IntruderStranded.controller;

public enum Direction {
	NORTH("NORTH"),
	SOUTH("SOUTH"),
	EAST("EAST"),
	WEST("WEST"),
	TEL0IN("INSIDE"),
	TEL0OUT("OUTSIDE"),
	TEL1("-1"),
	TEL2("-2");

	private String description;

	/**
	 * 
	 * @param description
	 */
	private Direction(String description) {
		// TODO - implement Direction.Direction
		throw new UnsupportedOperationException();
	}

	String getDescription() {
		return this.description;
	}

}