public class Consumable extends Item {
	
	// ATTRIBUTES
	private int hpRegen = 0;
	private int mpRegen = 0;


	// CONSTRUCTORS
	public Consumable(
		int id, final String name, final String description,
		int hpRegen, int mpRegen
	) {
		super(id, name, description);
		this.hpRegen = hpRegen;
		this.mpRegen = mpRegen;
	}

	public Consumable(Consumable other) {
		this(
			other.id(), other.name(), other.description(),
			other.hpRegen(), other.mpRegen()
		);
	}


	// METHODS
	public int hpRegen() {
		return hpRegen;
	}

	public int mpRegen() {
		return mpRegen;
	}

	/**
	 * Consumes item
	 * @param consumer Character that receives the Consumable's effects
	 */
	public void use(Character consumer) {
		consumer.heal( hpRegen );
		consumer.restore( mpRegen );
	}

}
