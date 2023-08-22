package triphub.entity.subservices;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import triphub.entity.product.service.Service;
import triphub.entity.util.Address;
import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.TourPackageFormViewModel;

@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	@OneToOne(cascade = CascadeType.ALL)
	private Service service;

	private String description;

	public Restaurant() {
	}

	public Restaurant(String name, Address address, String description) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
	}

	public static Restaurant createAccommodationFromViewModel(SubServicesViewModel restaurantvm) {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(restaurantvm.getId());
		restaurant.setName(restaurantvm.getName());
		restaurant.setAddress(restaurantvm.getAddress());
		restaurant.setDescription(restaurantvm.getDescription());
		return restaurant;
	}

	public void updateRestaurantViewModel(SubServicesViewModel restaurantvm) {
		this.setId(restaurantvm.getId());
		this.setName(restaurantvm.getName());
		this.getAddress().updateAddressFromViewModel(restaurantvm);
		this.setDescription(restaurantvm.getDescription());
		// need to add picture soon
	}

	public SubServicesViewModel initRestaurantViewModel() {
		SubServicesViewModel restaurantvm = new SubServicesViewModel();
		restaurantvm.setId(this.getId());
		restaurantvm.setName(this.getName());
		restaurantvm.setAddress(this.getAddress());
		restaurantvm.setDescription(this.getDescription());
		this.getAddress().initAddressViewModel(restaurantvm);
		return restaurantvm;
	}

	// Getters - Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}
