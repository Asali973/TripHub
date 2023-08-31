package triphub.entity.subservices;

/**
 * This enum represents different types of transportation that can be offered within the TripHub application.
 * Each transportation type is associated with a label that provides a more descriptive representation.
 */
public enum TransportationType {
    /** Represents a plane transportation type. */
    Plane("Plane"),

    /** Represents a train transportation type. */
    Train("Train"),

    /** Represents a bus transportation type. */
    Bus("Bus"),

    /** Represents a boat transportation type. */
    Boat("Boat"),

    /** Represents a bike transportation type. */
    Bike("Bike"),

    /** Represents a motorcycle transportation type. */
    Motorcycle("Motorcycle"),

    /** Represents a car transportation type. */
    Car("Car"),

    /** Represents a camping car transportation type. */
    CampingCar("Camping Car"),

    /** Represents an electric car transportation type. */
    ElectricCar("Electric Car"),

    /** Represents an electric bike transportation type. */
    ElectricBike("Electric Bike"),

    /** Represents an electric motorcycle transportation type. */
    ElectricMotorcycle("Electric Motorcycle"),

    /** Represents an electric scooter transportation type. */
    ElectricScooter("Electric Scooter");

    private String label;

    /**
     * Constructs a TransportationType enum constant with the provided label.
     *
     * @param label The label associated with the transportation type.
     */
    TransportationType(String label) {
        this.label = label;
    }

    /**
     * Returns the label associated with the transportation type.
     *
     * @return The label of the transportation type.
     */
    public String getLabel() {
        return label;
    }
}
