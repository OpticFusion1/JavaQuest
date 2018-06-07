import java.util.Locale;


public class Equip extends Item {

	// ATTRIBUTES
	//requirements
	private int strengthRequirement, dexterityRequirement, intelligenceRequirement;
	private int levelRequirement;
	private String roleRequirement;

	//bonuses
	private int strengthBonus, dexterityBonus, intelligenceBonus;
	private int armourBonus, negationBonus;
	private int healthBonus, magicBonus;
	private int attackBonus, spellBonus, criticalBonus; //NOTE: express criticalBonus as raw critical (mili%)


	// CONSTRUCTORS
	public Equip(
		int id, final String name, final String description,
		int strengthRequirement, int dexterityRequirement, int intelligenceRequirement,
		int levelRequirement,
		final String roleRequirement,
		int strengthBonus, int dexterityBonus, int intelligenceBonus,
		int armourBonus, int negationBonus,
		int healthBonus, int magicBonus,
		int attackBonus, int spellBonus, int criticalBonus
	) {
		super(id, name, description);
		this.strengthRequirement = strengthRequirement;
		this.dexterityRequirement = dexterityRequirement;
		this.intelligenceRequirement = intelligenceRequirement;
		this.levelRequirement = levelRequirement;
		setRoleRequirement(roleRequirement);
		this.strengthBonus = strengthBonus;
		this.dexterityBonus = dexterityBonus;
		this.intelligenceBonus = intelligenceBonus;
		this.armourBonus = armourBonus;
		this.negationBonus = negationBonus;
		this.healthBonus = healthBonus;
		this.magicBonus = magicBonus;
		this.attackBonus = attackBonus;
		this.spellBonus = spellBonus;
		this.criticalBonus = criticalBonus;
	}

	public Equip(Equip other) {
		this(
			other.id(), other.name(), other.description(),
			other.strengthRequirement(), other.dexterityRequirement(), other.intelligenceRequirement(), 
			other.levelRequirement(), other.roleRequirement(), 
			other.strengthBonus(), other.dexterityBonus(), other.intelligenceBonus(), 
			other.armourBonus(), other.negationBonus(), 
			other.healthBonus(), other.magicBonus(), 
			other.attackBonus(), other.spellBonus(), other.criticalBonus()
		);
	}


	// METHODS
	public boolean canBeEquiped(final Character character) {
		return	character.strength() >= strengthRequirement &&
				character.dexterity() >= dexterityRequirement &&
				character.intelligence() >= intelligenceRequirement &&
				character.level() >= levelRequirement &&
				character.role().toLowerCase(Locale.ROOT).contains(roleRequirement.toLowerCase(Locale.ROOT));
	}

	// automatically generated getters & setters

	public int strengthRequirement() {
		return strengthRequirement;
	}
	
	/*public void setStrengthRequirement(int strengthRequirement) {
		this.strengthRequirement = strengthRequirement;
	}*/
	
	public int dexterityRequirement() {
		return dexterityRequirement;
	}
	
	/*public void setDexterityRequirement(int dexterityRequirement) {
		this.dexterityRequirement = dexterityRequirement;
	}*/
	
	public int intelligenceRequirement() {
		return intelligenceRequirement;
	}
	
	/*public void setIntelligenceRequirement(int intelligenceRequirement) {
		this.intelligenceRequirement = intelligenceRequirement;
	}*/
	
	public int levelRequirement() {
		return levelRequirement;
	}
	
	/*public void setLevelRequirement(int levelRequirement) {
		this.levelRequirement = levelRequirement;
	}*/
	
	public String roleRequirement() {
		return roleRequirement;
	}
	
	/**
	 * Sets Equip's required Role;
	 * fills String as empty if argument is null
	 */
	public void setRoleRequirement(final String roleRequirement) {
		this.roleRequirement = (roleRequirement != null) ? roleRequirement : "";
	}
	
	public int strengthBonus() {
		return strengthBonus;
	}
	
	/*public void setStrengthBonus(int strengthBonus) {
		this.strengthBonus = strengthBonus;
	}*/
	
	public int dexterityBonus() {
		return dexterityBonus;
	}
	
	/*public void setDexterityBonus(int dexterityBonus) {
		this.dexterityBonus = dexterityBonus;
	}*/
	
	public int intelligenceBonus() {
		return intelligenceBonus;
	}
	
	/*public void setIntelligenceBonus(int intelligenceBonus) {
		this.intelligenceBonus = intelligenceBonus;
	}*/
	
	public int armourBonus() {
		return armourBonus;
	}
	
	/*public void setArmourBonus(int armourBonus) {
		this.armourBonus = armourBonus;
	}*/
	
	public int negationBonus() {
		return negationBonus;
	}
	
	/*public void setNegationBonus(int negationBonus) {
		this.negationBonus = negationBonus;
	}*/
	
	public int healthBonus() {
		return healthBonus;
	}
	
	/*public void setHealthBonus(int healthBonus) {
		this.healthBonus = healthBonus;
	}*/
	
	public int magicBonus() {
		return magicBonus;
	}
	
	/*public void setMagicBonus(int magicBonus) {
		this.magicBonus = magicBonus;
	}*/
	
	public int attackBonus() {
		return attackBonus;
	}
	
	/*public void setAttackBonus(int attackBonus) {
		this.attackBonus = attackBonus;
	}*/
	
	public int spellBonus() {
		return spellBonus;
	}
	
	/*public void setSpellBonus(int spellBonus) {
		this.spellBonus = spellBonus;
	}*/
	
	public int criticalBonus() {
		return criticalBonus;
	}
	
	/*public void setCriticalBonus(int criticalBonus) {
		this.criticalBonus = criticalBonus;
	}*/

}
