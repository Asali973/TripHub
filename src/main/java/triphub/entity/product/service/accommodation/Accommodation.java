package triphub.entity.product.service.accommodation;

import javax.persistence.*;

import triphub.entity.util.Address;
import triphub.entity.util.Picture;

@Entity
public class Accommodation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	private Address addresAccommodation;

	@Enumerated(EnumType.STRING)
	private AccommodationType accommodationType;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Picture picture;
	
	private String description;

	public Accommodation() {
	}
		

	public Accommodation(String name, Address addresAccommodation, AccommodationType accommodationType, Picture picture,
			String description) {
		
		this.name = name;
		this.addresAccommodation = addresAccommodation;
		this.accommodationType = accommodationType;
		this.picture = picture;
		this.description = description;
	}







	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddresAccommodation() {
		return addresAccommodation;
	}

	public void setAddresAccommodation(Address addresAccommodation) {
		this.addresAccommodation = addresAccommodation;
	}

	public AccommodationType getAccommodationType() {
		return accommodationType;
	}

	public void setAccommodationType(AccommodationType accommodationType) {
		this.accommodationType = accommodationType;
	}

}
