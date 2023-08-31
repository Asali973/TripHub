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
import triphub.entity.subservices.*;
import triphub.entity.user.User;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Represents an item in the shopping cart. Each item may represent a tour package,
 * accommodation, restaurant, or transportation service.
 */

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

	/**
	 * Updates the total price of the cart item based on the associated service and quantity.
	 */
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
	
	/**
	 * @return the total price of the cart item based on the associated service and quantity.
	 */
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

		return BigDecimal.ZERO; // Default, though it might be better to handle this case more gracefully.
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public Transportation getTransportation() {
		return transportation;
	}
	public TourPackage getTourPackage() {
		return tourPackage;
	}
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Sets the quantity of the cart item and updates its total price.
	 *
	 * @param quantity the new quantity for the cart item.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
		updateTotalPrice();
	}
	
	/**
	 * Sets the accommodation for the cart item and updates its total price.
	 *
	 * @param accommodation the accommodation service associated with the cart item.
	 */
	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
		updateTotalPrice();
	}

	/**
	 * Sets the restaurant for the cart item and updates its total price.
	 *
	 * @param restaurant the restaurant service associated with the cart item.
	 */
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
		updateTotalPrice();
	}

	/**
	 * Sets the transportation for the cart item and updates its total price.
	 *
	 * @param transportation the transportation service associated with the cart item.
	 */
	public void setTransportation(Transportation transportation) {
		this.transportation = transportation;
		updateTotalPrice();
	}

	/**
	 * Sets the tour package for the cart item and updates its total price.
	 *
	 * @param tourPackage the tour package associated with the cart item.
	 */
	public void setTourPackage(TourPackage tourPackage) {
		this.tourPackage = tourPackage;
		updateTotalPrice();
	}

}
