package triphub.entity.product;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.TourPackageFormViewModel;

/**
 * Represents the price of a product, specifically defined by its amount and
 * currency. The Price entity is associated with TourPackage and SubServices in
 * a one-to-one relationship.
 * <p>
 * This entity also provides utility methods to interact with two different view
 * models: one for the TourPackage and another for the SubServices.
 * </p>
 */
@Entity
public class Price {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal amount;
	private String currency;

	@OneToOne(mappedBy = "price")
	private TourPackage tourPackage;

	/**
	 * Default constructor.
	 */
	public Price() {
	}
	
	 /**
     * Parameterized constructor to initialize a price.
     *
     * @param amount The numeric value of the price.
     * @param currency The currency in which the price is denoted (e.g., USD, EUR).
     */
	public Price(BigDecimal amount, String currency) {
		super();
		this.amount = amount;
		this.currency = currency;
	}
	
	/**
     * Converts the provided tour package view model into a Price entity.
     * 
     * @param tourPackageVm The view model representing a tour package.
     * @return A new Price entity populated from the tour package view model.
     */
	public static Price createPriceFromViewModel(TourPackageFormViewModel tourPackageVm) {
		Price price = new Price();
		price.setAmount(tourPackageVm.getAmount());
		price.setCurrency(tourPackageVm.getCurrency());
		return price;
	}
	
	/**
	 * Updates the current Price entity's properties using data from the provided tour package view model.
	 * 
	 * @param tourPackageVm The view model representing a tour package.
	 */
	public void updatePriceFromViewModel(TourPackageFormViewModel tourPackageVm) {
		this.setAmount(tourPackageVm.getAmount());
		this.setCurrency(tourPackageVm.getCurrency());
	}
	
	/**
	 * Populates the provided tour package view model with data from the current Price entity.
	 * 
	 * @param tourPackageVm The view model to be populated.
	 */
	public void initPriceViewModel(TourPackageFormViewModel tourPackageVm) {
		tourPackageVm.setAmount(this.getAmount());
		tourPackageVm.setCurrency(this.getCurrency());
	}
	
	/**
	 * Creates and returns a new Price entity using data from the provided sub-services view model.
	 * 
	 * @param serviceVm The view model representing a sub-service.
	 * @return A new Price entity populated from the sub-service view model.
	 */
	public static Price createPriceFromViewModel(SubServicesViewModel serviceVm) {
		Price price = new Price();
		price.setAmount(serviceVm.getPrice().getAmount());
		price.setCurrency(serviceVm.getPrice().getCurrency());
		return price;
	}
	
	/**
	 * Updates the current Price entity's properties using data from the provided sub-services view model.
	 * 
	 * @param servicevm The view model representing a sub-service.
	 */
	public void updatePriceFromViewModel(SubServicesViewModel servicevm) {
		this.setAmount(servicevm.getPrice().getAmount());
		this.setCurrency(servicevm.getPrice().getCurrency());
	}
	
	/**
	 * Populates the provided sub-services view model with data from the current Price entity.
	 * 
	 * @param servicevm The view model to be populated.
	 */
	public void initPriceViewModel(SubServicesViewModel servicevm) {
		servicevm.getPrice().setAmount(this.getAmount());
		servicevm.getPrice().setCurrency(this.getCurrency());
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Price [id=" + id + ", amount=" + amount + ", currency=" + currency + ", tourPackage=" + tourPackage
				+ "]";
	}

}
