package triphub.managedBeans.products;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.AccommodationType;
import triphub.entity.util.CurrencyType;
import triphub.helpers.FacesMessageUtil;
import triphub.helpers.ImageHelper;
import triphub.services.AccommodationService;

import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.TourPackageFormViewModel;

@Named("accommodationBean")
@RequestScoped
public class AccommodationBean implements Serializable {

	@Inject
	private AccommodationService accommodationService;

	private SubServicesViewModel accommodationVm = new SubServicesViewModel();

	private static final long serialVersionUID = 1L;

	private List<Accommodation> allAccommodations;
	private Accommodation selectedAccommodation;
	private Accommodation lastAccommodationAdded;
	private String selectedCurrency;
	private boolean deletionSuccessful;

	private Part pictureAccommodation;

	public AccommodationBean() {

	}

	// quand il un constructeur a plusieur a crée le constructeur par défaut
	// n'existe plus donc il faut le créer explixicitement si besoin
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

		if (id != null && !id.isEmpty()) {
			Long accommodationId = Long.parseLong(id);

			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedAccommodationId",
					accommodationId);

			accommodationVm = accommodationService.initSubService(accommodationId);
			if (accommodationVm == null) {
				FacesMessageUtil.addErrorMessage("Initialization failed: Accommodation does not exist");
			}

			selectedAccommodation = accommodationService.getAccommodationById(accommodationId);
			if (selectedAccommodation == null) {
				FacesMessageUtil.addErrorMessage("Initialization failed: Accommodation does not exist");
			}
		}
	}


	public String loadAllAccommodations() {
		allAccommodations = accommodationService.getAll();

		return "accommodations";
	}

	public void create() {

		String userType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("userType");

		Long userId;
		if ("organizer".equals(userType)) {
			userId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("organizerId");
		} else if ("provider".equals(userType)) {
			userId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("providerId");
		} else {
			userId = null;
		}

		// Uploading the picture and setting the link to ViewModel
		try {
			String picName = ImageHelper.processProfilePicture(pictureAccommodation);
			if (picName != null) {
				accommodationVm.setLink(picName);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		accommodationService.create(accommodationVm, userId, userType);

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accommodation added successfully !"));

		
	}



	public String updateAccommodation() {
		try {
			
			accommodationService.update(accommodationVm);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Accommodation updated successfully!"));

			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/AccommodationForm.xhtml?faces-redirect=true";
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);

		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update accommodation : " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update accommodation. An unexpected error occurred.");

		}


		return null;
	}

	public String initFormUpdate() {
		try {
		

			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/AccomUpdate.xhtml?faces-redirect=true&id="
					+ accommodationVm.getId();
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);

		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update accommodation : " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update accommodation. An unexpected error occurred.");

		}
		

		return null; // TODO à quoi sert le srting ici

	}

	void clear() {
		accommodationVm = new SubServicesViewModel();
	}

	public void deleteAccommodation() {
		Long selectedAccommodationId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("selectedAccommodationId");
		if (selectedAccommodationId == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Accommodation ID not found in session.");
			return;
		}

		SubServicesViewModel existingAccommodationVm = accommodationService.initSubService(selectedAccommodationId);
		if (existingAccommodationVm == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Accommodation does not exist.");
			return;
		}

		FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("confirmDeleteAccom();");
	}

	public String performDelete() {
		Long selectedAccommodationId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("selectedAccommodationId");
		SubServicesViewModel existingAccommodationVm = accommodationService.initSubService(selectedAccommodationId);

		if (existingAccommodationVm == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Accommodation does not exist.");
			return "Accommodation does not exist";
		}

		accommodationService.delete(existingAccommodationVm);

		deletionSuccessful = true;

		return null;
	}

	public List<Accommodation> getCurrentUserAccommodations() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		String userType = (String) externalContext.getSessionMap().get("userType");

		if ("organizer".equals(userType)) {
			Long organizerId = (Long) externalContext.getSessionMap().get("organizerId");
			if (organizerId == null) {
				return new ArrayList<>();
			}
			return accommodationService.getAccommodationForOrganizer(organizerId);
		} else if ("provider".equals(userType)) {
			Long providerId = (Long) externalContext.getSessionMap().get("providerId");
			if (providerId == null) {
				return new ArrayList<>();
			}
			return accommodationService.getAccommodationForProvider(providerId);
		} else {

			return new ArrayList<>();
		}
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

	public Accommodation getSelectedAccommodation() {
		return selectedAccommodation;
	}

	public void setSelectedAccommodation(Accommodation selectedAccommodation) {
		this.selectedAccommodation = selectedAccommodation;
	}

	public boolean isDeletionSuccessful() {
		return deletionSuccessful;
	}

	public void setDeletionSuccessful(boolean deletionSuccessful) {
		this.deletionSuccessful = deletionSuccessful;
	}

	public String getSelectedCurrency() {
		return selectedCurrency;
	}

	public void setSelectedCurrency(String selectedCurrency) {
		this.selectedCurrency = selectedCurrency;
	}

	public Accommodation getLastAccommodationAdded() {
		return lastAccommodationAdded;
	}

	public void setLastAccommodationAdded(Accommodation lastAccommodationAdded) {
		this.lastAccommodationAdded = lastAccommodationAdded;
	}

	public Part getPictureAccommodation() {
		return pictureAccommodation;
	}

	public void setPictureAccommodation(Part pictureAccommodation) {
		this.pictureAccommodation = pictureAccommodation;
	}

}
