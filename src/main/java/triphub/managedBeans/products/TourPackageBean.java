
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
	
	private Part picturePackage;

	public TourPackageBean() {
		currencies = Arrays.asList("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF");
		tourPackageVm = new TourPackageFormViewModel();
		tourPackageVm.setCurrency("USD");

	}

//	@PostConstruct
//	public void init() {
//		allTourPackages = tourPackageService.getAllTourPackages();
//
//		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
//
//		if (id != null  && !id.isEmpty()) {
//			Long tourPackageId = Long.parseLong(id);
//			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedTourPackageId",
//					tourPackageId);
//			tourPackageVm = tourPackageService.initTourPackage(tourPackageId);
//			if (tourPackageVm == null) {
//				FacesMessageUtil.addErrorMessage("Initialization failed: Tour package does not exist");
//			}
//		}
//
//		if (id != null  && !id.isEmpty()) {
//			Long tourPackageId = Long.parseLong(id);
//			// Fetch the selected tour package using tourPackageService
//			selectedTourPackage = tourPackageService.getTourPackageById(tourPackageId);
//
//			if (selectedTourPackage == null) {
//				FacesMessageUtil.addErrorMessage("Initialization failed: Tour package does not exist");
//			}
//		}
//
//	}
	@PostConstruct
	public void init() {
	    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    // Step 1: Try to fetch the tourPackageId from the session
	    tourPackageId = (Long) externalContext.getSessionMap().get("selectedTourPackageId");
	    
	    // Step 2: If not found in session, try to get from the request parameters
	    if (tourPackageId == null) {
	        String idParam = externalContext.getRequestParameterMap().get("id");
	        if (idParam != null && !idParam.trim().isEmpty()) {
	            try {
	                tourPackageId = Long.parseLong(idParam);
	                externalContext.getSessionMap().put("selectedTourPackageId", tourPackageId);
	            } catch (NumberFormatException e) {
	                FacesMessageUtil.addErrorMessage("Invalid tour package ID format.");
	            }
	        }
	    }
	    
	    // Step 3: Use the tourPackageId to initialize other parts
	    if (tourPackageId != null) {
	        tourPackageVm = tourPackageService.initTourPackage(tourPackageId);
	        if (tourPackageVm == null) {
	            FacesMessageUtil.addErrorMessage("Initialization failed: Tour package does not exist");
	            return;
	        }
	        
	        // Fetch the selected tour package
	        selectedTourPackage = tourPackageService.getTourPackageById(tourPackageId);
	        if (selectedTourPackage == null) {
	            FacesMessageUtil.addErrorMessage("Initialization failed: Tour package does not exist");
	            return;
	        }
	    } else {
	        allTourPackages = tourPackageService.getAllTourPackages();
	    }
	}


	public String loadAllTourPackages() {
		allTourPackages = tourPackageService.getAllTourPackages();

		return "tourPackages";
	}

//	public void createPackage() {
////		try {
////			String profilePicName = ImageHelper.processProfilePicture(profilePicture);
////			if (profilePicName != null) {
////				tourPackageVm.setProfilePicture(profilePicName);
////			}
//		lastTourPackageAdded = tourPackageService.createTourPackage(tourPackageVm);
////		} catch (Exception e) {
////			FacesMessageUtil.addErrorMessage("Update failed: " + e.getMessage());
////		}
//		clear();
//	}
	
	public void createPackage() {
	    try {
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        Long organizerId = (Long) externalContext.getSessionMap().get("organizerId");

	        if (organizerId == null) {
	            FacesMessageUtil.addErrorMessage("No organizerId found in session.");
	            return;
	        }
	        
			// Uploading the picture and setting the link to ViewModel
			String picName= ImageHelper.processProfilePicture(picturePackage);
			if (picName != null) {
				tourPackageVm.setLink(picName);
			}

	        lastTourPackageAdded = tourPackageService.createTourPackage(tourPackageVm, organizerId);
	    } catch (Exception e) {
	        FacesMessageUtil.addErrorMessage("Creation failed: " + e.getMessage());
	    }
	    clear();
	}


	public String viewDetails(long packageId) {
		// Fetch package details based on packageId and store in selectedPackage
		// property
		selectedTourPackage = tourPackageService.getTourPackageById(packageId);

		// Return navigation outcome for the details page
		return "details"; // This should match the name of your details.xhtml page without the .xhtml
							// extension
	}

	public String updatePackage() {
		try {
			tourPackageVm.setCurrency(selectedCurrency);
			tourPackageService.updateTourPackage(tourPackageVm);
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

	public void initFormData(Long id) {
		TourPackageFormViewModel temp = tourPackageService.initTourPackage(id);
		if (temp == null) {
			this.tourPackageVm = temp;
		} else {
			FacesMessageUtil.addErrorMessage("Initialization failed: Tour package does not exist");
		}
	}

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

	// Setting Max and Min values
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
	
	

}
//// document.addEventListener('DOMContentLoaded', function() {
//// Get the "Add Package" form and button
//const addTourForm = document.getElementById('addTourForm');
//const addPackageButton = addTourForm.querySelector('button[type="submit"]');
//
//// Add a submit event listener to the "Add Package" form
//addTourForm.addEventListener('submit', function(event) {
//    event.preventDefault();
//    // Call  function to submit the form data to the server (if needed)
//    submitAddTourForm();
//});
//
//// Get the "Display the list" form and button
//const displayListForm = document.getElementById('displayListForm');
//const displayListButton = displayListForm.getElementById('displayListButton');
//
//// Add a click event listener to the "Display the list" button
//displayListButton.addEventListener('click', function() {
//    // Use AJAX to fetch the updated list data
//    fetchUpdatedListData();
//});
//}); 
