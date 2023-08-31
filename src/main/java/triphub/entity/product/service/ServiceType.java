package triphub.entity.product.service;

/**
 * This enum represents the different types of services that can be offered within the TripHub application.
 * Each service type is associated with a label that can be used to provide a more descriptive representation.
 */
public enum ServiceType {
    /** Represents an accommodation service. */
    ACCOMMODATION("Accommodation"),

    /** Represents a restaurant service. */
    RESTAURANT("Restaurant"),

    /** Represents a transportation service. */
    TRANSPORTATION("Transportation");

    private String label;

    /**
     * Constructs a ServiceType enum constant with the provided label.
     *
     * @param label The label associated with the service type.
     */
    ServiceType(String label) {
        this.label = label;
    }

    /**
     * Returns the label associated with the service type.
     *
     * @return The label of the service type.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label associated with the service type.
     *
     * @param label The label to be set.
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
