public class ThiefCalculator extends PlayerCalculator {

	// METHODS
	public int hp(int level) {
		return 40 + 74 * level;
	}

	public int mp(int level) {
		return 50 * level;
	}

	public int attack(int str) {
		return str * 15;
	}

	public int spell(int intel) {
		return intel * 5;
	}

	public int crit(int dex) {
		return dex * 1000;
	}

}
