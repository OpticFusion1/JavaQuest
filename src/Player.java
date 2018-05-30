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
			"Hero",
			1, "Adventurer",
			new PlayerCalculator(),
			100, 50,	//HP, MP
			5, 5, 5		//base stats
		);
	}

	// METHODS
	public void gainExperience(int exp) {
		experiencePoints += exp;

		//keeps leveling up if theres anough EXP
		while ( experiencePoints >= calculate().exp(level()) ) {
			experiencePoints -= calculate().exp(level());
			levelUp();
		}
	}

	@Override
	public void setName(final String name) {
		//@TODO valid name verification (throw something)
		super.setName(name);
	}

	@Override
	public void setRole(final String role) {
		//@TODO setStatCalculator based on role
		super.setRole(role);
	}

	@Override
	public void levelUp() {
		attributePoints += 5; //@TODO statCalculate
		skillPoints += 3; //@TODO statCalculate
		
		super.levelUp();
		
		setMaxHealth( calculate().hp(level()) );
		setMaxMagic( calculate().mp(level()) );
		
		rest();
	}

	@Override
	public String toString() {
		return String.format(
			"%s\n" +
			"AP: %d \t SP: %d\n" +
			"EXP: %d / %d\n",
			super.toString(),
			attributePoints, skillPoints,
			experiencePoints, calculate().exp(level())
		);
	}

}
