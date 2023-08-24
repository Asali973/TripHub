package triphub.entity.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.entity.util.Administration;
import triphub.entity.util.CompanyInfo;
import triphub.viewModel.UserViewModel;

@Entity
public class Provider {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	private CompanyInfo companyInfo;

//    @OneToOne(cascade = CascadeType.ALL)
//    private Service service;

	@OneToOne(cascade = CascadeType.ALL)
	private Administration administration;
	
	@OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transportation> transportations = new ArrayList<>();
	
	@OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Restaurant> restaurants = new ArrayList<>();
	
	@OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Accommodation> accommodations = new ArrayList<>();
	
	public void updateProviderFromViewModel(UserViewModel form) {
	    this.setId(form.getProviderId());
	    this.getUser().updateUserFromViewModel(form);
	    this.getUser().getAddress().updateAddressFromViewModel(form);
	    this.getUser().getFinance().updateFinanceInfoFromViewModel(form);
	    this.getCompanyInfo().updateCompanyInfoFromViewModel(form);
	    this.getAdministration().updateAdministrationFromViewModel(form);
	}

	public UserViewModel initProviderViewModel() {
	    UserViewModel form = new UserViewModel();
	    this.getUser().initUserViewModel(form);
	    this.getUser().getAddress().initAddressViewModel(form);
	    this.getUser().getFinance().initFinanceInfoViewModel(form);
	    this.getCompanyInfo().initCompanyInfoViewModel(form);
	    this.getAdministration().initAdministrationViewModel(form);
	    form.setProviderId(this.getId());

	    return form;
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

}
