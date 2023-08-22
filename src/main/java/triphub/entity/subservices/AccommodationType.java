package triphub.entity.subservices;

public enum AccommodationType {
	Hotel ("Hotel"),
	BnB("BnB"), 
	GuestRoom ("Guestroom"), 
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
