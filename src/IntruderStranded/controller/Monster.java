package IntruderStranded.controller;

import IntruderStranded.model.RoomDB;

import java.util.*;

public class Monster extends Entity implements RoomEvent {
	private String name;
	private int damage;
	private final RoomDB roomDB;

	public Monster(int id, int roomID, int playerID) {
		super(id);
		this.roomDB = new RoomDB(roomID, playerID);
	}

	public List<Item> getRewards() {
		throw new UnsupportedOperationException();
	}

    public int getDamage() {
        throw new UnsupportedOperationException();
    }

    public void setDamage(int damage) {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public void setName(String name) {
        throw new UnsupportedOperationException();
    }
}