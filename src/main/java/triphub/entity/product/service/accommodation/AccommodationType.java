package triphub.entity.product.service.accommodation;

public enum AccommodationType {
	Hotel ("Hotel"),
	BnB("BnB"), 
	PrivateAccom ("Private Accommodation"), 
	Cottage ("Cottage"), 
	Camping ("Camping");

	private String label;

	AccommodationType(String label) {
	        this.label = label;
	    }

	public String getLabel() {
		return label;
	}

}
