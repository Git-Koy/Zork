package ch.bbw.zork;

import java.util.ArrayList;

/**
 * This class represents the player and the items they have in their inventory.
 * The player can carry several items if they are not too heavy in total.
 *
 * @author Git-Koy
 */
public class Player {
    private ArrayList<Item> items = new ArrayList<Item>();
    private double maxWeight = 101;
    private double currentWeight = 0;

    /**
     * Adds an item to the inventory if the total weight stays within the limit.
     *
     * @param item the item to pick up
     * @return true if the item was added, false if it is too heavy
     */
    public boolean addItem(Item item) {
        if (item.getWeight() + currentWeight > maxWeight) {
            return false;
        } else {
            items.add(item);
            currentWeight += item.getWeight();

            return true;
        }
    }

    /**
     * Removes an item from the player inventory and removes it from the total weight.
     * @param item the item to remove
     * @return true if the item was removed, false if it was not removed
     */
    public boolean removeItem(Item item) {
        if (items.contains(item)) {
            items.remove(item);
            currentWeight -= item.getWeight();
            return true;
        } else
            return false;
    }

    /**
     * Builds a readable sentence listing the current items in the inventory and the total weight.
     * @return the inventory as text
     */
    public String inventoryString() {
        if (items.isEmpty()) {
            return "Your inventory is empty (0 kg).";
        } else if (items.size() == 1) {
            return "Your inventory has "+ items.get(0).getDescription() + " (" + currentWeight + " kg).";
        } else {
            String itemStorage = "Your inventory has ";

            for(int i = 0; i < items.size(); i++) {
                itemStorage += items.get(i).getDescription();

                if (i == items.size() - 1) {
                    itemStorage += "";
                } else if (i == items.size() - 2) {
                    itemStorage += " and ";
                } else {
                    itemStorage += ", ";
                }
            }

            return itemStorage + " (" + currentWeight + " kg).";
        }
    }

    /**
     * Prints the inventory into the console.
     */
    public void showInventory() {
        System.out.println(inventoryString());
    }

    /**
     * Returns a list of the items in the inventory.
     * @return the items in the inventory
     */
    public ArrayList<Item> getItems() {
        return items;
    }
}
