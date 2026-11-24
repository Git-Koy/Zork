package ch.bbw.zork;

/**
 * An item that is either carried by the player or lies in a country.
 * Every item has a name, a description and a weight in kg.
 * Items can give bonuses or penalties to a troop category.
 *
 * @author Git-Koy
 * @author Yann Eggenberger
 */
public class Item {
	private static final int BONUS_PERCENT = 10;
	private static final int PENALTY_PERCENT = 5;

	private final String name;
	private final String description;
	private final double weight;
	private final TroopCategory bonusCategory;
	private final TroopCategory penaltyCategory;

	public Item(String name, String description, double weight) {
		this(name, description, weight, null, null);
	}

	public Item(String name, String description, double weight, TroopCategory bonusCategory,
			TroopCategory penaltyCategory) {
		this.name = name;
		this.description = description;
		this.weight = weight;
		this.bonusCategory = bonusCategory;
		this.penaltyCategory = penaltyCategory;
	}

	/**
	 * Returns the name of an item in a readable format.
	 * @return the name of the item
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the description of the item in a readable format.
	 * @return the description of the item
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the weight of the item.
	 * @return the weight of the item in kg
	 */
	public double getWeight() {
		return weight;
	}

	public int getPowerModifier(TroopCategory category) {
		if (category == bonusCategory) {
			return BONUS_PERCENT;
		}
		if (category == penaltyCategory) {
			return -PENALTY_PERCENT;
		}
		return 0;
	}
}
