package triphub.entity.subservices;

/**
 * This enum represents different types of accommodations that can be offered
 * within the TripHub application. Each accommodation type is associated with a
 * label that provides a more descriptive representation.
 */
public enum AccommodationType {
	/** Represents a hotel accommodation type. */
	Hotel("Hotel"),

	/** Represents a Bed and Breakfast (BnB) accommodation type. */
	BnB("BnB"),

	/** Represents a guestroom accommodation type. */
	GuestRoom("Guestroom"),

	/** Represents a cottage accommodation type. */
	Cottage("Cottage"),

	/** Represents a camping accommodation type. */
	Camping("Camping");

	private String label;

	/**
	 * Constructs an AccommodationType enum constant with the provided label.
	 *
	 * @param label The label associated with the accommodation type.
	 */
	AccommodationType(String label) {
		this.label = label;
	}

	/**
	 * Returns the label associated with the accommodation type.
	 *
	 * @return The label of the accommodation type.
	 */
	public String getLabel() {
		return label;
	}
}
