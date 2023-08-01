//package triphub.entity.product.service;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//import triphub.entity.product.Price;
//import triphub.entity.product.service.accommodation.Accommodation;
//import triphub.entity.product.service.restaurant.Restaurant;
//import triphub.entity.product.service.transportation.Transportation;
//
//
//import triphub.entity.util.Date;
//
//@Entity
//public class Service {
//	@Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    private Long id;
//	
//	private Accommodation accommodation;
//	private Restaurant restaurant;
//	private Transportation transportation;
//	private Price price;
//	private boolean availability;
//	private Date availableFrom;
//	private Date availableTill;
//	private Date startDate;
//	private Date endDate;
//	
//	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public Accommodation getAccomodation() {
//		return accomodation;
//	}
//	public void setAccomodation(Accommodation accomodation) {
//		this.accomodation = accomodation;
//	}
//	public Restaurant getRestaurant() {
//		return restaurant;
//	}
//	public void setRestaurant(Restaurant restaurant) {
//		this.restaurant = restaurant;
//	}
//	public Transportation getTransportation() {
//		return transportation;
//	}
//	public void setTransportation(Transportation transportation) {
//		this.transportation = transportation;
//	}
//	public Price getPrice() {
//		return price;
//	}
//	public void setPrice(Price price) {
//		this.price = price;
//	}
//	public boolean isAvailability() {
//		return availability;
//	}
//	public void setAvailability(boolean availability) {
//		this.availability = availability;
//	}
//	public Date getAvailableFrom() {
//		return availableFrom;
//	}
//	public void setAvailableFrom(Date availableFrom) {
//		this.availableFrom = availableFrom;
//	}
//	public Date getAvailableTill() {
//		return availableTill;
//	}
//	public void setAvailableTill(Date availableTill) {
//		this.availableTill = availableTill;
//	}
//	public Date getStartDate() {
//		return startDate;
//	}
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//	public Date getEndDate() {
//		return endDate;
//	}
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}
//	
//}
