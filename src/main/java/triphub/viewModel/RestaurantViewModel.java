package triphub.viewModel;

import java.io.Serializable;

import triphub.entity.util.Address;

public class RestaurantViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nameRestaurant;
	private Address addressRestaurant;
	private String description;

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

	public void setNameRestaurant(String nameRestaurant) {
		this.nameRestaurant = nameRestaurant;
	}

	public Address getAddressRestaurant() {
		return addressRestaurant;
	}

	public void setAddressRestaurant(Address addressRestaurant) {
		this.addressRestaurant = addressRestaurant;
	}

}