package IntruderStranded.controller;

import IntruderStranded.gameExceptions.GameException;
import IntruderStranded.model.RoomDB;

import java.util.*;

/**
 * Class: Monster
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 15, 2024
 *
 * This class handles business logic for monsters.
 */
public class Monster extends Entity implements RoomEvent {
	private String name;
	private int damage;
	private final RoomDB roomDB;
    private int freezeCounter = -1;

    /**
     * Creates a new Monster with the given id, room id, and player id.
     * @param id The id of the monster.
     * @param roomID The room id of the monster.
     * @param playerID The id of the current player.
     */
	public Monster(int id, int roomID, int playerID) {
		super(id);
		this.roomDB = new RoomDB(roomID, playerID);
	}

    /**
     * Method: getRewards
     * Returns a List of rewards for defeating the Monster from RoomDB
     * @return The list of rewards.
     */
	public List<Item> getRewards() throws GameException {
		return roomDB.getRewards();
	}

    /**
     * Method: delete
     * Deletes this monster from the database.
     */
    @Override
    public void delete() throws GameException {
        roomDB.removeMonster(this);
    }

    /**
     * Method: getDamage
     * Gets the damage of this monster.
     * @return The damage value.
     */
    @Override
    public int getDamage() {
        return damage;
    }

    /**
     * Method: setDamage
     * Sets the damage of this monster.
     * @param damage The damage to set.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Method: getName
     * Gets the name of this monster.
     * @return The name of this monster.
     */
    public String getName() {
        return name;
    }

    /**
     * Method: setName
     * Sets the name of this monster.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method: isFrozen
     * Checks if this monster is currently frozen.
     * @return True if this monster is frozen, otherwise false.
     */
    boolean isFrozen() {
        return freezeCounter >= 0;
    }

    /**
     * Method: tickFreeze
     * Counts a turn this monster has been frozen for.
     */
    void tickFreeze() {
        freezeCounter--;
    }

    /**
     * Method: freeze
     * Freezes this monster.
     */
    void freeze() {
        freezeCounter = 2;
        setHealth(getHealth() - 10);
    }
}