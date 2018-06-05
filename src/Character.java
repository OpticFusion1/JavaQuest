import static java.lang.Math.max;
import static java.lang.Math.min;


/**
 * RPG Character abstraction for JavaQuest
 */
public abstract class Character {

	// ATTRIBUTES
	private String name;
	private int level = 0;

	private String role = "None";
	private StatCalculator calculator;

	private int healthPoints = 1;
	private int magicPoints = 0;

	private int poison = 0;

	private int defaultStrength = 0;
	private int defaultDexterity = 0;
	private int defaultIntelligence = 0;

	private int strength = 0;
	private int dexterity = 0;
	private int intelligence = 0;

	// TODO setters & getters
	//private ArrayList<Item> equipment; // TODO Item class // TODO Armor and MagicRes methods from equipment
	//private ArrayList<Skill> skills; // TODO Skills class
	//private ArrayList<Ability> abilities; // TODO Abilities class
	//private ArrayList<Item> inventory;


	// CONSTRUCTORS
	public Character(
		final String name,
		int level,
		final String role,
		StatCalculator calculator,
		int str,
		int dex,
		int intel
	) throws NullPointerException {
		try {
			setName(name);
		} catch (NullPointerException err) {
			throw err;
		}

		this.level = level;

		setRole(role);
		setStatCalculator(calculator);

		healthPoints = calculator.hp(level);
		magicPoints = calculator.mp(level);

		defaultStrength = str;
		defaultDexterity = dex;
		defaultIntelligence = intel;

		strength = defaultStrength;
		dexterity = defaultDexterity;
		intelligence = defaultIntelligence;
	}


	// METHODS
	public String name() {
		return name;
	}

	public void setName(final String name) throws NullPointerException {
		if (name == null) {
			throw new NullPointerException("Character name can't be null.");
		}
		this.name = name;
	}

	public int level() {
		return level;
	}

	public void levelUp() {
		++level;
	}

	public String role() {
		return role;
	}

	public void setRole(final String role) {
		this.role = (role != null) ? role : "";
	}

	public StatCalculator calculate() {
		return calculator;
	}

	public void setStatCalculator(final StatCalculator calculator) {
		this.calculator = (calculator != null) ? calculator : new NullStatCalculator();
	}

	public long exp() {
		return calculator.exp(level);
	}

	public int attackDamage() {
		return calculator.attack( strength() ); //TODO also apply equipment
	}

	public int spellDamage() {
		return calculator.spell( intelligence() ); //TODO also apply equipment
	}

	/**
	 * Returns raw critical value, use criticalRate() to get percentage value (0 to 100)
	 */
	public int critical() {
		return calculator.crit( dexterity() ); //TODO also apply equipment
	}

	/**
	 * Returns critical chance going from 0 to 100
	 */
	public float criticalRate() {
		return critical() / 1000f;
	}

	public int armour() {
		return 0; //TODO needs equipment
	}

	public int negation() {
		return 0; //TODO needs equipment
	}

	public int maxHealth() {
		return calculator.hp(level); //TODO also apply equipment
	}

	public int maxMagic() {
		return calculator.mp(level); //TODO also apply equipment
	}

	public int health() {
		return healthPoints;
	}

	private void setHealth(int hp) {
		healthPoints = limit(hp, 0, maxHealth());
	}

	/**
	 * Increases the Character's current HP
	 */
	public void heal(int healing) {
		setHealth(healthPoints + healing);
	}

	/**
	 * Decreases the Character's current HP
	 */
	public void wound(int damage) {
		setHealth(healthPoints - damage);
	}

	public boolean isDead() {
		return healthPoints <= 0 || poison >= 10;
	}

	public int magic() {
		return magicPoints;
	}

	private void setMagic(int mp) {
		magicPoints = limit(mp, 0, maxMagic());
	}

	/**
	 * Increases the Character's current MP
	 */
	public void restore(int mana) {
		setMagic(magicPoints + mana);
	}

	/**
	 * Decreases the Character's current MP
	 */
	public void deplete(int manaCost) {
		setMagic(magicPoints - manaCost);
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
		if (str < 0) {
			throw new IllegalArgumentException("STR should be a positive value!");
		}
		defaultStrength = str;
	}

	public int defaultDexterity() {
		return defaultDexterity;
	}

	/**
	 * Sets DEX default value
	 */
	public void setDexterity(int dex) {
		if (dex < 0) {
			throw new IllegalArgumentException("DEX should be a positive value!");
		}
		defaultDexterity = dex;
	}

	public int defaultIntelligence() {
		return defaultIntelligence;
	}

	/**
	 * Sets INT default value
	 */
	public void setIntelligence(int intel) {
		if (intel < 0) {
			throw new IllegalArgumentException("INT should be a positive value!");
		}
		defaultIntelligence = intel;
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
		healthPoints = maxHealth();
		magicPoints = maxMagic();
		remedy();
		strength = defaultStrength;
		dexterity = defaultDexterity;
		intelligence = defaultIntelligence;
	}

    @Override
	public String toString() {
		return String.format(java.util.Locale.US,
			"%s - LVL %d %s\n" +
			"HP: %d / %d\n" +
			"MP: %d / %d\n" +
			"%d poison\n" +
			"STR: %d (%d)\tAD: %d\n" +
			"DEX: %d (%d)\tCR: %.2f%%\n" +
			"INT: %d (%d)\tSD: %d",
			name, level, role,
			healthPoints, maxHealth(),
			magicPoints, maxMagic(),
			poison,
			defaultStrength, (strength() - defaultStrength), attackDamage(),
			defaultDexterity, (dexterity() - defaultDexterity), criticalRate(),
			defaultIntelligence, (intelligence() - defaultIntelligence), spellDamage()
		);
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
	private static int limit(int value, int floor, int ceil) {
		if (value <= ceil && value >= floor) {
			return value;

		} else if (value < floor) {
			return floor;
		}

		return ceil;
	}

}
