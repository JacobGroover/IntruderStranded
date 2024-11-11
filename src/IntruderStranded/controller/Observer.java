package IntruderStranded.controller;

/**
 * Class: GameController
 * @author Jacob Groover
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 6th, 2024
 *
 * This interface – Utilizes the observer design pattern by providing an observer interface for updating
 * subscribers based on the state of observed objects. Specifically used to update GameController with the
 * current game state of the active Commands class.
 */
public interface Observer<T> {

	/**
	 * Method: onUpdate
	 * Updates subscribers to this Observer interface. Implementation varies between subscribers.
	 * @param arg
	 */
	void onUpdate(T arg);

}