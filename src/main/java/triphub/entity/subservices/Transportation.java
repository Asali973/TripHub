package triphub.entity.subservices;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

/**
 * Represents a mode of transportation. This entity contains details of the
 * transportation, including the departure and arrival addresses, related
 * services, and a visual representation.
 */

@Entity
public class Transportation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Service service;

	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "departure")
	private Address departure;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "arrival")
	private Address arrival;

	@Enumerated(EnumType.STRING)
	private TransportationType transportationType;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) // image can be null
	private Picture picture;

	private String description;

	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;

	@ManyToOne
	@JoinColumn(name = "organizer_id")
	private Organizer organizer;

	public Transportation() {

	}

	public Transportation(String name, Address departure, Address arrival, TransportationType transportationType,
			Picture picture, String description, Service service) {
		this.name = name;
		this.departure = departure;
		this.arrival = arrival;
		this.transportationType = transportationType;
		this.picture = picture;
		this.description = description;
		this.service = service;
	}

	/**
	 * Converts the provided ViewModel into a Transportation entity.
	 *
	 * @param transportationvm ViewModel representing the transportation details.
	 * @return A fully constructed Transportation entity.
	 */
	public static Transportation createTransportationFromViewModel(SubServicesViewModel transportationvm) {
		Transportation transportation = new Transportation();
		transportation.setId(transportationvm.getId());
		transportation.setName(transportationvm.getName());
		transportation.setDeparture(transportationvm.getDeparture());
		transportation.setArrival(transportationvm.getArrival());
		transportation.setTransportationType(transportationvm.getTransportationType());
		transportation.setDescription(transportationvm.getDescription());
		transportation.setService(transportationvm.getService());

		Picture picture = new Picture();
		picture.setLink(transportationvm.getLink());
		transportation.setPicture(picture);

		return transportation;
	}

	/**
	 * Updates the current Transportation entity based on the provided ViewModel.
	 *
	 * @param transportationvm ViewModel representing the updated transportation
	 *                         details.
	 */
	public void updateTransportationViewModel(SubServicesViewModel transportationvm) {
		this.setId(transportationvm.getId());
		this.setName(transportationvm.getName());
		this.setDeparture(transportationvm.getDeparture());
		this.setArrival(transportationvm.getArrival());
		this.setDescription(transportationvm.getDescription());

		Picture picture = new Picture();
		picture.setLink(transportationvm.getLink());
		this.setPicture(picture);

	}

	/**
	 * Initializes a ViewModel with the details from the current Transportation
	 * entity.
	 *
	 * @return A ViewModel populated with the transportation's details.
	 */
	public SubServicesViewModel initTransportationViewModel() {
		SubServicesViewModel transportationvm = new SubServicesViewModel();
		transportationvm.setId(this.getId());
		transportationvm.setName(this.getName());
		transportationvm.setDeparture(this.getDeparture());
		transportationvm.setArrival(this.getArrival());
		transportationvm.setDescription(this.getDescription());
		this.getDeparture().initAddressViewModel(transportationvm);
		this.getArrival().initAddressViewModel(transportationvm);

		if (this.getPicture() != null) {
			transportationvm.setLink(this.getPicture().getLink());
		}

		return transportationvm;
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

	public TransportationType getTransportationType() {
		return transportationType;
	}

	public void setTransportationType(TransportationType transportationType) {
		this.transportationType = transportationType;
	}

	public String getName() {
		return name;
	}

	public void setName(String nameTransportation) {
		this.name = nameTransportation;
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

}
