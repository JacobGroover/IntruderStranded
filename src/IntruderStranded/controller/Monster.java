package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;
import IntruderStranded.model.RoomDB;

import java.util.*;

public class Monster extends Entity implements RoomEvent {
	private String name;
	private int damage;
	private final RoomDB roomDB;
    private int freezeCounter = -1;

	public Monster(int id, int roomID, int playerID) {
		super(id);
		this.roomDB = new RoomDB(roomID, playerID);
	}

	public List<Item> getRewards() throws GameException {
		return roomDB.getRewards();
	}

    @Override
    public void delete() throws GameException {
        roomDB.removeMonster(this);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    boolean isFrozen() {
        return freezeCounter >= 0;
    }

    void tickFreeze() {
        freezeCounter--;
    }

    void freeze() {
        freezeCounter = 2;
        setHealth(getHealth() - 10);
    }
}