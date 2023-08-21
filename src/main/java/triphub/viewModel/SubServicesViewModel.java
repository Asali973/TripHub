package triphub.viewModel;

import java.io.Serializable;
import java.time.LocalDate;

import triphub.entity.product.Price;
import triphub.entity.service.AccommodationType;
import triphub.entity.service.TransportationType;

import triphub.entity.util.Address;
import triphub.entity.util.Date;
import triphub.entity.util.Picture;
import triphub.entity.util.PictureType;

public class SubServicesViewModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	



	private Long id;
	
	private String name;
	
	private boolean availability;
	private LocalDate availableFrom;
	private LocalDate availableTill;
	

	private Date startDate;
	private Date endDate;

	private AccommodationType accommodationType;
	private TransportationType transportationType;

	
	private String description;

	private Address address = new Address();
	private Price price= new Price();

	// Image attributes
	private int size;
	private int weight;
	private String link;
	private PictureType type;
	private String altText;

	// Getters & Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccommodationType getAccommodationType() {
		return accommodationType;
	}

	public void setAccommodationType(AccommodationType accommodationType) {
		this.accommodationType = accommodationType;
	}

	public TransportationType getTransportationType() {
		return transportationType;
	}

	public void setTransportationType(TransportationType transportationType) {
		this.transportationType = transportationType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public PictureType getType() {
		return type;
	}

	public void setType(PictureType type) {
		this.type = type;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
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

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public LocalDate getAvailableFrom() {
		return availableFrom;
	}

	public void setAvailableFrom(LocalDate availableFrom) {
		this.availableFrom = availableFrom;
	}

	public LocalDate getAvailableTill() {
		return availableTill;
	}

	public void setAvailableTill(LocalDate availableTill) {
		this.availableTill = availableTill;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	
	
	

}
