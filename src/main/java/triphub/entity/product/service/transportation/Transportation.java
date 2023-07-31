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

@Entity
public class Transportation {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Address departure;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address arrival;
	
	@Enumerated(EnumType.STRING)
	private TransportationType transportation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
