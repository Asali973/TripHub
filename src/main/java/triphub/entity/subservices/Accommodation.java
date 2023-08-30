package triphub.entity.subservices;

import javax.persistence.*;

import triphub.entity.product.service.Service;
import triphub.entity.user.Organizer;
import triphub.entity.user.Provider;
import triphub.entity.user.User;
import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.helpers.PasswordUtils;
import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.UserViewModel;

/**
 * Represents a lodging establishment where travelers can pay for shelter and
 * rest. It could be in various forms mentioned in AccommodationType enum class.
 * This entity contains details of the accommodation, related services, and its
 * visual representation.
 */
@Entity
public class Accommodation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	@Enumerated(EnumType.STRING)
	private AccommodationType accommodationType;

	@OneToOne(cascade = CascadeType.ALL)
	private Service service;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) // image can be null
	private Picture picture;

	private String description;

	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;

	@ManyToOne
	@JoinColumn(name = "organizer_id")
	private Organizer organizer;

	public Accommodation() {
	}

	public Accommodation(String name, Address address, AccommodationType accommodationType, Picture picture,
			String description, Service service) {
		this.name = name;
		this.address = address;
		this.accommodationType = accommodationType;
		this.picture = picture;
		this.description = description;
		this.service = service;
	}

	/**
	 * Converts the provided ViewModel into an Accommodation entity.
	 *
	 * @param accommodationvm ViewModel representing the accommodation details.
	 * @return A fully constructed Accommodation entity.
	 */
	public static Accommodation createAccommodationFromViewModel(SubServicesViewModel accommodationvm) {
		Accommodation accommodation = new Accommodation();
		accommodation.setId(accommodationvm.getId());
		accommodation.setName(accommodationvm.getName());
		accommodation.setAddress(accommodationvm.getAddress());
		accommodation.setAccommodationType(accommodationvm.getAccommodationType());
		accommodation.setDescription(accommodationvm.getDescription());
		accommodation.setService(accommodationvm.getService());

		Picture picture = new Picture();
		picture.setLink(accommodationvm.getLink());
		accommodation.setPicture(picture);

		return accommodation;
	}

	/**
	 * Updates the current Accommodation entity based on the provided ViewModel.
	 *
	 * @param accommodationvm ViewModel representing the updated accommodation
	 *                        details.
	 */
	public void updateAccommodationViewModel(SubServicesViewModel accommodationvm) {
		this.setId(accommodationvm.getId());
		this.setName(accommodationvm.getName());
		this.setAddress(accommodationvm.getAddress());
		this.setAccommodationType(accommodationvm.getAccommodationType());
		this.setDescription(accommodationvm.getDescription());
		this.getService().updateServiceFromViewModel(accommodationvm);
		Picture picture = new Picture();
		picture.setLink(accommodationvm.getLink());
		this.setPicture(picture);

	}

	/**
	 * Initializes a ViewModel with the details from the current Accommodation
	 * entity.
	 *
	 * @return A ViewModel populated with the accommodation's details.
	 */
	public SubServicesViewModel initAccommodationViewModel() {
		SubServicesViewModel accommodationvm = new SubServicesViewModel();
		accommodationvm.setId(this.getId());
		accommodationvm.setName(this.getName());
		accommodationvm.setAddress(this.getAddress());
		accommodationvm.setAccommodationType(this.getAccommodationType());
		accommodationvm.setDescription(this.getDescription());
		this.getService().initServiceViewModel(accommodationvm);

		if (this.getPicture() != null) {
			accommodationvm.setLink(this.getPicture().getLink());
		}

		return accommodationvm;
	}

	// getters - setters

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

	public AccommodationType getAccommodationType() {
		return accommodationType;
	}

	public void setAccommodationType(AccommodationType accommodationType) {
		this.accommodationType = accommodationType;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
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

	@Override
	public String toString() {
		return "Accommodation [id=" + id + ", name=" + name + ", address=" + address + ", accommodationType="
				+ accommodationType + ", service=" + service + ", picture=" + picture + ", description=" + description
				+ "]";
	}

}