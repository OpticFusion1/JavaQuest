import static java.lang.Math.abs;


public class PlayerCalculator implements StatCalculator {

	// METHODS
	public long exp(int level) {
		return 10 * PlayerCalculator.fibonacci(level);
	}

	public int hp(int level) {
		return 25 + 75 * level;
	}

	public int mp(int level) {
		return 50 * level;
	}


	// CLASS FUNCTIONS
	private static int fibonacci(int n) {
		int previous = 0;
		int next = 1;
		int result = 1;

		for (int i = 0; i < abs(n); i++) {
			result = previous + next;
			previous = next;
			next = result;
		}

		return result;
	}

}