package IntruderStranded.model;

import IntruderStranded.controller.*;

import java.util.List;

public interface ItemRoomDB extends RoomDBInfoProvider {
	default List<Item> getItems() {
		return null;
	}

	/**
	 * method: addItem
	 * Adds an item to the room with the roomID matching the parameter passed in.
	 * @param item
	 */
	default void addItem(Item item) {

	}

	/**
	 * method: removeItem
	 * Removes an item from the room with the roomID matching the parameter passed in.
	 * @param item
	 */
	default void removeItem(Item item) {

	}
}