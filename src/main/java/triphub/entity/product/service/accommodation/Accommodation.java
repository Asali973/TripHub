package triphub.entity.product.service.accommodation;

import javax.persistence.*;

import triphub.entity.util.Address;

@Entity
public class Accommodation {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	
	@Enumerated(EnumType.STRING)
	private AccommodationType accommodation;

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
	
	
	
}
