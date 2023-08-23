package triphub.entity.subservices;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import triphub.entity.product.service.Service;
import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;

@Entity
public class Transportation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Service service;

	private String name;

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

	public Transportation(String name, Address departure, Address arrival, TransportationType transportation,
			Picture picture, String description, Service service) {
		this.name = name;
		this.departure = departure;
		this.arrival = arrival;
		this.transportation = transportation;
		this.picture = picture;
		this.description = description;
		this.service = service;
	}
	
	public static Transportation createTransportationFromViewModel(SubServicesViewModel transportationvm) {
		Transportation transportation = new Transportation();
		transportation.setId(transportationvm.getId());
		transportation.setName(transportationvm.getName());
		transportation.setDeparture(transportationvm.getDeparture());
		transportation.setArrival(transportationvm.getArrival());
		transportation.setTransportation(transportationvm.getTransportationType());
		transportation.setDescription(transportationvm.getDescription());
		transportation.setService(transportationvm.getService());
		return transportation;
	}

	public void updateTransportationViewModel(SubServicesViewModel transportationvm) {
		this.setId(transportationvm.getId());
		this.setName(transportationvm.getName());
		this.setDeparture(transportationvm.getDeparture());
		this.setArrival(transportationvm.getArrival());
		this.setDescription(transportationvm.getDescription());
		// need to add picture soon
	}

	public SubServicesViewModel initTransportationViewModel() {
		SubServicesViewModel transportationvm = new SubServicesViewModel();
		transportationvm.setId(this.getId());
		transportationvm.setName(this.getName());
		transportationvm.setDeparture(this.getDeparture());
		transportationvm.setArrival(this.getArrival());
		transportationvm.setDescription(this.getDescription());
		this.getDeparture().initAddressViewModel(transportationvm);
		this.getArrival().initAddressViewModel(transportationvm);
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

	public TransportationType getTransportation() {
		return transportation;
	}

	public void setTransportation(TransportationType transportation) {
		this.transportation = transportation;
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

}
