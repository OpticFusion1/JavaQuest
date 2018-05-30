public class Game {

	public static void main(String[] args) {
		Player my_char = new Player();

		my_char.setName("Zkirmisher");
		my_char.setRole("Mage");
		my_char.addModifier(-2, +1, +2);
		my_char.wither();
		my_char.deplete(25);
		my_char.wound(90);

		System.out.println(my_char.toString());

		my_char.gainExperience(37);

		System.out.println("\n" + my_char.toString());
	}

}