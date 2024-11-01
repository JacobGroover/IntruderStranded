package IntruderStranded.controller;

public abstract class Entity {

	private int id;
	private int health;

	public int getHealth() {
		return this.health;
	}

	/**
	 * 
	 * @param health
	 */
	public void setHealth(int health) {
		this.health = health;
	}

}