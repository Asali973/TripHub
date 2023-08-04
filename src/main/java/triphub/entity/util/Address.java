package triphub.entity.util;

import javax.persistence.*;

import triphub.viewModel.UserViewModel;

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

	public Address() {

	}

	public Address(String num, String street, String city, String state, String country, String zipCode) {
		this.num = num;
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
	}

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

	public void updateAddressFromViewModel(UserViewModel form) {
		this.setNum(form.getNum());
		this.setStreet(form.getStreet());
		this.setCity(form.getCity());
		this.setState(form.getState());
		this.setCountry(form.getCountry());
		this.setZipCode(form.getZipCode());
	}
	
	public void initAddressViewModel(UserViewModel userViewModel) {
	    userViewModel.setNum(this.getNum());
	    userViewModel.setStreet(this.getStreet());
	    userViewModel.setCity(this.getCity());
	    userViewModel.setState(this.getState());
	    userViewModel.setCountry(this.getCountry());
	    userViewModel.setZipCode(this.getZipCode());
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
