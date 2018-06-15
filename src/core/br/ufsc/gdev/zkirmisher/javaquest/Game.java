package br.ufsc.gdev.zkirmisher.javaquest;


import br.ufsc.gdev.zkirmisher.javaquest.entities.*;
import br.ufsc.gdev.zkirmisher.javaquest.statistics.*;


public class Game {

	public static void main(String[] args) {
		// character
		Player hero = new Player();
		hero.setName("Zkirmisher");
		hero.setRole("Mage");
		hero.setStatCalculator(new MageCalculator());
		hero.deplete(40);
		//print
		System.out.println(hero.toString() + "\n");

		// potions
		Item healthPotion = new Item(
			"Health Potion", Item.USE * 1000 + 000,
			"Restores 50 HP",
			user -> {
				user.heal(50);
			}
		);
		//print
		System.out.println("CREATED: " + healthPotion.toString() + "\n");

		Item manaPotion = new Item(
			"Mana Potion", Item.USE * 1000 + 100,
			"Restores 50 MP",
			user -> {
				user.restore(50);
			}
		);
		//print
		System.out.println("CREATED: " + manaPotion.toString() + "\n");

		// equips
		Equip wizardHat = new Equip(
			"Wizard Hat", Item.EQUIP * 1000 + Item.EQUIP_HEAD * 100 + 10,
			"A pointy hat, only for Mages.\n+5 INT\n+10 Negation\n+25 MP",
			0, 0, +5, //primary stat bonuses
			0, 0, 0, //damage bonuses
			0, +10, //reduction bonuses
			0, +25, //vitality bonuses
			1, "Mage", //level/role requirements
			0, 0, 5 //stat requirements
		);
		//print
		System.out.println("CREATED: " + wizardHat.toString() + "\n");

		// add to inventory
		hero.inventory().add(healthPotion, 5);
		hero.inventory().add(manaPotion, 11);
		hero.inventory().add(wizardHat);
		hero.inventory().swap(1, 2);
		//print
		System.out.println(hero.inventory().toString());
		hero.inventory().sort();
		System.out.println(hero.inventory().toString());

		// equip & use
		if (hero.inventory().contains(wizardHat.id())) {
			hero.equip(wizardHat);
		}
		if (hero.isEquipping(wizardHat)) {
			System.out.println(hero.name() + " is wearing a hat!\n");
		}
		if (hero.inventory().contains(manaPotion, 10)) {
			System.out.println("USING: " + manaPotion.name() + "\n");
			hero.use(hero.inventory().indexOf(manaPotion));
		}
		//print
		System.out.println(hero.inventory().toString());
		System.out.println(hero.toString() + "\n");

		//unequip
		hero.unequip(wizardHat);
		//print
		System.out.println(hero.toString() + "\n");

	}

}
