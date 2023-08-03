package triphub.viewModel;

import triphub.entity.product.service.accommodation.AccommodationType;
import triphub.entity.product.service.transportation.TransportationType;
import triphub.entity.util.PictureType;


public class SubServicesViewModel {
	
	// attributs 
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private AccommodationType accommodationType;
	
	private TransportationType transportationType;
	
	private String description;

	private String num;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    
    private int size;
    private int weight;
    private String link;
    
    private PictureType type;
    
    
    // getters setters
    
    
	public String getName() {
		return name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public PictureType getType() {
		return type;
	}
	public void setType(PictureType type) {
		this.type = type;
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
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
	
   
    
    
}
