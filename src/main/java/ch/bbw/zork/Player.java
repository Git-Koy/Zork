package ch.bbw.zork;

import java.util.ArrayList;

public class Player {
    private ArrayList<Item> items = new ArrayList<Item>();
    private double maxWeight = 101;
    private double currentWeight = 0;

    public boolean addItem(Item item) {
        if (item.getWeight() + currentWeight > maxWeight) {
            return false;
        } else {
            items.add(item);
            currentWeight += item.getWeight();

            return true;
        }
    }

    public boolean removeItem(Item item) {
        if (items.contains(item)) {
            items.remove(item);
            currentWeight -= item.getWeight();
            return true;
        } else
            return false;
    }

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

    public void showInventory() {
        System.out.println(inventoryString());
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
