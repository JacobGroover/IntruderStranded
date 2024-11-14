package IntruderStranded.controller;

public class Item {

	private final int itemID;
	private String itemName;
	private String itemDescription;
	private boolean discardAllowed;
	private ConsumableType consumableType;

	/**
	 * Returns the name and description of the item as a string.
	 */
	String display() {
		return itemName + " - " + itemDescription;
	}

	/**
	 * 
	 * @param itemID
	 */
	public Item(int itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return this.itemName;
	}

	/**
	 * 
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {

		return this.itemDescription;
	}

	/**
	 * 
	 * @param itemDescription
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

    public int getItemID() {
        return itemID;
    }

    public boolean isDiscardAllowed() {
        return discardAllowed;
    }

    public void setDiscardAllowed(boolean discardAllowed) {
        this.discardAllowed = discardAllowed;
    }

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item other) {
			return itemID == other.itemID;
		}

		return false;
	}

    public ConsumableType getConsumableType() {
        return consumableType;
    }

    public void setConsumableType(ConsumableType consumableType) {
        this.consumableType = consumableType;
    }
}