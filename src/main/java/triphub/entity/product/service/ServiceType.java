package triphub.entity.product.service;

public enum ServiceType {
	ACCOMMODATION,
	RESTAURANT,
	TRANSPORTATION;

	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
