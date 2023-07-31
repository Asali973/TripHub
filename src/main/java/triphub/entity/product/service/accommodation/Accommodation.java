package triphub.entity.product.service.accommodation;

import javax.persistence.*;

import triphub.entity.util.Address;

@Entity
public class Accommodation {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private String nameAccomodation;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	
	@Enumerated(EnumType.STRING)
	private AccommodationType accommodation;

	public Accommodation() {}
	
	public Accommodation(String nameAccomodation, Address address, AccommodationType accommodation) {
		super();
		this.nameAccomodation = nameAccomodation;
		this.address = address;
		this.accommodation = accommodation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public AccommodationType getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(AccommodationType accommodation) {
		this.accommodation = accommodation;
	}

	public String getNameAccomodation() {
		return nameAccomodation;
	}

	public void setNameAccomodation(String nameAccomodation) {
		this.nameAccomodation = nameAccomodation;
	}
	
	
	
}
