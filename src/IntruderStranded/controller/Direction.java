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

	public boolean isTeleport() {
		return !physicalDirectionMap.containsValue(this);
	}

	public String getTitleCaseString() {
		return name().charAt(0) + name().toLowerCase().substring(1);
	}
}