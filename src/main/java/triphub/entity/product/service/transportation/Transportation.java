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
