package IntruderStranded.controller;

public abstract class Entity {

	private final int id;
	private int health;

	public int getHealth() {
		return this.health;
	}

	public Entity(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @param health
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	abstract public int getDamage();

    public int getID() {
        return id;
    }

	String getStatus() {
		return "Health = " + getHealth() + ", Damage = " + getDamage();
	}
}