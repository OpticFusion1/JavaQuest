public class MageCalculator extends PlayerCalculator {

	// METHODS
	public int hp(int level) {
		return 80 + 48 * level;
	}

	public int mp(int level) {
		return 150 * level;
	}

	public int attack(int str) {
		return str * 5;
	}

	public int spell(int intel) {
		return intel * 15;
	}

	public int crit(int dex) {
		return dex * 250;
	}

}
