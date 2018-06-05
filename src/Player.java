/**
 * JavaQuest user-playable Character
 */
public class Player extends Character {

	// ATTRIBUTES
	private long experiencePoints = 0;
	private int attributePoints = 0;
	private int skillPoints = 0;

	// CONSTRUCTORS
	public Player() {
		super(
			"Hero", 1,
			"Adventurer", new AdventurerCalculator(),
			5, 5, 5 //base stats
		);

		rest();
	}
	

	// METHODS
	public long currentExp() {
		return experiencePoints;
	}

	public long nextLevelExp() {
		return super.exp();
	}

	public void gainExperience(int exp) {
		experiencePoints += exp;

		//keeps leveling up if theres anough EXP
		while ( experiencePoints >= nextLevelExp() ) {
			experiencePoints -= nextLevelExp();
			levelUp();
		}
	}

	public int attributePoints() {
		return attributePoints;
	}

	/**
	 * Exchanges one attributePoint for a permanent increment on one chosen stat
	 * @param stat Must be either "STR", "DEX" or "INT"
	 */
	public void spendAP(final String stat) {
		if (stat == null) {
			throw new NullPointerException();
		}

		if (attributePoints <= 0) {
			return;
		}

		switch (stat.toUpperCase()) {
			case "STR":
				setStrength(defaultStrength() + 1);
				break;
			
			case "DEX":
				setDexterity(defaultDexterity() + 1);
				break;
			
			case "INT":
				setIntelligence(defaultIntelligence() + 1);
				break;
			
			default:
				return;
		}

		--attributePoints;
	}

	public int skillPoints() {
		return skillPoints;
	}

	/*public void spendSP(int skillCode) {
		//TODO ^
	}*/

	@Override
	public void setName(final String name) {
		//TODO valid name verification (throw something)
		super.setName(name);
	}

	@Override
	public void setRole(final String role) {
		//TODO setStatCalculator based on role, also recalculate stats
		super.setRole(role);
	}

	@Override
	public void levelUp() {
		attributePoints += 5; //TODO statCalculate
		skillPoints += 3; //TODO statCalculate
		super.levelUp();
		rest();
	}

	@Override
	public String toString() {
		return String.format(java.util.Locale.US,
			"%s\n" +
			"AP: %d\tSP: %d\n" +
			"EXP: %d / %d",
			super.toString(),
			attributePoints, skillPoints,
			experiencePoints, nextLevelExp()
		);
	}

}
