package triphub.entity.product.service.restaurant;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import triphub.entity.util.Address;

@Entity
public class Restaurant {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nameRestaurant;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address addressRestaurant;
	
	private String description;

	
	public Restaurant () {
	}
	
	public Restaurant(String nameRestaurant, Address addressRestaurant, String description) {
		super();
		this.nameRestaurant = nameRestaurant;
		this.addressRestaurant = addressRestaurant;
		this.description = description;
	}

	
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

}
