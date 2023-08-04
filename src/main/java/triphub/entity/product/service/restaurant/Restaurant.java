package triphub.entity.product.service.restaurant;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import triphub.entity.util.Address;
import triphub.entity.util.Picture;

@Entity
public class Restaurant {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nameRestaurant;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address addressRestaurant;
	
	private String description;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Picture picture;

	
	public Restaurant () {
	}
	
//	public Restaurant(String nameRestaurant, Address addressRestaurant, String description, Picture picture) {
//		super();
//		this.nameRestaurant = nameRestaurant;
//		this.addressRestaurant = addressRestaurant;
//		this.description = description;
//		this.picture = picture;
//	}

	
	// Getters - Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getNameRestaurant() {
		return nameRestaurant;
	}

	public void setNameRestaurant(String name) {
		this.nameRestaurant = name;
	}

	
	public Address getAddressRestaurant() {
		return addressRestaurant;
	}

	public void setAddressRestaurant(Address addressRestaurant) {
		this.addressRestaurant = addressRestaurant;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

}
