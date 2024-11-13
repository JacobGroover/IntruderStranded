package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;

import java.util.*;

public interface RoomEvent {

	/**
	 * Method: getRewards
	 * abstract method for getting the rewards associated with an implementing class.
	 */
	List<Item> getRewards() throws GameException;

}