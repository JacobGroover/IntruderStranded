package IntruderStranded.controller;

/**
 * Class: Entity
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 14, 2024
 *
 * This class is the base class of all entities, classes that have health and damage.
 */
public abstract class Entity {
	private final int id;
	private int health;

	/**
	 * Creates a new Entity with the given id.
	 * @param id The id of the entity.
	 */
	public Entity(int id) {
		this.id = id;
	}

	/**
	 * Creates a new Entity instance from an existing one, copying all of its fields.
	 * @param entity The entity to copy.
	 */
	public Entity(Entity entity) {
		this.id = entity.id;
		this.health = entity.health;
	}

	/**
	 * Method: getHealth
	 * Gets the health of this entity.
	 * @return The health value.
	 */
	public int getHealth() {
		return this.health;
	}

	/**
	 * Method: setHealth
	 * Sets the health of this entity.
	 * @param health The health to set.
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Method: getDamage
	 * Gets the damage of this entity.
	 * @return The damage of this entity.
	 */
	abstract public int getDamage();

	/**
	 * Method: getID
	 * Gets the id of this entity.
	 * @return The id.
	 */
    public int getID() {
        return id;
    }

	/**
	 * Method: getStatus
	 * Gets the status text of this entity.
	 * @return The string to display.
	 */
	String getStatus() {
		return "Health = " + getHealth() + ", Damage = " + getDamage();
	}
}