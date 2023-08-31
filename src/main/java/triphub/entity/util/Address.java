package triphub.entity.util;

import javax.persistence.*;

import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.UserViewModel;
/**
 * Represents an address entity in the system.
 * This entity contains address details such as number, street, city, state, country, and zip code.
 */
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String num;
	private String street;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	
	/**
	 * Default constructor for Address.
	 */
	public Address() {

	}
	/**
	 * Parameterized constructor for creating an Address object.
	 * 
	 * @param num     Address number.
	 * @param street  Street name.
	 * @param city    City name.
	 * @param state   State name.
	 * @param country Country name.
	 * @param zipCode Zip code.
	 */
	public Address(String num, String street, String city, String state, String country, String zipCode) {
		this.num = num;
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
	}

	/**
	 * Creates and returns an Address object from the provided UserViewModel.
	 * 
	 * @param form The UserViewModel containing address data.
	 * @return An Address object.
	 */
	public static Address createAddressFromViewModel(UserViewModel form) {
		Address address = new Address();
		address.setNum(form.getNum());
		address.setStreet(form.getStreet());
		address.setCity(form.getCity());
		address.setState(form.getState());
		address.setCountry(form.getCountry());
		address.setZipCode(form.getZipCode());
		return address;
	}
	
	/**
	 * Updates the properties of the Address based on the provided UserViewModel.
	 * 
	 * @param form The UserViewModel containing updated address data.
	 */
	public void updateAddressFromViewModel(UserViewModel form) {
		this.setNum(form.getNum());
		this.setStreet(form.getStreet());
		this.setCity(form.getCity());
		this.setState(form.getState());
		this.setCountry(form.getCountry());
		this.setZipCode(form.getZipCode());
	}
	
	/**
	 * Initializes the provided UserViewModel with the properties of this Address object.
	 * 
	 * @param userViewModel The UserViewModel to be populated with address data.
	 */
	public void initAddressViewModel(UserViewModel userViewModel) {
	    userViewModel.setNum(this.getNum());
	    userViewModel.setStreet(this.getStreet());
	    userViewModel.setCity(this.getCity());
	    userViewModel.setState(this.getState());
	    userViewModel.setCountry(this.getCountry());
	    userViewModel.setZipCode(this.getZipCode());
	}
	
	/**
	 * Initializes the provided SubServicesViewModel with the properties of this Address object.
	 * 
	 * @param subservicesvm The SubServicesViewModel to be populated with address data.
	 */
	public void initAddressViewModel(SubServicesViewModel subservicesvm) {
		subservicesvm.getAddress().setNum(this.getNum());
		subservicesvm.getAddress().setStreet(this.getStreet());
		subservicesvm.getAddress().setCity(this.getCity());
		subservicesvm.getAddress().setState(this.getState());
		subservicesvm.getAddress().setCountry(this.getCountry());
		subservicesvm.getAddress().setZipCode(this.getZipCode());
	}
	
	/**
	 * Updates the properties of the Address based on the provided SubServicesViewModel.
	 * 
	 * @param form The SubServicesViewModel containing updated address data.
	 */
	public void updateAddressFromViewModel(SubServicesViewModel form) {
		this.setNum(form.getAddress().getNum());
		this.setStreet(form.getAddress().getStreet());
		this.setCity(form.getAddress().getCity());
		this.setState(form.getAddress().getState());
		this.setCountry(form.getAddress().getCountry());
		this.setZipCode(form.getAddress().getZipCode());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
