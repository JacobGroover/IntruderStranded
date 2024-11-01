package IntruderStranded.controller;

public class Weapon extends Item {

	private int damage;

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