package triphub.entity.product.service.transportation;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;

@Entity
public class Transportation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nameTransportation;

	@OneToOne(cascade = CascadeType.ALL)
	private Address departure;

	@OneToOne(cascade = CascadeType.ALL)
	private Address arrival;

	@Enumerated(EnumType.STRING)
	private TransportationType transportation;

	@OneToOne(cascade = CascadeType.ALL)
	private Picture picture;
	
	private String description;

	public Transportation() {

	}

	public Transportation(String nameTransportation, Address departure, Address arrival,
			TransportationType transportation, Picture picture, String description) {
		super();
		this.nameTransportation = nameTransportation;
		this.departure = departure;
		this.arrival = arrival;
		this.transportation = transportation;
		this.picture = picture;
		this.description = description;
	}

	public void updateTransportation(SubServicesViewModel restaurantvm) {
		this.setName(restaurantvm.getName());
		this.setId(restaurantvm.getId());
		this.setDescription(restaurantvm.getDescription());
		// need to add picture soon
	}

	public SubServicesViewModel initRestaurantViewModel() {
		SubServicesViewModel restaurantvm = new SubServicesViewModel();
		Address address = new Address();
		restaurantvm.setId(this.getId());
		restaurantvm.setName(this.getName());
		restaurantvm.setAddress(this.getAddress());
		restaurantvm.setDescription(this.getDescription());
		this.getAddress().initAddressViewModel(restaurantvm);
		return restaurantvm;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDeparture(Address departure) {
		this.departure = departure;
	}

	public Address getDeparture() {
		return departure;
	}

	public Address getArrival() {
		return arrival;
	}

	public void setArrival(Address arrival) {
		this.arrival = arrival;
	}

	public TransportationType getTransportation() {
		return transportation;
	}

	public void setTransportation(TransportationType transportation) {
		this.transportation = transportation;
	}

	public String getNameTransportation() {
		return nameTransportation;
	}

	public void setNameTransportation(String nameTransportation) {
		this.nameTransportation = nameTransportation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
