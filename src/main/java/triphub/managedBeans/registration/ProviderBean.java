package triphub.managedBeans.registration;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;

import triphub.dao.ProviderDAO;
import triphub.entity.user.Provider;
import triphub.entity.user.User;
import triphub.entity.util.Address;
import triphub.entity.util.Administration;
import triphub.entity.util.CompanyInfo;
import triphub.entity.util.FinanceInfo;
import triphub.entity.util.Picture;
import triphub.entity.util.PictureType;

@ManagedBean(name="providerBean")
@SessionScoped
public class ProviderBean implements Serializable {
	
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
	
	// Picture
    private String link;
    
	
	// Company info
	private String companyName;
	private String companyLogoLink;
	private String companyPictureLink;
	
	// Administration info
	private String siret;
	private String phone;
	private String sector;
	private String adminEmail;
	
	// Login status
	private boolean loggedIn;

	// EntityManager setup
	private EntityManagerFactory emf;
	private EntityManager em;

	public ProviderBean() {
		emf = Persistence.createEntityManagerFactory("triphub");
		em = emf.createEntityManager();
	}

	public void register() {
		if (!password.equals(confirmPassword)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Passwords do not match!"));
			return;
		}
		
		// Create user
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPhoneNum(phoneNum);
		user.setPassword(hashPassword(password));
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
		
		// Create company info
		CompanyInfo companyInfo = new CompanyInfo();
		companyInfo.setName(companyName);
		
		// Set logo
		Picture logo = new Picture();
		logo.setLink(companyLogoLink);
		companyInfo.setLogo(logo);
		
		// Set company picture
		Picture picture = new Picture();
		picture.setLink(companyPictureLink);
		companyInfo.setPicture(picture);
		
		// Create administration info
		Administration administration = new Administration();
		administration.setSiret(siret);
		administration.setPhone(phone);
		administration.setSector(sector);
		administration.setEmail(adminEmail);
		
		// Create provider
		Provider provider = new Provider();
		provider.setUser(user);
		provider.setCompanyInfo(companyInfo);
		provider.setAdministration(administration);

		ProviderDAO providerDao = new ProviderDAO(em);
		em.getTransaction().begin();
		providerDao.create(provider);
		em.getTransaction().commit();
	}

	public boolean login() {
		ProviderDAO providerDao = new ProviderDAO(em);
		Provider provider = providerDao.findByEmail(email);

		if (provider != null && checkPassword(password, provider.getUser().getPassword())) {
			loggedIn = true;
			return true;
		} else {
			loggedIn = false;
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

	// Encrypt password
	private String hashPassword(String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	// Check if the password matches the encrypted password
	private boolean checkPassword(String plainPassword, String hashedPassword) {
		if (BCrypt.checkpw(plainPassword, hashedPassword))
			return true;
		else
			return false;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyLogoLink() {
		return companyLogoLink;
	}

	public void setCompanyLogoLink(String companyLogoLink) {
		this.companyLogoLink = companyLogoLink;
	}

	public String getCompanyPictureLink() {
		return companyPictureLink;
	}

	public void setCompanyPictureLink(String companyPictureLink) {
		this.companyPictureLink = companyPictureLink;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}


}

