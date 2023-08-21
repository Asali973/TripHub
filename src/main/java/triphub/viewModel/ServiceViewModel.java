package triphub.viewModel;

import triphub.entity.product.Price;
import triphub.entity.service.Accommodation;
import triphub.entity.service.Restaurant;
import triphub.entity.service.Transportation;
import triphub.entity.util.Date;

public class ServiceViewModel {

	private Accommodation accomodation;
	private Restaurant restaurant;
	private Transportation transportation;
	private Price price;
	private boolean availability;
	private Date availableFrom;
	private Date availableTill;
	private Date startDate;
	private Date endDate;
	
	
	public Accommodation getAccomodation() {
		return accomodation;
	}
	public void setAccomodation(Accommodation accomodation) {
		this.accomodation = accomodation;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public Transportation getTransportation() {
		return transportation;
	}
	public void setTransportation(Transportation transportation) {
		this.transportation = transportation;
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
	public Date getAvailableFrom() {
		return availableFrom;
	}
	public void setAvailableFrom(Date availableFrom) {
		this.availableFrom = availableFrom;
	}
	public Date getAvailableTill() {
		return availableTill;
	}
	public void setAvailableTill(Date availableTill) {
		this.availableTill = availableTill;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
