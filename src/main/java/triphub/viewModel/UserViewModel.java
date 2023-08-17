package triphub.viewModel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import triphub.entity.product.Price;
import triphub.entity.subscription.Customization;
import triphub.entity.subscription.Subscription;
import triphub.entity.subscription.SubscriptionType;
import triphub.entity.util.Picture;

public class UserViewModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long customerId;
	private Long providerId;
	private Long organizerId;
	private Long superAdminId;
	private Long userId;

	private String firstName;
	private String lastName;
	private String email;
	private String phoneNum;
	private String password;
	private String confirmPassword;
	
	private String profilePicture;

	private String num;
	private String street;
	private String city;
	private String state;
	private String country;
	private String zipCode;

	private String CCNumber;
	private Date expirationDate;

	private String companyName;
	private String companyLogoLink;
	private String companyPictureLink;

	private String siret;
	private String phone;
	private String sector;
	private String adminEmail;
	
    private Long id;

    private Subscription subscription; 

    private Price price;
    private int duration;
    private SubscriptionType type;
    private Customization customization;
	
	private String primaryColor;
	private String secondaryColor;
	private String primaryFont;
	private String secondaryFont;
	private String logoUrl;
	private String backgroundUrl;
	private Customization.LayoutType layoutType;
	private boolean useHeader;
	private boolean useFooter;
	private boolean showSidebar;
	private boolean stickySidebar;
	private boolean useDarkTheme;
	
    public void clear() {
        this.customerId = null;
        this.providerId = null;
        this.organizerId = null;
        this.superAdminId = null;
        this.userId = null;

        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.phoneNum = "";
        this.password = "";
        this.confirmPassword = "";
        
        this.profilePicture = "";

        this.num = "";
        this.street = "";
        this.city = "";
        this.state = "";
        this.country = "";
        this.zipCode = "";

        this.CCNumber = "";
        this.expirationDate = null;

        this.companyName = "";
        this.companyLogoLink = "";
        this.companyPictureLink = "";

        this.siret = "";
        this.phone = "";
        this.sector = "";
        this.adminEmail = "";
    }

	public UserViewModel() {
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

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getOrganizerId() {
		return organizerId;
	}

	public void setOrganizerId(Long organizerId) {
		this.organizerId = organizerId;
	}

	public Long getSuperAdminId() {
		return superAdminId;
	}

	public void setSuperAdminId(Long superAdminId) {
		this.superAdminId = superAdminId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPrimaryColor() {
		return primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	public String getSecondaryColor() {
		return secondaryColor;
	}

	public void setSecondaryColor(String secondaryColor) {
		this.secondaryColor = secondaryColor;
	}

	public String getPrimaryFont() {
		return primaryFont;
	}

	public void setPrimaryFont(String primaryFont) {
		this.primaryFont = primaryFont;
	}

	public String getSecondaryFont() {
		return secondaryFont;
	}

	public void setSecondaryFont(String secondaryFont) {
		this.secondaryFont = secondaryFont;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public Customization.LayoutType getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(Customization.LayoutType layoutType) {
		this.layoutType = layoutType;
	}

	public boolean isUseHeader() {
		return useHeader;
	}

	public void setUseHeader(boolean useHeader) {
		this.useHeader = useHeader;
	}

	public boolean isUseFooter() {
		return useFooter;
	}

	public void setUseFooter(boolean useFooter) {
		this.useFooter = useFooter;
	}

	public boolean isShowSidebar() {
		return showSidebar;
	}

	public void setShowSidebar(boolean showSidebar) {
		this.showSidebar = showSidebar;
	}

	public boolean isStickySidebar() {
		return stickySidebar;
	}

	public void setStickySidebar(boolean stickySidebar) {
		this.stickySidebar = stickySidebar;
	}

	public boolean isUseDarkTheme() {
		return useDarkTheme;
	}

	public void setUseDarkTheme(boolean useDarkTheme) {
		this.useDarkTheme = useDarkTheme;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public SubscriptionType getType() {
		return type;
	}

	public void setType(SubscriptionType type) {
		this.type = type;
	}

	public Customization getCustomization() {
		return customization;
	}

	public void setCustomization(Customization customization) {
		this.customization = customization;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	
	

}
