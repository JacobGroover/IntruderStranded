package IntruderStranded.controller;

import java.util.*;

public class Monster extends Entity implements RoomEvent {

	private String name;
	private List<Item> rewards;

	public List<Item> getRewards() {
		return this.rewards;
	}

}