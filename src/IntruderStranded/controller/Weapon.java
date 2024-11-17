package IntruderStranded.controller;

/**
 * Class: Weapon
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 14, 2024
 *
 * This class handles business logic for weapons. It extends Item.
 */
public class Weapon extends Item {

	private int damage;

	/**
	 * Creates a new Weapon object with the given item id.
	 * @param itemID The item id.
	 */
	public Weapon(int itemID) {
		super(itemID);
	}

	/**
	 * Method: getDamage
	 * Gets the damage of this weapon.
	 * @return The damage value.
	 */
	int getDamage() {
		return this.damage;
	}

	/**
	 * Method: setDamage
	 * Sets the damage of this weapon.
	 * @param damage The damage to set.
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

}