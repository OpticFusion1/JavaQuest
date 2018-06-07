import java.util.Locale;


/**
 * Base Item class for JavaQuest
 */
public class Item {

	// ATTRIBUTES
	private int id;
	private String name;
	private String description;


	// CONSTRUCTORS
	public Item(int id, final String name, final String description) {
		this.id = id;
		setName(name);
		setDescription(description);
	}

	public Item(Item other) {
		this.id = other.id();
		this.name = other.name();
		this.description = other.description();
	}


	// METHODS
	/**
	 * @return Item ID number formatted as tsxx;
	 * t -> Type: Item, Consumable, Equip;
	 * s -> Subtype: Head equip, Body equip, etc;
	 * xx -> Number
	 */
	public int id() {
		return id;
	}

	public String name() {
		return name;
	}

	public void setName(final String name) {
		if (name == null) {
			throw new NullPointerException("Item name can't be null.");
		}
		this.name = name;
	}

	public String description() {
		return description;
	}

	/**
	 * Sets Item description;
	 * fills String as empty if argument is null
	 */
	public void setDescription(final String description) {
		this.description = (description != null) ? description : "";
	}

    @Override
    public String toString() {
		return String.format(Locale.ROOT,
			"[%d] %s:\n%s",
			id, name, description
		);
	}

}
