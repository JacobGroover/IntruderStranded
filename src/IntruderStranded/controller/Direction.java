package IntruderStranded.controller;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
	NORTH,
	SOUTH,
	EAST,
	WEST,
	TEL0IN,
	TEL0OUT,
	TEL1,
	TEL2;

	private static final Map<String, Direction> directionMap = new HashMap<>();

	static {
		directionMap.put("NORTH", NORTH);
		directionMap.put("SOUTH", SOUTH);
		directionMap.put("EAST", EAST);
		directionMap.put("WEST", WEST);
	}

	/**
	 * Method: parseDirection
	 * Converts a string value into a direction if the string is a valid direction.
	 * @param direction The string to convert into a direction.
	 * @return The direction value which represents the provided string, or null if the string
	 * does not represent a direction.
	 */
	public static Direction parseDirection(String direction) {
		return directionMap.get(direction);
	}
}