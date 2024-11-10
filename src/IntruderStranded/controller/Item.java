package IntruderStranded.controller;

import IntruderStranded.model.ItemDB;

public class Item {

	private int itemID;
	private String itemName;
	private String itemDescription;

	/**
	 * Returns the name and description of the item as a string.
	 */
	String display() {
		return itemName + "- " + itemDescription;
	}

	/**
	 * 
	 * @param itemID
	 */
	public Item(int itemID) {
		// TODO: Fix implementation
		ItemDB idb = new ItemDB();
		idb.getItem(itemID);
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
}