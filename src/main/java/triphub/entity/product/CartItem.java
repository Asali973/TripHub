package triphub.entity.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import triphub.entity.product.service.Service;


import triphub.entity.subservices.*;


import triphub.entity.user.User;
import triphub.viewModel.CartViewModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class CartItem implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private TourPackage tourPackage;

	@ManyToOne
	@JoinColumn(name = "accommodation_id")
	private Accommodation accommodation;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@ManyToOne
	@JoinColumn(name = "transportation_id")
	private Transportation transportation;

	private int quantity;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfOrder;

	@Transient
	private int newQuantity = quantity;

	private BigDecimal totalPrice;

	public void updateFromCartItemViewModel(CartViewModel viewModel) {
		this.setQuantity(viewModel.getQuantity());

	}
			
	 public void setQuantity(int quantity) {
	        this.quantity = quantity;
	        updateTotalPrice();
	    }
   	
	 private void updateTotalPrice() {
		    if (tourPackage != null) {
		        this.totalPrice = tourPackage.getPrice().getAmount().multiply(new BigDecimal(quantity));
		    } else if (accommodation != null) {
		        this.totalPrice = accommodation.getService().getPrice().getAmount().multiply(new BigDecimal(quantity));
		    } else if (restaurant != null) {
		        this.totalPrice = restaurant.getService().getPrice().getAmount().multiply(new BigDecimal(quantity));
		    } else if (transportation != null) {
		        this.totalPrice = transportation.getService().getPrice().getAmount().multiply(new BigDecimal(quantity));
		    }
		}

	 @Transient
	    public BigDecimal getTotalPrice() {
	        if (tourPackage != null) {
	            return tourPackage.getPrice().getAmount().multiply(new BigDecimal(quantity));
	        }
	        if (accommodation != null) {
	            return accommodation.getService().getPrice().getAmount().multiply(new BigDecimal(quantity));
	        }
	        if (transportation != null) {
	            return transportation.getService().getPrice().getAmount().multiply(new BigDecimal(quantity));
	        }
	        if (restaurant != null) {
	            return restaurant.getService().getPrice().getAmount().multiply(new BigDecimal(quantity));
	        }
	        
	        return BigDecimal.ZERO;  // Default, though it might be better to handle this case more gracefully.
	    }
	 
	 public void setTourPackage(TourPackage tourPackage) {
	        this.tourPackage = tourPackage;
	        updateTotalPrice();
	    }
	 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TourPackage getTourPackage() {
		return tourPackage;
	}
	
	
	public Date getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public int getNewQuantity() {
		return newQuantity;
	}

	public void setNewQuantity(int newQuantity) {
		this.newQuantity = newQuantity;
	}

	

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Accommodation getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
		updateTotalPrice();
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
		updateTotalPrice();
	}

	public Transportation getTransportation() {
		return transportation;
	}

	public void setTransportation(Transportation transportation) {
		this.transportation = transportation;
		updateTotalPrice();
	}

	

}
