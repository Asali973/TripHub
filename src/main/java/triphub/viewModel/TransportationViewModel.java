package triphub.viewModel;

import java.io.Serializable;

import triphub.entity.product.service.transportation.TransportationType;
import triphub.entity.util.Address;

public class TransportationViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nameTransportation;
	private Address departure;
	private Address arrival;
	
	private TransportationType transportation;

	public String getNameTransportation() {
		return nameTransportation;
	}

	public TransportationViewModel() {

	}

	public void setNameTransportation(String nameTransportation) {
		this.nameTransportation = nameTransportation;
	}

	public Address getDeparture() {
		return departure;
	}

	public void setDeparture(Address departure) {
		this.departure = departure;
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


}
