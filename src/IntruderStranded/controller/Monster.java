package IntruderStranded.controller;

import java.util.*;

public class Monster extends Entity implements RoomEvent {

	private String name;
	private Collection<Item> rewards;

	public Collection<Item> getRewards() {
		return this.rewards;
	}

}