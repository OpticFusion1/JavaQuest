import java.util.regex.*;
import java.util.Locale;


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
			throw new NullPointerException("STAT string can't be null.");
		}

		if (attributePoints < 1) {
			return;
		}

		switch (stat) {
			case "STR":
				setDefaultStrength(defaultStrength() + 1);
				break;
			
			case "DEX":
				setDefaultDexterity(defaultDexterity() + 1);
				break;
			
			case "INT":
				setDefaultIntelligence(defaultIntelligence() + 1);
				break;
			
			default:
				throw new IllegalArgumentException("Specified STAT doesn't exist.");
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
	public void setName(final String name) throws RuntimeException {
		if (!Pattern.compile("^\\w{3,15}$").matcher(name).matches() ||
			Pattern.compile(".*_{2,}.*").matcher(name).matches()	) {
			throw new IllegalArgumentException("Invalid name: Should have from 3 to 15 characters, those being only letters, numbers and \"_\". Can't have two \"_\"s in a row.");
		}

		super.setName(name);
	}

	@Override
	public void setRole(final String role) {
		//TODO setStatCalculator based on role, also recalculate stats
		super.setRole(role);
	}

	@Override
	public void levelUp() {
		attributePoints += 5;
		skillPoints += 1;
		super.levelUp();
	}

	@Override
	public String toString() {
		return String.format(Locale.ROOT,
			"%s\n" +
			"AP: %d\tSP: %d\n" +
			"EXP: %d / %d",
			super.toString(),
			attributePoints, skillPoints,
			experiencePoints, nextLevelExp()
		);
	}

}
