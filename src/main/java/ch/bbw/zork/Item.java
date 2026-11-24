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

	/**
	 * Creates an item without combat modifiers.
	 *
	 * @param name item id used by inventory commands
	 * @param description readable item description
	 * @param weight item weight in kilograms
	 */
	public Item(String name, String description, double weight) {
		this(name, description, weight, null, null);
	}

	/**
	 * Creates an item with an optional troop-category bonus and penalty.
	 *
	 * @param name item id used by inventory commands
	 * @param description readable item description
	 * @param weight item weight in kilograms
	 * @param bonusCategory category receiving a ten percent power bonus
	 * @param penaltyCategory category receiving a five percent power penalty
	 */
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

	/**
	 * Returns this item's power modifier for a troop category.
	 *
	 * @param category troop category to check
	 * @return ten for the bonus category, negative five for the penalty
	 *         category, or zero otherwise
	 */
	public int getPowerModifier(TroopCategory category) {
		if (bonusCategory != null && category == bonusCategory) {
			return BONUS_PERCENT;
		}
		if (penaltyCategory != null && category == penaltyCategory) {
			return -PENALTY_PERCENT;
		}
		return 0;
	}
}
