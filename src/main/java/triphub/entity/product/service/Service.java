package triphub.entity.product.service;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import triphub.entity.product.Price;
import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.restaurant.Restaurant;
import triphub.entity.product.service.transportation.Transportation;


import triphub.entity.util.Date;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    @OneToOne(cascade = CascadeType.ALL)
    private Accommodation accommodation;

    @OneToOne(cascade = CascadeType.ALL)
    private Transportation transportation;

    @OneToOne(cascade = CascadeType.ALL)
    private Price price;

    private boolean availability;
    @OneToOne
    @JoinColumn(name = "fromDate_id")
    private Date availableFrom;
    @OneToOne
    @JoinColumn(name = "tillDate_id")
    private Date availableTill;
  

  
			
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public Accommodation getAccommodation() {
		return accommodation;
	}
	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
	}
	public Transportation getTransportation() {
		return transportation;
	}
	public void setTransportation(Transportation transportation) {
		this.transportation = transportation;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	
}
