package triphub.viewModel;

import java.io.Serializable;


public class TourPackageFormViewModel implements Serializable{

	private static final long serialVersionUID = 1L;
	// TourPackage properties
    private String name;

    // Price properties
    private double amount;
    private String currency;

    // Destination properties
    private String cityName;
    private String state;
    private String country;

    // Theme properties
    private String themeName;   

    // Image properties
//    private List<Image> imagelinks;
    
	public TourPackageFormViewModel() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

//	public List<Image> getImagelinks() {
//		return imagelinks;
//	}
//
//	public void setImagelinks(List<Image> imagelinks) {
//		this.imagelinks = imagelinks;
//	}




	
	
}