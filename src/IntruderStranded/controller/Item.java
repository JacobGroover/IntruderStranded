package IntruderStranded.controller;

/**
 * Class: Item
 * @author Fareed Ahmed
 * @version 1.0
 * Course: ITEC 3860 Fall 2024
 * Written: November 14, 2024
 *
 * This class handles business logic for items.
 */
public class Item {

	private final int itemID;
	private String itemName;
	private String itemDescription;
	private boolean canDiscard;
	private ConsumableType consumableType;

	/**
	 * Returns the name and description of the item as a string.
	 * @return The string to display.
	 */
	String display() {
		return itemName + " - " + itemDescription;
	}

	/**
	 * Creates a new Item object with the given item id.
	 * @param itemID The item id.
	 */
	public Item(int itemID) {
		this.itemID = itemID;
	}

	/**
	 * Method: getItemName
	 * Gets the name of this item.
	 * @return The name of this item.
	 */
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * Method: setItemName
	 * Sets the name of this item.
	 * @param itemName The name of this item.
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * Method: getItemDescription
	 * Gets the description of this item.
	 * @return The description of this item.
	 */
	public String getItemDescription() {
		return this.itemDescription;
	}

	/**
	 * Method: setItemDescription
	 * Sets the description of this item.
	 * @param itemDescription The description of this item.
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	/**
	 * Method: getItemID
	 * Gets the id of this item.
	 * @return The id of this item.
	 */
    public int getItemID() {
        return itemID;
    }

	/**
	 * Method: canDiscard
	 * Checks if this item can be discarded.
	 * @return True if this item can be discarded, otherwise false.
	 */
    public boolean canDiscard() {
        return canDiscard;
    }

	/**
	 * Method: setCanDiscard
	 * Sets this item's discard flag.
	 * @param canDiscard The discard flag to set.
	 */
    public void setCanDiscard(boolean canDiscard) {
        this.canDiscard = canDiscard;
    }

	/**
	 * Method: equals
	 * Checks if the given object is an item with the same item id as this item.
	 * @param obj The object to check.
	 * @return True if the object is an item with an equal item id, otherwise false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item other) {
			return itemID == other.itemID;
		}

		return false;
	}

	/**
	 * Method: getConsumableType
	 * Gets the consumable type of this item.
	 * @return The consumable type of this item.
	 */
    public ConsumableType getConsumableType() {
        return consumableType;
    }

	/**
	 * Method: setConsumableType
	 * Sets the consumable type of this item.
	 * @param consumableType The consumable type to set.
	 */
    public void setConsumableType(ConsumableType consumableType) {
        this.consumableType = consumableType;
    }
}