package triphub.entity.product;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import triphub.viewModel.TourPackageFormViewModel;

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

	public Destination() {}
	public Destination(String cityName, String state, String country) {
		
		this.cityName = cityName;
		this.state = state;
		this.country = country;
	}
	
	public static Destination createDestinationFromViewModel(TourPackageFormViewModel tourPackageVm) {
		Destination destination = new Destination();
		destination.setCityName(tourPackageVm.getCityName());
		destination.setState(tourPackageVm.getState());
		destination.setCountry(tourPackageVm.getCountry());
		return destination;
	}
	public void  updateDestinationFromViewModel(TourPackageFormViewModel tourPackageVm) {
		this.setCityName(tourPackageVm.getCityName());
		this.setState(tourPackageVm.getState());
		this.setCountry(tourPackageVm.getCountry());
	}
	public void  initDestinationViewModel(TourPackageFormViewModel tourPackageVm) {
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
