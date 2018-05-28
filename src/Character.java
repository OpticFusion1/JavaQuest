import static java.lang.Math.max;
import static java.lang.Math.min;


/**
 * RPG Character abstraction for JavaQuest
 */
public abstract class Character implements Cloneable {

	// ATTRIBUTES
	public final String name;

	public String role = "None";
	private int level = 0;

	private int maxHealthPoints = 1;
	private int maxMagicPoints = 0;

	private int healthPoints = 1;
	private int magicPoints = 0;

	private int poison = 0;

	private int defaultStrength = 0;
	private int defaultDexterity = 0;
	private int defaultIntelligence = 0;

	private int strength = 0;
	private int dexterity = 0;
	private int intelligence = 0;

	//@NOTE after those, check if class is still Cloneable
	//@TODO equipment[]
	//@TODO skills[]
	//@TODO abilities[]
	//@TODO inventory[]


	// CONSTRUCTORS
	public Character(
		String name,
		String role,
		int level,
		int hp,
		int mp,
		int str,
		int dex,
		int intel
	) {
		this.name = name;
		this.role = role;
		this.level = level;

		maxHealthPoints = hp;
		maxMagicPoints = mp;

		healthPoints = maxHealthPoints;
		magicPoints = maxMagicPoints;

		defaultStrength = str;
		defaultDexterity = dex;
		defaultIntelligence = intel;

		strength = defaultStrength;
		dexterity = defaultDexterity;
		intelligence = defaultIntelligence;
	}

	public Character(
		String name,
		String role,
		int level
	) {
		this.name = name;
		this.role = role;
		this.level = level;
	}


	// METHODS
	public int level() {
		return level;
	}

	public void levelUp() {
		++level;
	}

	public int maxHealth() {
		return maxHealthPoints;
	}

	public void setMaxHealth(int maxHP) {
		maxHealthPoints = max(1, maxHP);
	}

	public int maxMagic() {
		return maxMagicPoints;
	}

	public void setMaxMagic(int maxMP) {
		maxMagicPoints = max(0, maxMP);
	}

	public int health() {
		return healthPoints;
	}

	private void setHealth(int hp) {
		healthPoints = limit(hp, 0, maxHealthPoints);
	}

	/**
	 * Increases the Character's current HP
	 */
	public void heal(int healing) {
		setHealth(healthPoints + max(0, healing));
	}

	/**
	 * Decreases the Character's current HP
	 */
	public void wound(int damage) {
		setHealth(healthPoints - max(0, damage));
	}

	public boolean isDead() {
		return healthPoints <= 0 || poison >= 10;
	}

	public int magic() {
		return magicPoints;
	}

	private void setMagic(int mp) {
		magicPoints = limit(mp, 0, maxMagicPoints);
	}

	/**
	 * Increases the Character's current MP
	 */
	public void restore(int mana) {
		setMagic(magicPoints + max(0, mana));
	}

	/**
	 * Decreases the Character's current MP
	 */
	public void deplete(int manaCost) {
		setMagic(magicPoints - max(0, manaCost));
	}

	public int poisonCount() {
		return poison;
	}

	/**
	 * Increases the Character's poison count by one
	 */
	public void wither() {
		poison = min(poison + 1, 10);
	}

	/**
	 * Removes all poison from Character
	 */
	public void remedy() {
		poison = 0;
	}

	public int defaultStrength() {
		return defaultStrength;
	}

	/**
	 * Sets STR default value
	 */
	public void setStrength(int str) {
		defaultStrength = max(0, str);
	}

	public int defaultDexterity() {
		return defaultDexterity;
	}

	/**
	 * Sets DEX default value
	 */
	public void setDexterity(int dex) {
		defaultDexterity = max(0, dex);
	}

	public int defaultIntelligence() {
		return defaultIntelligence;
	}

	/**
	 * Sets INT default value
	 */
	public void setIntelligence(int intel) {
		defaultIntelligence = max(0, intel);
	}

	/**
	 * @return Current STR with any modifier
	 */
	public int strength() {
		return max(0, strength);
	}

	/**
	 * @return Current DEX with any modifier
	 */
	public int dexterity() {
		return max(0, dexterity);
	}

	/**
	 * @return Current INT with any modifier
	 */
	public int intelligence() {
		return max(0, intelligence);
	}

	/**
	 * Modifies basic attributes by given argument values
	 */
	public void addModifier(int str, int dex, int intel) {
		strength += str;
		dexterity += dex;
		intelligence += intel;
	}
	
	public void removeModifier(int str, int dex, int intel) {
		addModifier(-1 * str, -1 * dex, -1 * intel);
	}

	/**
	 * Fully restores the Character:
	 * recovers HP and MP,
	 * removes all poison counters and
	 * removes all non-equipment modifiers.
	 */
	public void rest() {
		healthPoints = maxHealthPoints;
		magicPoints = maxMagicPoints;
		poison = 0;
		strength = defaultStrength;
		dexterity = defaultDexterity;
		intelligence = defaultIntelligence;
	}

    @Override
	public String toString() {
		return String.format(
			"%s - LVL %d %s\n " +
			"HP: %d/%d\n " +
			"MP: %d/%d\n " +
			"%d poison\n " +
			"STR: %d (%d)\n " +
			"DEX: %d (%d)\n " +
			"INT: %d (%d)",
			name, level, role,
			healthPoints, maxHealthPoints,
			magicPoints, maxMagicPoints,
			poison,
			defaultStrength, (strength() - defaultStrength),
			defaultDexterity, (dexterity() - defaultDexterity),
			defaultIntelligence, (intelligence() - defaultIntelligence)
		);
	}

	@Override
    protected final Object clone() throws CloneNotSupportedException {
        return this;
    }


	// CLASS FUNCTIONS
	/**
	 * Limits value to a specific range
	 * 
	 * @param floor Lower bound
	 * @param ceil Upper bound
	 * @param value Original value
	 * 
	 * @return If value is already in range, returns it as it is;
	 * else, returns the limit it has gone through.
	 */
	protected static int limit(int value, int floor, int ceil) {
		if (value <= ceil && value >= floor) {
			return value;

		} else if (value < floor) {
			return floor;
		}

		return ceil;
	}

}
