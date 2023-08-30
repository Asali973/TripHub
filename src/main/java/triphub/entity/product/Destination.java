package triphub.entity.product;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import triphub.viewModel.TourPackageFormViewModel;

/**
 * Represents a travel destination, defined by its city, state, and country.
 * Each destination may have multiple tour packages associated with it.
 * <p>
 * This entity also provides utility methods to interact with a view model that
 * represents the information about the destination in a form-friendly manner.
 * </p>
 */
@Entity
public class Destination {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cityName;
	private String state;
	private String country;

	@OneToMany(mappedBy = "destination")
	private List<TourPackage> tourPackages;

	/**
	 * Default constructor.
	 */
	public Destination() {
	}

	/**
	 * Parameterized constructor to initialize a destination.
	 *
	 * @param cityName The name of the city.
	 * @param state    The state in which the city is located.
	 * @param country  The country in which the city is located.
	 */
	public Destination(String cityName, String state, String country) {

		this.cityName = cityName;
		this.state = state;
		this.country = country;
	}

	/**
	 * Converts the provided tour package view model into a Destination entity.
	 * 
	 * @param tourPackageVm The view model representing a tour package.
	 * @return A new Destination entity populated from the view model.
	 */
	public static Destination createDestinationFromViewModel(TourPackageFormViewModel tourPackageVm) {
		Destination destination = new Destination();
		destination.setCityName(tourPackageVm.getCityName());
		destination.setState(tourPackageVm.getState());
		destination.setCountry(tourPackageVm.getCountry());
		return destination;
	}

	/**
	 * Updates the current Destination entity using the provided tour package view
	 * model.
	 * 
	 * @param tourPackageVm The view model representing a tour package.
	 */
	public void updateDestinationFromViewModel(TourPackageFormViewModel tourPackageVm) {
		this.setCityName(tourPackageVm.getCityName());
		this.setState(tourPackageVm.getState());
		this.setCountry(tourPackageVm.getCountry());
	}

	/**
	 * Initializes the provided tour package view model with details from the
	 * current Destination entity.
	 * 
	 * @param tourPackageVm The view model to be initialized.
	 */
	public void initDestinationViewModel(TourPackageFormViewModel tourPackageVm) {
		tourPackageVm.setCityName(this.getCityName());
		tourPackageVm.setState(this.getState());
		tourPackageVm.setCountry(this.getCountry());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public List<TourPackage> getTourPackages() {
		return tourPackages;
	}

	public void setTourPackages(List<TourPackage> tourPackages) {
		this.tourPackages = tourPackages;
	}

}
