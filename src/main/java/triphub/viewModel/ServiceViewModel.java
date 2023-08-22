package triphub.viewModel;

import triphub.entity.product.Price;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.entity.util.Calendar;

public class ServiceViewModel {

	private Accommodation accomodation;
	private Restaurant restaurant;
	private Transportation transportation;
	private Price price;
	private boolean availability;
	private Calendar availableFrom;
	private Calendar availableTill;
	private Calendar startDate;
	private Calendar endDate;
	
	
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
	public Calendar getAvailableFrom() {
		return availableFrom;
	}
	public void setAvailableFrom(Calendar availableFrom) {
		this.availableFrom = availableFrom;
	}
	public Calendar getAvailableTill() {
		return availableTill;
	}
	public void setAvailableTill(Calendar availableTill) {
		this.availableTill = availableTill;
	}
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	
	
}
