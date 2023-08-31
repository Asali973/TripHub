package triphub.viewModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.Part;

import triphub.entity.util.Picture;

public class TourPackageFormViewModel implements Serializable {

	private static final long serialVersionUID = 1L;
	// TourPackage properties
	private Long id;
	private String name;
	private String description;

	// Price properties
	private BigDecimal amount;
	private String currency;

	// Destination properties
	private String cityName;
	private String state;
	private String country;

	// Theme properties
	private String themeName;

	// New fields for price range
	private BigDecimal minPrice = new BigDecimal(100);
	private BigDecimal maxPrice = BigDecimal.ZERO;

	// Image properties
	private String profilePicture;
	private Picture picture;
	private String link;

	// Method to set all the attributes to null/empty String/0
	public void clear() {

		this.id = null;
		this.name = "";
		this.description= "";

		this.amount = null;
		this.currency = "";

		this.cityName = "";
		this.country = "";
		this.state = "";
		this.country = "";

		this.picture = null;

		this.themeName = "";
	}

	// Empty constructor
	public TourPackageFormViewModel() {
	}

	//Getters & Setters

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
