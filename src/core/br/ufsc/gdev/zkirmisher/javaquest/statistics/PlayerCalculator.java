package br.ufsc.gdev.zkirmisher.javaquest.statistics;


public abstract class PlayerCalculator implements StatCalculator {

	// METHODS
	@Override
	public long exp(int level) {
		return 10 * PlayerCalculator.fibonacci(level); //XXX Since longs are signed, overflows after level 40.
	}

	@Override
	public abstract int hp(int level);

	@Override
	public abstract int mp(int level);

	@Override
	public abstract int attack(int str);

	@Override
	public abstract int spell(int intel);

	@Override
	public abstract int crit(int dex);


	// CLASS FUNCTIONS
	private static int fibonacci(int n) {
		int previous = 0;
		int next = 1;
		int result = 1;

		for (int i = 0; i < n; i++) {
			result = previous + next;
			previous = next;
			next = result;
		}

		return result;
	}

}
