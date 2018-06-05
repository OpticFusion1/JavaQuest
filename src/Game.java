public class Game {

	public static void main(String[] args) {
		Player adventurer = new Player();

		Player mage = new Player();
		mage.setRole("Mage");
		mage.setStatCalculator(new MageCalculator());

		Player warrior = new Player();
		warrior.setRole("Warrior");
		warrior.setStatCalculator(new WarriorCalculator());

		Player thief = new Player();
		thief.setRole("Thief");
		thief.setStatCalculator(new ThiefCalculator());

		Player[] players = new Player[] {
			adventurer, mage, warrior, thief,
		};

		System.out.println("\"LVL\",\"Class\",\"HP\",\"MP\",\"AD\",\"SD\",\"CR\",\"EXP\"");

		for (int level = 1; level <= 40; level++) {
			for (Player player : players) {
				System.out.println(String.format(java.util.Locale.US,
					"%d,%s,%d,%d,%d,%d,%.2f,%d",
					player.level(), player.role(), player.maxHealth(), player.maxMagic(),
					player.attackDamage(), player.spellDamage(), player.criticalRate(), player.nextLevelExp()
				));

				player.levelUp();

				switch (player.role()) {
					case "Adventurer":
						player.spendAP("STR");
						player.spendAP("STR");
						player.spendAP("STR");
						player.spendAP("DEX");
						player.spendAP("DEX");
						break;
					
					case "Mage":
						player.spendAP("INT");
						player.spendAP("INT");
						player.spendAP("INT");
						player.spendAP("INT");
						player.spendAP("INT");
						break;
					
					case "Warrior":
						player.spendAP("STR");
						player.spendAP("STR");
						player.spendAP("STR");
						player.spendAP("STR");
						player.spendAP("DEX");
						break;
					
					case "Thief":
						if (player.defaultDexterity() < 100) {
							player.spendAP("DEX");
							player.spendAP("DEX");
							player.spendAP("DEX");
							player.spendAP("DEX");
							player.spendAP("DEX");
						} else {
							player.spendAP("STR");
							player.spendAP("STR");
							player.spendAP("STR");
							player.spendAP("STR");
							player.spendAP("STR");
						}
						break;
				}
			}
		}

	}

}
