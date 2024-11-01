package IntruderStranded.controller;

public class Weapon extends Item {

	private int damage;

	/**
	 * @param itemID
	 */
	public Weapon(int itemID) {
		super(itemID);
	}

	int getDamage() {
		return this.damage;
	}

	/**
	 * 
	 * @param damage
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

}