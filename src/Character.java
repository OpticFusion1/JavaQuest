import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.Locale;


/**
 * RPG Character abstraction for JavaQuest
 */
public class Character {

	// CONSTANTS
	public static final int WEAPON = 0;
	public static final int BODY = 1;
	public static final int HEAD = 2;
	public static final int LEGS = 3;
	//public static final int BACK = 4;
	//public static final int HANDS = 5;
	//public static final int SHOES = 6;
	public static final int EQUIP_SIZE = 4;


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

	//NOTE: equipment array size depends on how many pieces/subtypes are defined
	//NOTE: Some methods use Item ID number format as index for reading/writing to array
	private Equip[] equipment = new Equip[EQUIP_SIZE];

	// TODO setters & getters
	//private ArrayList<Item> inventory;
	//private ArrayList<Skill> skills; // TODO Skills class
	//private ArrayList<Ability> abilities; // TODO Abilities class


	// CONSTRUCTORS
	public Character(
		final String name,
		int level,
		final String role,
		final StatCalculator calculator,
		int str,
		int dex,
		int intel
	) throws RuntimeException {
		setName(name);
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

	/**
	 * @throws NullPointerException when name is null
	 * @throws IllegalArgumentException when name has illegal character sequence, use getMessage() for more info
	 */
	public void setName(final String name) throws RuntimeException {
		if (name == null) {
			throw new NullPointerException("Character name can't be null.");
		}
		this.name = name;
	}

	public int level() {
		return level;
	}

	/**
	 * Increases level and fully restores Character
	 */
	public void levelUp() {
		++level;
		rest();
	}

	public String role() {
		return role;
	}

	/**
	 * Sets Character role;
	 * fills String as "None" if argument is null
	 */
	public void setRole(final String role) {
		this.role = (role != null) ? role : "None";
	}

	public StatCalculator calculate() {
		return calculator;
	}

	/**
	 * Sets this Character StatCalculator;
	 * uses NullObject pattern if argument is null
	 */
	public void setStatCalculator(final StatCalculator calculator) {
		this.calculator = (calculator != null) ? calculator : new NullStatCalculator();
	}

	public long exp() {
		return calculator.exp(level);
	}

	public int attackDamage() {
		if (equipment[Character.WEAPON] != null) {
			return calculator.attack( strength() ) + equipment[Character.WEAPON].attackBonus();
		}
		return calculator.attack( strength() );
	}

	public int spellDamage() {
		if (equipment[Character.WEAPON] != null) {
			return calculator.spell( intelligence() ) + equipment[Character.WEAPON].spellBonus();
		}
		return calculator.spell( intelligence() );
	}

	/**
	 * Returns raw critical value, use criticalRate() to get percentage value (0 to 100)
	 */
	public int critical() {// TODO where to apply crit
		if (equipment[Character.WEAPON] != null) {
			return calculator.crit( dexterity() ) + equipment[Character.WEAPON].criticalBonus();
		}
		return calculator.crit( dexterity() );
	}

	/**
	 * Returns critical chance going from 0 to 100
	 */
	public float criticalRate() {
		return critical() / 1000f;
	}

	public int armour() {
		int total = 0;

		for (Equip item : equipment) {
			if (item == null) {
				continue;
			}
			total += item.armourBonus();
		}

		return total;
	}
	//TODO where to apply damage reduction
	public int negation() {
		int total = 0;

		for (Equip item : equipment) {
			if (item == null) {
				continue;
			}
			total += item.negationBonus();
		}
		
		return total;
	}

	public int maxHealth() {
		int total = calculator.hp(level);
		
		for (Equip item : equipment) {
			if (item == null) {
				continue;
			}
			total += item.healthBonus();
		}

		return total;
	}

	public int maxMagic() {
		int total = calculator.mp(level);

		for (Equip item : equipment) {
			if (item == null) {
				continue;
			}
			total += item.magicBonus();
		}

		return total;
	}

	public int health() {
		return healthPoints;
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
		int total = defaultStrength;
		
		for (Equip item : equipment) {
			if (item == null) {
				continue;
			}
			total += item.strengthBonus();
		}

		return total;
	}

	public void setDefaultStrength(int str) {
		if (str < 0) {
			throw new IllegalArgumentException("STR should be a positive value.");
		}
		defaultStrength = str;
	}

	public int defaultDexterity() {
		int total = defaultDexterity;

		for (Equip item : equipment) {
			if (item == null) {
				continue;
			}
			total += item.dexterityBonus();
		}

		return total;
	}

	public void setDefaultDexterity(int dex) {
		if (dex < 0) {
			throw new IllegalArgumentException("DEX should be a positive value.");
		}
		defaultDexterity = dex;
	}

	public int defaultIntelligence() {
		int total = defaultIntelligence;

		for (Equip item : equipment) {
			if (item == null) {
				continue;
			}
			total += item.intelligenceBonus();
		}

		return total;
	}

	public void setDefaultIntelligence(int intel) {
		if (intel < 0) {
			throw new IllegalArgumentException("INT should be a positive value.");
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
	 * @return Copy of the Equip at the specified index; returns null if it is empty
	 * @param index Use Character.[WEAPON|BODY|HEAD|LEGS]
	 */
	public Equip getEquipAt(int index) {
		if (equipment[index] == null) {
			return null;
		}
		return new Equip(equipment[index]);
	}

	/**
	 * Equips an item to the Character, overriding any equipped item of the same subtype
	 * @throws ArrayIndexOutOfBoundsException When the third least significant digit of the ID is illegal.
	 */
	public void equip(final Equip item) throws ArrayIndexOutOfBoundsException { //TODO check requirements
		int subtype = (item.id() % 1000) / 100;

		try {
			equipment[subtype] = new Equip(item);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Item ID equip subtype indicator digit is invalid.");
		}
	}

	/**
	 * Sets equipment at the specified index as null
	 * @param index Use Character.[WEAPON|BODY|HEAD|LEGS]
	 */
	public void unequip(int index) {
		equipment[index] = null;
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
		strength = defaultStrength();
		dexterity = defaultDexterity();
		intelligence = defaultIntelligence();
	}

	/**
	 * Uses a Consumable Item
	 */
	public void use(final Consumable item) {
		item.use(this);
	}

	/**
	 * NOTE: Primary Stats follow the format: Default (Equipment) (Modifier)
	 */
    @Override
	public String toString() {
		return String.format(Locale.ROOT,
			"%s - LVL %d %s\n" +
			"HP: %d / %d\n" +
			"MP: %d / %d\n" +
			"%d poison\n" +
			"STR: %d (%d) (%d)\tAD: %d\n" +
			"DEX: %d (%d) (%d)\tCR: %.2f%%\n" +
			"INT: %d (%d) (%d)\tSD: %d",
			name, level, role,
			healthPoints, maxHealth(),
			magicPoints, maxMagic(),
			poison,
			defaultStrength, (defaultStrength() - defaultStrength), (strength() - defaultStrength()), attackDamage(),
			defaultDexterity, (defaultDexterity() - defaultDexterity), (dexterity() - defaultDexterity()), criticalRate(),
			defaultIntelligence, (defaultIntelligence() - defaultIntelligence), (intelligence() - defaultIntelligence()), spellDamage()
		);
	}


	// PRIVATE FUNCTIONS
	private void setHealth(int hp) {
		healthPoints = limit(hp, 0, maxHealth());
	}

	private void setMagic(int mp) {
		magicPoints = limit(mp, 0, maxMagic());
	}

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
