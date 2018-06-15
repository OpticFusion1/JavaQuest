package br.ufsc.gdev.zkirmisher.javaquest.entities;


import java.util.regex.*;
import java.util.Locale;

import br.ufsc.gdev.zkirmisher.javaquest.statistics.*;


/**
 * JavaQuest user-playable Character.
 */
public class Player extends Character {

	// ATTRIBUTES
	private long experiencePoints = 0;
	private int attributePoints = 0;

	//TODO Skill Points

	private Inventory inventory = new Inventory(15 * Item.TYPES);//XXX Default Player inventory size.


	// CONSTRUCTORS
	public Player() {
		super(
			"Hero", 1,
			"Adventurer", new AdventurerCalculator(),
			5, 5, 5 //base stats
		);
	}


	// METHODS
	public long currentExp() {
		return experiencePoints;
	}

	public long nextLevelExp() {
		return super.experience();
	}

	public void gainExperience(long exp) {
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
	 * Exchanges one Attribute Point for a permanent increment of chosen stat.
	 * @param stat NOTE: depends on constants available at some Character.STAT
	 * @throws IllegalArgumentException when the specified STAT doesn't exist.
	 */
	public void spendAP(int stat) {
		if (attributePoints < 1) {
			return;
		}

		switch (stat) {
			case Character.STR:
				super.setBaseStrength(super.baseStrength() + 1);
				break;

			case Character.DEX:
				super.setBaseDexterity(super.baseDexterity() + 1);
				break;

			case Character.INT:
				super.setBaseIntelligence(super.baseIntelligence() + 1);
				break;

			default:
				throw new IllegalArgumentException("Specified STAT doesn't exist.");
		}

		--attributePoints;
	}

	public Inventory inventory() {
		return inventory;
	}

	/**
	 * Uses an item from the inventory.
	 * <p>
	 * If it's an Equip, equip it and store the previous one on the inventory.
	 * <p>
	 * If it's a consumable, remove it from the inventory.
	 */
	public void use(int inventoryIndex) {
		Item item = inventory.get(inventoryIndex);
		if (item == null) {
			return;
		}

		if (item instanceof Equip) {
			Equip previous = super.getEquipAt(item.subtype());
			super.equip((Equip) item);
			inventory.remove(inventoryIndex, 1);
			inventory.add(previous);
		} else {
			super.use(item);
			inventory.remove(inventoryIndex, 1);
		}
	}

	/**
	 * @throws IllegalArgumentException when name has illegal character sequence, use getMessage() for more info.
	 */
	@Override
	public void setName(final String name) {
		if (!Pattern.compile("^\\w{3,15}$").matcher(name).matches() ||
			Pattern.compile(".*_{2,}.*").matcher(name).matches()	) {
			throw new IllegalArgumentException("Invalid name: Should have from 3 to 15 characters, those being only letters, numbers and \"_\". Can't have two \"_\"s in a row.");
		}

		super.setName(name);
	}

	@Override
	public void levelUp() {
		attributePoints += 5;
		super.levelUp();
	}


	@Override
	public void use(Item item) {
		if (item == null) {
			return;
		}

		int index = inventory.indexOf(item);
		if (index >= 0) {
			this.use(index);
		}
	}

	@Override
	public boolean equip(final Equip item) {
		if (!super.canEquip(item)) {
			return false;
		} else {
			this.use(item);
			return true;
		}
	}

	@Override
	public String toString() {
		return String.format(Locale.ROOT,
			"%s\n" +
			"AP: %d\n" +
			"EXP: %d / %d",
			super.toString(),
			attributePoints,
			experiencePoints, nextLevelExp()
		);
	}

}
