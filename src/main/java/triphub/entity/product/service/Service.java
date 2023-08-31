package triphub.entity.product.service;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import triphub.entity.product.Price;
import triphub.viewModel.SubServicesViewModel;

/**
 * This class represents a service offered within the TripHub application. A
 * service can have various attributes such as its type, price, availability,
 * and the date range it is available for. It is associated with a specific
 * price and can be categorized into different types. The class provides methods
 * to create, update, and initialize a service view model.
 */
@Entity
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** The type of the service. */
	@Enumerated(EnumType.STRING)
	private ServiceType type;

	/** The price associated with the service. */
	@OneToOne(cascade = CascadeType.ALL)
	private Price price;

	/** Indicates whether the service is currently available. */
	private boolean availability;

	/** The starting date from which the service is available. */
	@JoinColumn(name = "availableFrom_id")
	@Temporal(TemporalType.DATE)
	private Date availableFrom;

	/** The ending date till which the service is available. */
	@JoinColumn(name = "tillDate_id")
	@Temporal(TemporalType.DATE)
	private Date availableTill;

	/**
	 * Creates a new Service object based on the provided SubServicesViewModel.
	 *
	 * @param form The SubServicesViewModel containing information for the new
	 *             service.
	 * @return A new Service object populated with data from the view model.
	 */
	public static Service createServiceFromViewModel(SubServicesViewModel form) {
		Service service = new Service();
		service.setPrice(form.getPrice());
		service.setAvailability(form.isAvailability());
		service.setAvailableFrom(form.getAvailableFrom());
		service.setAvailableTill(form.getAvailableTill());
		return service;
	}

	/**
	 * Updates the service's attributes based on the provided SubServicesViewModel.
	 *
	 * @param form The SubServicesViewModel containing updated information for the
	 *             service.
	 */
	public void updateServiceFromViewModel(SubServicesViewModel form) {
		this.setPrice(form.getPrice());
		this.setAvailability(form.isAvailability());
		this.setAvailableFrom(form.getAvailableFrom());
		this.setAvailableTill(form.getAvailableTill());
	}

	/**
	 * Initializes a SubServicesViewModel with data from this service.
	 *
	 * @param form The SubServicesViewModel to be initialized with this service's
	 *             data.
	 */
	public void initServiceViewModel(SubServicesViewModel form) {
		form.setId(this.getId());
		this.getPrice().initPriceViewModel(form);
		form.setAvailability(this.isAvailability());
		form.setAvailableFrom(this.getAvailableFrom());
		form.setAvailableTill(this.getAvailableTill());
	}

	// Getters and Setters for the class attributes

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

	public ServiceType getType() {
		return type;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}

	/**
	 * Returns a string representation of the Service object.
	 *
	 * @return A string containing the service's attributes.
	 */
	@Override
	public String toString() {
		return "Service [id=" + id + ", type=" + type + ", price=" + price + ", availability=" + availability
				+ ", availableFrom=" + availableFrom + ", availableTill=" + availableTill + "]";
	}
}
