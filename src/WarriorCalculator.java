public class WarriorCalculator extends PlayerCalculator {

	// METHODS
	public int hp(int level) {
		return 150 * level;
	}

	public int mp(int level) {
		return 20 + 37 * level;
	}

	public int attack(int str) {
		return (int)(str * 12.5);
	}

	public int spell(int intel) {
		return intel * 4;
	}

	public int crit(int dex) {
		return dex * 500;
	}

}
