package triphub.entity.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import triphub.entity.product.TourPackage;
import triphub.entity.subscription.Customization;
import triphub.entity.subscription.Subscription;
import triphub.entity.util.Administration;
import triphub.entity.util.CompanyInfo;
import triphub.viewModel.UserViewModel;

@Entity
public class Organizer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	private CompanyInfo companyInfo;

	@OneToOne(cascade = CascadeType.ALL)
	private Administration administration;

//    @OneToOne(cascade = CascadeType.ALL)
//    private Destination destination;

	@OneToOne(cascade = CascadeType.ALL)
	private Subscription subscription;
	
	@OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TourPackage> tourPackages = new ArrayList<>();

	
	public void updateOrganizerFromViewModel(UserViewModel form) {
	    this.setId(form.getOrganizerId());
	    this.getUser().updateUserFromViewModel(form);
	    this.getUser().getAddress().updateAddressFromViewModel(form);
	    this.getUser().getFinance().updateFinanceInfoFromViewModel(form);
	    this.getCompanyInfo().updateCompanyInfoFromViewModel(form);
	    this.getAdministration().updateAdministrationFromViewModel(form);
	}

	public UserViewModel initOrganizerViewModel() {
	    UserViewModel userViewModel = new UserViewModel();
	    this.getUser().initUserViewModel(userViewModel);
	    this.getUser().getAddress().initAddressViewModel(userViewModel);
	    this.getUser().getFinance().initFinanceInfoViewModel(userViewModel);
	    this.getCompanyInfo().initCompanyInfoViewModel(userViewModel);
	    this.getAdministration().initAdministrationViewModel(userViewModel);
	    userViewModel.setOrganizerId(this.getId());

	    return userViewModel;
	}
	
	public void updateGraphicSettingsFromViewModel(UserViewModel form) {
	    if(this.getSubscription() != null && this.getSubscription().getCustomization() != null) {
	        Customization customization = this.getSubscription().getCustomization();
	        customization.setPrimaryColor(form.getPrimaryColor());
	        customization.setSecondaryColor(form.getSecondaryColor());
	        customization.setPrimaryFont(form.getPrimaryFont());
	        customization.setSecondaryFont(form.getSecondaryFont());
	        customization.setLogoUrl(form.getLogoUrl());
	        customization.setBackgroundUrl(form.getBackgroundUrl());
	        customization.setLayout(form.getLayout());
	    }
	}
	
    public Subscription toSubscription() {
        if (this.subscription == null) {
            this.subscription = new Subscription();
        }
        return this.subscription;
    }
    
    public Customization toCustomization() {
        return this.toSubscription().toCustomization();
    }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public Administration getAdministration() {
		return administration;
	}

	public void setAdministration(Administration administration) {
		this.administration = administration;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public List<TourPackage> getTourPackages() {
		return tourPackages;
	}

	public void setTourPackages(List<TourPackage> tourPackages) {
		this.tourPackages = tourPackages;
	}
	
	
	
}
