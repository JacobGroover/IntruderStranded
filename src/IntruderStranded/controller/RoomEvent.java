package IntruderStranded.controller;

import java.util.*;

public interface RoomEvent {

	/**
	 * Method: getRewards
	 * abstract method for getting the rewards associated with an implementing class.
	 */
	List<Item> getRewards();

}