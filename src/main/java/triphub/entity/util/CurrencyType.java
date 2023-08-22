package triphub.entity.util;

public enum CurrencyType {
	EURO ("EUR"),
	USD("USD"), 
	GBP ("GBP"), 
	JPY ("JPY"), 
	CAD ("CAD"),
	AUD ("AUD"),
	CHF ("CHF");

	private String label;

	CurrencyType(String label) {
	        this.label = label;
	    }

	public String getLabel() {
		return label;
	}

}

