package IntruderStranded.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum: Direction
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 14, 2024
 *
 * This enum contains the valid directions a player can move in.
 */
public enum Direction {
	NORTH,
	SOUTH,
	EAST,
	WEST,
	TEL0IN,
	TEL0OUT,
	TEL1,
	TEL2;

	private static final Map<String, Direction> physicalDirectionMap = new HashMap<>();

	static {
		physicalDirectionMap.put("NORTH", NORTH);
		physicalDirectionMap.put("SOUTH", SOUTH);
		physicalDirectionMap.put("EAST", EAST);
		physicalDirectionMap.put("WEST", WEST);
	}

	/**
	 * Method: parseDirection
	 * Converts a string value into a direction if the string is a valid direction.
	 * @param direction The string to convert into a direction.
	 * @return The direction value which represents the provided string, or null if the string
	 * does not represent a direction.
	 */
	public static Direction parseDirection(String direction) {
		return physicalDirectionMap.get(direction);
	}

	/**
	 * Method: isTeleport
	 * Checks if this direction is a teleport direction.
	 * @return True if this direction is a teleport direction, otherwise false.
	 */
	public boolean isTeleport() {
		return !physicalDirectionMap.containsValue(this);
	}
}