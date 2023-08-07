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
//	private Restaurant restuarant;
//	private Accommodation accomodation;
//	private Transportation transportation;
//	private Price price;
//	private boolean availability;
//	private Date availableFrom;
//	private Date availableTill;
//	private Date startDate;
//	private Date endDate;
//	
//	
//	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	
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
