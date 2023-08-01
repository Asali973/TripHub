package triphub.managedBeans.registration;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import triphub.entity.util.*;
import triphub.dao.user.CustomerDAO;
import triphub.entity.user.*;
import triphub.helpers.AuthenticationException;
import triphub.helpers.FacesMessageUtil;
import triphub.helpers.PasswordUtils;
import triphub.helpers.RegistrationException;

@Named
@RequestScoped
public class CustomerBean implements Serializable {

	@Inject
	private CustomerDAO customerDAO;

	private static final long serialVersionUID = 1L;
	// User info
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNum;
	private String password;
	private String confirmPassword;
	// Address info
	private String num;
	private String street;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	// Finance info
	private String CCNumber;
	private Date expirationDate;
	// Login status
	private boolean loggedIn;

	public CustomerBean() {
	}

	public void register() {
		if (!password.equals(confirmPassword)) {
			FacesMessageUtil.addErrorMessage("Passwords do not match!");
			return;
		}
		// Create user
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPhoneNum(phoneNum);
		user.setPassword(PasswordUtils.getInstance().hashPassword(password));
		// Create address
		Address address = new Address();
		address.setNum(num);
		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setCountry(country);
		address.setZipCode(zipCode);
		user.setAddress(address);
		// Create finance info
		FinanceInfo finance = new FinanceInfo();
		finance.setCCNumber(CCNumber);
		finance.setExpirationDate(expirationDate);
		user.setFinance(finance);
		// Create customer
		Customer customer = new Customer();
		customer.setUser(user);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("triphub");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		customerDAO.create(customer);
		em.getTransaction().commit();

		em.close();
		emf.close();
	}

	public boolean login() {
		try {
			Customer customer = customerDAO.findByEmail(email);
			if (customer != null
					&& PasswordUtils.getInstance().checkPassword(password, customer.getUser().getPassword())) {
				loggedIn = true;
				return true;
			} else {
				loggedIn = false;
				return false;
			}
		} catch (RegistrationException e) {
			loggedIn = false;
			FacesMessageUtil.addErrorMessage("Registration failed: " + e.getMessage());
			return false;
		}
	}

	public void logout() {
		loggedIn = false;
	}

	// Check if the user is logged in
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public String getCCNumber() {
		return CCNumber;
	}

	public void setCCNumber(String cCNumber) {
		CCNumber = cCNumber;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

}
