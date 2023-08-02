package triphub.viewModel;

import java.io.Serializable;

import triphub.entity.util.Address;

public class RestaurantViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nameRestaurant;
	private Address addressRestaurant;
	private String description;

//	// Address Attributes
//	private String num;
//	private String street;
//	private String city;
//	private String state;
//	private String country;
//	private String zipCode;

	// void Constructor
	public RestaurantViewModel() {
	}

	// Getters - Setters >> Restaurants
	public Address getAddress() {
		return addressRestaurant;
	}

	public void setAddress(Address addressRestaurant) {
		this.addressRestaurant = addressRestaurant;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNameRestaurant() {
		return nameRestaurant;
	}

	public void setNameRestaurant(String nameTransportation) {
		this.nameRestaurant = nameTransportation;
	}

	public Address getAddressRestaurant() {
		return addressRestaurant;
	}

	public void setAddressRestaurant(Address addressRestaurant) {
		this.addressRestaurant = addressRestaurant;
	}

//	// Getters - Setters >> Address
//	public String getNum() {
//		return num;
//	}
//
//	public void setNum(String num) {
//		this.num = num;
//	}
//
//	public String getStreet() {
//		return street;
//	}
//
//	public void setStreet(String street) {
//		this.street = street;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public String getState() {
//		return state;
//	}
//
//	public void setState(String state) {
//		this.state = state;
//	}
//
//	public String getCountry() {
//		return country;
//	}
//
//	public void setCountry(String country) {
//		this.country = country;
//	}
//
//	public String getZipCode() {
//		return zipCode;
//	}
//
//	public void setZipCode(String zipCode) {
//		this.zipCode = zipCode;
//	}

}
