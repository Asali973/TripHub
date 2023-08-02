package triphub.viewModel;

import triphub.entity.product.service.accommodation.AccommodationType;
import triphub.entity.util.Address;

public class AccommodationViewModel {
	
	private String nameAccommodation;
	private Address address;
	private AccommodationType accommodationType;
	

	
	public String getNameAccommodation() {
		return nameAccommodation;
	}
	public void setNameAccommodation(String nameAccommodation) {
		this.nameAccommodation = nameAccommodation;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public AccommodationType getAccommodationType() {
		return accommodationType;
	}
	public void setAccommodationType(AccommodationType accommodationType) {
		this.accommodationType = accommodationType;
	}
	
	
}


