package triphub.managedBeans.products;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import triphub.dao.service.AccommodationDAO;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.AccommodationType;
import triphub.entity.util.CurrencyType;
import triphub.helpers.FacesMessageUtil;
import triphub.helpers.ImageHelper;
import triphub.services.AccommodationService;

import triphub.viewModel.SubServicesViewModel;

/**
 * Managed bean for handling accommodation related operations in the
 * application's UI layer.
 */
@Named("accommodationBean")
@RequestScoped
public class AccommodationBean implements Serializable {

	@Inject
	private AccommodationService accommodationService;

	@Inject
	private AccommodationDAO accommodationDao;

	private SubServicesViewModel accommodationVm = new SubServicesViewModel();

	private static final long serialVersionUID = 1L;

	private List<Accommodation> allAccommodations;
	private Accommodation selectedAccommodation;
	private Accommodation lastAccommodationAdded;
	private String selectedCurrency;
	private boolean deletionSuccessful;
	private List<String> currencies;
	private Part pictureAccommodation;
	private String picName;

	private Long accommodationId;

	public AccommodationBean() {

	}

	/**
	 * Constructor for creating a new instance with given properties.
	 * 
	 * @param accommodationService The service for managing accommodations.
	 * @param accommodationVm      The view model for sub-services.
	 * @param allAccommodations    The list of all accommodations.
	 */
	public AccommodationBean(AccommodationService accommodationService, SubServicesViewModel accommodationVm,
			List<Accommodation> allAccommodations) {
		this.accommodationService = accommodationService;
		this.accommodationVm = accommodationVm;
		this.allAccommodations = allAccommodations;
		currencies = Arrays.asList("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF");
	}

	/**
	 * Initialization method for the bean. Determines and initializes the current
	 * accommodationId and sets up the related objects.
	 */
	@PostConstruct
	public void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		accommodationId = (Long) externalContext.getSessionMap().get("selectedAccommodationId");

		if (accommodationId == null) {
			String idParam = externalContext.getRequestParameterMap().get("id");
			if (idParam != null && !idParam.trim().isEmpty()) {
				try {
					accommodationId = Long.parseLong(idParam);
					externalContext.getSessionMap().put("selectedAccommodationId", accommodationId);
				} catch (NumberFormatException e) {
					FacesMessageUtil.addErrorMessage("Id not valid");
					return;
				}
			}
		}

		if (accommodationId != null) {
			accommodationVm = accommodationService.initSubService(accommodationId);
			if (accommodationVm == null) {
				FacesMessageUtil.addErrorMessage("Accommodation does not exists");
				return;
			}

			selectedAccommodation = accommodationService.getAccommodationById(accommodationId);
			if (selectedAccommodation == null) {
				FacesMessageUtil.addErrorMessage("Accommodation does not exists");
				return;
			}
		} else {
			allAccommodations = accommodationService.getAll();
		}
	}

	/**
	 * Fetches all accommodations.
	 *
	 * @return Redirects to the accommodations page.
	 */
	public String loadAllAccommodations() {
		allAccommodations = accommodationService.getAll();

		return "accommodations";
	}

	/**
	 * Creates a new accommodation.
	 */
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
			picName = ImageHelper.processProfilePicture(pictureAccommodation);
		} catch (IOException e) {

			e.printStackTrace();
		}
		if (picName != null) {
			accommodationVm.setLink(picName);
		}

		accommodationService.create(accommodationVm, userId, userType);

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accommodation added successfully !"));

		clear();
	}

	/**
	 * Updates the details of an accommodation.
	 *
	 * @return Redirects to the AccommodationFormTest page if successful.
	 */
	public String updateAccommodation() {
		try {

			accommodationService.update(accommodationVm);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Accommodation updated successfully!"));

			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/AccommodationFormTest.xhtml?faces-redirect=true";
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);

		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update accommodation : " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update accommodation. An unexpected error occurred.");

		}
		// clear();

		return null;
	}

	/**
	 * Initializes the accommodation update form.
	 *
	 * @return Redirects to the AccomUpdateTest page.
	 */
	public String initFormUpdate() {
		try {

			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/AccomUpdateTest.xhtml?faces-redirect=true&id="
					+ accommodationVm.getId();
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);

		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update accommodation : " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update accommodation. An unexpected error occurred.");

		}
		// clear();

		return null; // TODO à quoi sert le srting ici

	}

	/**
	 * Clears the current accommodation view model.
	 */
	void clear() {
		accommodationVm = new SubServicesViewModel();
	}

	/**
	 * Initiates the process to delete an accommodation.
	 */
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

	/**
	 * Performs the actual delete operation for the accommodation.
	 *
	 * @return Error message if the accommodation doesn't exist; otherwise null.
	 */
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

	/**
	 * Fetches accommodations associated with the current user.
	 *
	 * @return List of accommodations associated with the user.
	 */
	public List<Accommodation> getCurrentUserAccommodations() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		String userType = (String) externalContext.getSessionMap().get("userType");
		Long userId = null;

		if ("organizer".equals(userType)) {
			userId = (Long) externalContext.getSessionMap().get("organizerId");
		} else if ("provider".equals(userType)) {
			userId = (Long) externalContext.getSessionMap().get("providerId");
		}

		if (userId == null) {
			String userIdParam = externalContext.getRequestParameterMap().get("userId");
			if (userIdParam != null && !userIdParam.trim().isEmpty()) {
				try {
					userId = Long.parseLong(userIdParam);
				} catch (NumberFormatException e) {
					FacesMessageUtil.addErrorMessage("Format d'ID d'utilisateur non valide.");
					return new ArrayList<>();
				}
			}
		}

		if (userId == null) {
			return new ArrayList<>();
		}

		return accommodationService.getAccommodationForOrganizer(userId);
	}

	/**
	 * Adds an accommodation to an organizer.
	 */
	public void addAccommodationToOrganizer() {
		String userType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("userType");
		Long userId;

		if ("organizer".equals(userType)) {
			userId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("organizerId");
		} else {
			FacesMessageUtil.addErrorMessage("Only an organizer can add a accommodation");
			return;
		}

		String accommodationIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("id");

		if (accommodationIdParam == null) {
			FacesMessageUtil.addErrorMessage("Select a accommodation.");
			return;
		}

		Long selectedAccommodationIdToAdd;
		try {
			selectedAccommodationIdToAdd = Long.parseLong(accommodationIdParam);
		} catch (NumberFormatException e) {
			FacesMessageUtil.addErrorMessage("Accommodation id no valid.");
			return;
		}

		boolean result = accommodationDao.addAccommodationToOrganizer(userId, selectedAccommodationIdToAdd);

		if (result) {
			FacesMessageUtil.addSuccessMessage("Accommodation added with success.");
		} else {
			FacesMessageUtil.addErrorMessage("Error when added a accommodation.");
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

	public List<String> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<String> currencies) {
		this.currencies = currencies;
	}

}
