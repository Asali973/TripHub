package triphub.viewModel;

import triphub.entity.product.service.accommodation.AccommodationType;
import triphub.entity.util.Address;

public class AccommodationViewModel {
	
	private String nameAccommodation;
	private Address addressAccommodation;
	private AccommodationType accommodationType;
	
	private String num;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
	

	
	public String getNameAccommodation() {
		return nameAccommodation;
	}
	public void setNameAccommodation(String nameAccommodation) {
		this.nameAccommodation = nameAccommodation;
	}
	
	public Address getAddressAccommodation() {
		return addressAccommodation;
	}
	public void setAddressAccommodation(Address addressAccommodation) {
		this.addressAccommodation = addressAccommodation;
	}
	public AccommodationType getAccommodationType() {
		return accommodationType;
	}
	public void setAccommodationType(AccommodationType accommodationType) {
		this.accommodationType = accommodationType;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
	
	
}


