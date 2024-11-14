package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;

import java.util.*;

/**
 * Interface: RoomEvent
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 14, 2024
 *
 * This interface is implemented by monsters and puzzles, and represents an event in a room.
 */
public interface RoomEvent {

	/**
	 * Method: getRewards
	 * abstract method for getting the rewards associated with an implementing class.
	 */
	List<Item> getRewards() throws GameException;

	/**
	 * Method: delete
	 * Deletes a RoomEvent from the database.
	 */
	void delete() throws GameException;
}