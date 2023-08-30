
package triphub.managedBeans.products;

import java.io.Serializable;
import java.math.BigDecimal;
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

import triphub.entity.product.TourPackage;
import triphub.helpers.FacesMessageUtil;
import triphub.helpers.ImageHelper;
import triphub.services.TourPackageService;
import triphub.viewModel.TourPackageFormViewModel;

/**
 * Managed bean for handling tour package related operations.
 */
@Named("tourPackageBean")
@RequestScoped

public class TourPackageBean implements Serializable {

	@Inject
	private TourPackageService tourPackageService;
	private static final long serialVersionUID = 1L;
	private Long tourPackageId;
	private TourPackageFormViewModel tourPackageVm = new TourPackageFormViewModel();
	private List<TourPackage> allTourPackages;
	private TourPackage lastTourPackageAdded;
	private TourPackage tourPackage;
	private TourPackage selectedTourPackage;
	private Part profilePicture;
	private boolean deletionSuccessful;
	private List<String> currencies;
	private String selectedCurrency;
	private List<TourPackage> searchResults = new ArrayList<>();

	private Part picturePackage;

	public TourPackageBean() {
		currencies = Arrays.asList("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF");
		tourPackageVm = new TourPackageFormViewModel();
		tourPackageVm.setCurrency("USD");

	}

	/**
	 * Initializes data for the bean.
	 */
	@PostConstruct
	public void init() {
		allTourPackages = tourPackageService.getAllTourPackages();

		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

		if (id != null && !id.isEmpty()) {
			Long tourPackageId = Long.parseLong(id);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedTourPackageId",
					tourPackageId);
			tourPackageVm = tourPackageService.initTourPackage(tourPackageId);
			if (tourPackageVm == null) {
				FacesMessageUtil.addErrorMessage("Initialization failed: Tour package does not exist");
			}
		}

		if (id != null && !id.isEmpty()) {
			Long tourPackageId = Long.parseLong(id);
			// Fetch the selected tour package using tourPackageService
			selectedTourPackage = tourPackageService.getTourPackageById(tourPackageId);

			if (selectedTourPackage == null) {
				FacesMessageUtil.addErrorMessage("Initialization failed: Tour package does not exist");
			}
		}

	}

	/**
	 * Load all tour packages.
	 *
	 * @return Navigation outcome.
	 */
	public String loadAllTourPackages() {
		allTourPackages = tourPackageService.getAllTourPackages();

		return "tourPackages";
	}

	/**
	 * Create a new tour package.
	 */
	public void createPackage() {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Long organizerId = (Long) externalContext.getSessionMap().get("organizerId");

			if (organizerId == null) {
				FacesMessageUtil.addErrorMessage("No organizerId found in session.");
				return;
			}

			// Uploading the picture and setting the link to ViewModel
			String picName = ImageHelper.processProfilePicture(picturePackage);
			if (picName != null) {
				tourPackageVm.setLink(picName);
			}

			lastTourPackageAdded = tourPackageService.createTourPackage(tourPackageVm, organizerId);
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Creation failed: " + e.getMessage());
		}
		clear();
	}

	/**
	 * View details of a specific tour package.
	 *
	 * @param packageId The ID of the package.
	 * @return Navigation outcome.
	 */
	public String viewDetails(long packageId) {
		selectedTourPackage = tourPackageService.getTourPackageById(packageId);
		return "details";
	}

	/**
	 * Update an existing tour package.
	 *
	 * @return Navigation outcome.
	 */
	public String updatePackage() {
		try {
			tourPackageVm.setCurrency(selectedCurrency);
			tourPackageService.updateTourPackage(tourPackageVm);
			System.out.println("Executing updatePackage()");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Tour package updated successfully!"));

			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/tpUpdate.xhtml?faces-redirect=true&id="
					+ tourPackageVm.getId();
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);

		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update tour package: " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update tour package. An unexpected error occurred.");
		}
		clear();
		return null;
	}

	/**
	 * Delete a tour package.
	 */
	public void deletePackage() {
		Long selectedTourPackageId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("selectedTourPackageId");
		if (selectedTourPackageId == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Tour package ID not found in session.");
			return;
		}

		TourPackageFormViewModel existingTourPackageVm = tourPackageService.initTourPackage(selectedTourPackageId);
		if (existingTourPackageVm == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Tour package does not exist.");
			return;
		}

		FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("confirmDelete();");
	}

	/**
	 * Perform the delete action.
	 *
	 * @return Optional message.
	 */
	public String performDelete() {
		Long selectedTourPackageId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("selectedTourPackageId");
		TourPackageFormViewModel existingTourPackageVm = tourPackageService.initTourPackage(selectedTourPackageId);

		if (existingTourPackageVm == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Tour package does not exist.");
			return "Tour package does not exist";
		}

		tourPackageService.deleteTourPackage(existingTourPackageVm);

		deletionSuccessful = true;

		return null;
	}

	/**
	 * Initialize form data based on a tour package ID.
	 *
	 * @param id Tour package ID.
	 */
	public void initFormData(Long id) {
		TourPackageFormViewModel temp = tourPackageService.initTourPackage(id);
		if (temp == null) {
			this.tourPackageVm = temp;
		} else {
			FacesMessageUtil.addErrorMessage("Initialization failed: Tour package does not exist");
		}
	}

	/**
	 * Conduct an advanced search for tour packages.
	 */
	public void performAdvancedSearch() {
		String name = tourPackageVm.getName();
		String themeName = tourPackageVm.getThemeName();
		String city = tourPackageVm.getCityName();
		String state = tourPackageVm.getState();
		String country = tourPackageVm.getCountry();
		BigDecimal minPrice = tourPackageVm.getMinPrice();
		BigDecimal maxPrice = tourPackageVm.getMaxPrice();

		if (minPrice == null) {
			minPrice = BigDecimal.ZERO;
		}
		if (maxPrice == null) {
			maxPrice = BigDecimal.ZERO;
		}

		List<TourPackage> searchResults = tourPackageService.advancedSearch(city, state, country, minPrice, maxPrice,
				name, themeName);
		
		if (searchResults.isEmpty()) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "No results found.", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Search successful.", null);
			FacesContext.getCurrentInstance().addMessage(null, message);

			for (TourPackage tourPackage : searchResults) {
				FacesMessage packageMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Package Name: " + tourPackage.getName() + ", Price: " + tourPackage.getPrice().getAmount()
								+ " " + tourPackage.getPrice().getCurrency() + ", City: "
								+ tourPackage.getDestination().getCityName() + ", State: "
								+ tourPackage.getDestination().getState() + ", Country: "
								+ tourPackage.getDestination().getCountry() + ", Theme: "
								+ tourPackage.getTheme().getThemeName(),
						null);
				FacesContext.getCurrentInstance().addMessage(null, packageMessage);
			}
		}
	}

	/**
	 * Generate options for price.
	 *
	 * @param maxPrice Maximum price value.
	 * @return List of options.
	 */
	public List<BigDecimal> generateNumberOptions(BigDecimal maxPrice) {
		if (maxPrice == null || maxPrice.compareTo(BigDecimal.ZERO) == 0) {
			maxPrice = BigDecimal.valueOf(3000); // Set the default maximum price to 3000
		}

		List<BigDecimal> options = new ArrayList<>();
		for (BigDecimal i = BigDecimal.valueOf(100); i.compareTo(maxPrice) <= 0; i = i.add(BigDecimal.valueOf(100))) {
			options.add(i);
		}
		return options;
	}

	/**
	 * Fetch tour packages for the currently logged-in user.
	 *
	 * @return List of tour packages.
	 */
	public List<TourPackage> getCurrentUserTourPackages() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		Long organizerId = (Long) externalContext.getSessionMap().get("organizerId");

		if (organizerId == null) {
			String organizerIdParam = externalContext.getRequestParameterMap().get("organizerId");
			if (organizerIdParam != null && !organizerIdParam.trim().isEmpty()) {
				try {
					organizerId = Long.parseLong(organizerIdParam);
				} catch (NumberFormatException e) {
					FacesMessageUtil.addErrorMessage("ID not valid");
					return new ArrayList<>();
				}
			}
		}

		if (organizerId == null) {
			return new ArrayList<>();
		}

		return tourPackageService.getTourPackagesForOrganizer(organizerId);
	}

	/**
	 * Clear the ViewModel.
	 */
	public void clear() {
		tourPackageVm = new TourPackageFormViewModel();
	}

	// GETTERS SETTERS
	public String getSelectedCurrency() {
		return selectedCurrency;
	}

	public void setSelectedCurrency(String selectedCurrency) {
		this.selectedCurrency = selectedCurrency;
	}

	public List<String> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<String> currencies) {
		this.currencies = currencies;
	}

	public List<BigDecimal> getMinPriceOptions() {
		return generateNumberOptions(tourPackageVm.getMaxPrice());
	}

	public List<BigDecimal> getMaxPriceOptions() {
		return generateNumberOptions(tourPackageVm.getMinPrice());
	}

	public List<TourPackage> getAllTourPackages() {
		return allTourPackages;
	}

	public void setAllTourPackages(List<TourPackage> allTourPackages) {
		this.allTourPackages = allTourPackages;
	}

	public TourPackageFormViewModel getTourPackageVm() {
		return tourPackageVm;
	}

	public void setTourPackageVm(TourPackageFormViewModel tourPackageVm) {
		this.tourPackageVm = tourPackageVm;
	}

	public TourPackage getLastTourPackageAdded() {
		return lastTourPackageAdded;
	}

	public void setLastTourPackageAdded(TourPackage lastTourPackageAdded) {
		this.lastTourPackageAdded = lastTourPackageAdded;
	}

	public TourPackage getTourPackage() {
		return tourPackage;
	}

	public void setTourPackage(TourPackage tourPackage) {
		this.tourPackage = tourPackage;
	}

	public TourPackage getSelectedTourPackage() {
		return selectedTourPackage;
	}

	public void setSelectedTourPackage(TourPackage selectedTourPackage) {
		this.selectedTourPackage = selectedTourPackage;
	}

	public Long getTourPackageId() {
		return tourPackageId;
	}

	public void setTourPackageId(Long tourPackageId) {
		this.tourPackageId = tourPackageId;
	}

	public Part getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(Part profilePicture) {
		this.profilePicture = profilePicture;
	}

	public boolean isDeletionSuccessful() {
		return deletionSuccessful;
	}

	public void setDeletionSuccessful(boolean deletionSuccessful) {
		this.deletionSuccessful = deletionSuccessful;
	}

	public TourPackageService getTourPackageService() {
		return tourPackageService;
	}

	public void setTourPackageService(TourPackageService tourPackageService) {
		this.tourPackageService = tourPackageService;
	}

	public Part getPicturePackage() {
		return picturePackage;
	}

	public void setPicturePackage(Part picturePackage) {
		this.picturePackage = picturePackage;
	}

	public List<TourPackage> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<TourPackage> searchResults) {
		this.searchResults = searchResults;
	}

}
