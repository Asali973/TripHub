package triphub.entity.subservices;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import triphub.entity.product.service.Service;
import triphub.entity.user.Organizer;
import triphub.entity.user.Provider;
import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.TourPackageFormViewModel;

/**
 * Represents an establishment that prepares and serves food and drinks to
 * customers. This entity contains details of the restaurant, related services,
 * and its visual representation.
 */
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

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) // image can be null
	private Picture picture;

	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;

	@ManyToOne
	@JoinColumn(name = "organizer_id")
	private Organizer organizer;

	public Restaurant() {
	}

	public Restaurant(String name, Address address, String description) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
	}

	/**
	 * Converts the provided ViewModel into a Restaurant entity.
	 *
	 * @param restaurantvm ViewModel representing the restaurant details.
	 * @return A fully constructed Restaurant entity.
	 */
	public static Restaurant createAccommodationFromViewModel(SubServicesViewModel restaurantvm) {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(restaurantvm.getId());
		restaurant.setName(restaurantvm.getName());
		restaurant.setAddress(restaurantvm.getAddress());
		restaurant.setDescription(restaurantvm.getDescription());
		restaurant.setService(restaurantvm.getService());

		Picture picture = new Picture();
		picture.setLink(restaurantvm.getLink());
		restaurant.setPicture(picture);

		return restaurant;
	}

	/**
	 * Updates the current Restaurant entity based on the provided ViewModel.
	 *
	 * @param restaurantvm ViewModel representing the updated restaurant details.
	 */
	public void updateRestaurantViewModel(SubServicesViewModel restaurantvm) {
		this.setId(restaurantvm.getId());
		this.setName(restaurantvm.getName());
		this.getAddress().updateAddressFromViewModel(restaurantvm);
		this.setDescription(restaurantvm.getDescription());

		Picture picture = new Picture();
		picture.setLink(restaurantvm.getLink());
		this.setPicture(picture);

	}

	/**
	 * Initializes a ViewModel with the details from the current Restaurant entity.
	 *
	 * @return A ViewModel populated with the restaurant's details.
	 */
	public SubServicesViewModel initRestaurantViewModel() {
		SubServicesViewModel restaurantvm = new SubServicesViewModel();

		restaurantvm.setId(this.getId());
		restaurantvm.setName(this.getName());
		restaurantvm.setAddress(this.getAddress());
		restaurantvm.setDescription(this.getDescription());
		this.getAddress().initAddressViewModel(restaurantvm);
		this.getService().initServiceViewModel(restaurantvm);

		if (this.getPicture() != null) {
			restaurantvm.setLink(this.getPicture().getLink());
		}

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

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Organizer getOrganizer() {
		return organizer;
	}

	public void setOrganizer(Organizer organizer) {
		this.organizer = organizer;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

}
