package triphub.entity.product.service.accommodation;

import javax.persistence.*;

import triphub.entity.util.Address;

@Entity
public class Accommodation {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	private String nameAccommodation;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	
	@Enumerated(EnumType.STRING)
	private AccommodationType accommodationType;
	

	public Accommodation() {
	}
	
	public Accommodation(String nameAccommodation, Address address, AccommodationType accommodationType) {
		this.nameAccommodation = nameAccommodation;
		this.address = address;
		this.accommodationType = accommodationType;
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
		return accommodationType;
	}

	public void setAccommodation(AccommodationType accommodationType) {
		this.accommodationType = accommodationType;
	}

	public String getNameAccommodation() {
		return nameAccommodation;
	}

	public void setNameAccommodation(String nameAccommodation) {
		this.nameAccommodation = nameAccommodation;
	}

	public AccommodationType getAccommodationType() {
		return accommodationType;
	}

	public void setAccommodationType(AccommodationType accommodationType) {
		this.accommodationType = accommodationType;
	}


	
	
}
