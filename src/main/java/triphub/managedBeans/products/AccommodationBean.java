package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.AccommodationType;
import triphub.entity.util.CurrencyType;
import triphub.helpers.FacesMessageUtil;
import triphub.services.AccommodationService;

import triphub.viewModel.SubServicesViewModel;

@Named("accommodationBean")
@RequestScoped
public class AccommodationBean implements Serializable {

	@Inject
	private AccommodationService accommodationService;
	
	
	@Inject
	private SubServicesViewModel accommodationVm = new SubServicesViewModel();
	
	private static final long serialVersionUID = 1L;
	
	private List<Accommodation> allAccommodations;


	public AccommodationBean() {
		
	}
	

	public AccommodationBean(AccommodationService accommodationService, SubServicesViewModel accommodationVm,
			List<Accommodation> allAccommodations) {
		this.accommodationService = accommodationService;
		this.accommodationVm = accommodationVm;
		this.allAccommodations = allAccommodations;
	}
	
	@PostConstruct
	public void init() {
		allAccommodations = accommodationService.getAll();
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if (id != null) {
			Long accommodationId = Long.parseLong(id);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedAccommodationId",
					accommodationId);
			accommodationVm = accommodationService.initSubService(accommodationId);
			if (accommodationVm == null) {
				FacesMessageUtil.addErrorMessage("Initialization failed: Accommodation does not exist");
			}
		}
	}

	public void create () {
		 accommodationService.create(accommodationVm);
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accommodation added successfully !"));
		
	}
	
	public String updateAccommodation() {
		try {
			accommodationService.update(accommodationVm);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Accommodation updated successfully!"));
			
			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/AccommodationUpdate.xhtml?faces-redirect=true&id="
					+ accommodationVm.getId();
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);
			
		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update accommodation : " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update accommodation. An unexpected error occurred.");
		
		}clear();
		
		return null;		
	}
	
	void clear() {
		accommodationVm = new SubServicesViewModel();
	}
	
	public void deleteAccommodation() {
		Long selectedAccommodationId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("selectedAccommodationId");
		if ( selectedAccommodationId == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Accommodation ID not found in session.");
			return;
		}

		SubServicesViewModel existingAccommodationVm = accommodationService.initSubService(selectedAccommodationId);
		if (existingAccommodationVm == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Accommodation does not exist.");
			return;
		}

		FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("confirmDelete();");
	}

	
	 public List<Accommodation> getAllAccommodation() {
		 return accommodationService.getAllAccommodation();
	 }


	public AccommodationService getAccommodationService() {
		return accommodationService;
	}


	public void setAccommodationService(AccommodationService accommodationService) {
		this.accommodationService = accommodationService;
	}


	public SubServicesViewModel getAccommodationVm() {
		return accommodationVm;
	}


	public void setAccommodationVm(SubServicesViewModel accommodationVm) {
		this.accommodationVm = accommodationVm;
	}
	
	public AccommodationType[] getAllAccommodationTypes() {
        return AccommodationType.values();
    }
	
	public CurrencyType[] getAllCurrencyTypes() {
        return CurrencyType.values();
    }

	public List<Accommodation> getAllAccommodations() {
		return allAccommodations;
	}


	public void setAllAccommodations(List<Accommodation> allAccommodations) {
		this.allAccommodations = allAccommodations;
	}
	
	
	
}
