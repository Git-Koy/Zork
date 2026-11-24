package ch.bbw.zork;

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

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

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
