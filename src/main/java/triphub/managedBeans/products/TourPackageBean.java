
package triphub.managedBeans.products;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.mapping.Map;

import triphub.entity.product.Destination;
import triphub.entity.product.Price;
import triphub.entity.product.Theme;
import triphub.entity.product.TourPackage;
import triphub.helpers.FacesMessageUtil;
import triphub.services.TourPackageService;
import triphub.viewModel.TourPackageFormViewModel;

@Named("tourPackageBean")
@RequestScoped

public class TourPackageBean implements Serializable {

	@Inject
	private TourPackageService tourPackageService;
	private static final long serialVersionUID = 1L;

	private TourPackageFormViewModel tourPackageVm = new TourPackageFormViewModel();
	private List<TourPackage> allTourPackages;
	private TourPackage lastTourPackageAdded;
	private TourPackage tourPackage;	
	private TourPackage selectedTourPackage;
	public TourPackageBean() {
	}

	@PostConstruct
	public void init() {
		// Load all tour packages from the database
		allTourPackages = tourPackageService.getAllTourPackages();
		
		 FacesContext context = FacesContext.getCurrentInstance();
	 HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		    Long id = (Long) session.getAttribute("id");

		    if (id != null) {
		    	initFormData(id);
		    }
		
		// Load the specific tour package based on the provided id from the URL
//	    FacesContext context = FacesContext.getCurrentInstance();
//	    String id = context.getExternalContext().getRequestParameterMap().get("id");
//	    if (id != null) {
//	        tourPackageVm = tourPackageService.initTourPackage(Long.parseLong(id));
//		
//	    }
		 // Load the specific tour package based on the provided id from the URL
//        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
//        if (id != null) {
//            tourPackage = tourPackageService.getTourPackageById(Long.parseLong(id));           
//            tourPackageVm.setId(tourPackage.getId());
//            tourPackageVm.setName(tourPackage.getName());
//            tourPackageVm.setAmount(tourPackage.getPrice().getAmount());
//            tourPackageVm.setCurrency(tourPackage.getPrice().getCurrency());
//            tourPackageVm.setCityName(tourPackage.getDestination().getCityName());
//            tourPackageVm.setState(tourPackage.getDestination().getState());
//            tourPackageVm.setCountry(tourPackage.getDestination().getCountry());
//            tourPackageVm.setThemeName(tourPackage.getTheme().getThemeName());
//        }
    }
	
	 public void initFormData(Long id) {
		 TourPackageFormViewModel temp = tourPackageService.initTourPackage(id);
	        if (temp == null) {
	        	this.tourPackageVm= temp;
	        }else {
	            FacesMessageUtil.addErrorMessage("Initialization failed: Tour package does not exist");
	        }
	    }
//	
//	 public void updatePackage() {
//			
//	       // Handle image processing if needed
//	       // ... Image processing ...
//	
//	   	tourPackageVm = tourPackageService.updateTourPackageWithImage(tourPackageVm);
//	 
//	       FacesMessageUtil.addErrorMessage("Update failed: ");
//	   }
	 public String updatePackage() {
	        // Call the service method to update the tour package
	        tourPackageVm = tourPackageService.updateTourPackageWithImage(tourPackageVm);

	        // Redirect to the tpUpdate outcome with the updated tour package ID
	        return "tpUpdate?faces-redirect=true&id=" + tourPackageVm.getId();
	    }

	  
	
	
	

	// Create
	public void createPackage() {
		lastTourPackageAdded = tourPackageService.createTourPackage(tourPackageVm);
		clear();
	}

	// List
	public void loadAllTourPackages() {
		allTourPackages = tourPackageService.getAllTourPackages();
	}
	
	
	

	
//	public String updatePackage() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//        String id = request.getParameter("id"); // Get the value of the "id" parameter
//
//        // Retrieve the tour package from the data source (e.g., database) using the ID
//        TourPackage tourPackage = tourPackageService.getTourPackageById(Long.parseLong(id));
//
//        // Update the tour package properties based on user inputs in the form
//        tourPackage.setName(tourPackageVm.getName());
//        tourPackage.getPrice().setAmount(tourPackageVm.getAmount());
//        tourPackage.getPrice().setCurrency(tourPackageVm.getCurrency());
//        tourPackage.getDestination().setCityName(tourPackageVm.getCityName());
//        tourPackage.getDestination().setState(tourPackageVm.getState());
//        tourPackage.getDestination().setCountry(tourPackageVm.getCountry());
//        tourPackage.getTheme().setThemeName(tourPackageVm.getThemeName());
//
//        // Call your service method to update the tour package
//        tourPackageService.update(tourPackage);
//
//        return "tpUpdate?faces-redirect=true&id=" + id; // Redirect to the tpUpdate outcome with the ID
//    }

//	public String updatePackage() {
//	    FacesContext context = FacesContext.getCurrentInstance();
//	    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
//        String id = request.getParameter("id"); // Get the value of the "id" parameter
//
//
//	    // Get the existing tour package from the service by its ID
//	    TourPackage existingTourPackage = tourPackageService.getTourPackageById(Long.parseLong(id));
//
//	    // Update the tour package properties based on user inputs in the form
//	    existingTourPackage.setName(tourPackageVm.getName());
//	    existingTourPackage.getPrice().setAmount(tourPackageVm.getAmount());
//	    existingTourPackage.getPrice().setCurrency(tourPackageVm.getCurrency());
//	    existingTourPackage.getDestination().setCityName(tourPackageVm.getCityName());
//	    existingTourPackage.getDestination().setState(tourPackageVm.getState());
//	    existingTourPackage.getDestination().setCountry(tourPackageVm.getCountry());
//	    existingTourPackage.getTheme().setThemeName(tourPackageVm.getThemeName());
//
//	    TourPackage updatedTourPackage = tourPackageService.updateTourPackageWithImage(tourPackageVm);
//
//	    // Map the updatedTourPackage to the viewModel
//	    mapTourPackageToViewModel(updatedTourPackage);
//
//	    return "tpUpdate?faces-redirect=true&id=" + id;
//	}


	private void mapTourPackageToViewModel(TourPackage tourPackage) {
	 
	    tourPackageVm.setName(tourPackage.getName());

	    // Map Price information
	    Price price = tourPackage.getPrice();
	    if (price != null) {
	        tourPackageVm.setAmount(price.getAmount());
	        tourPackageVm.setCurrency(price.getCurrency());
	    }

	    // Map Destination information
	    Destination destination = tourPackage.getDestination();
	    if (destination != null) {
	        tourPackageVm.setCityName(destination.getCityName());
	        tourPackageVm.setState(destination.getState());
	        tourPackageVm.setCountry(destination.getCountry());
	    }

	    // Map Theme information
	    Theme theme = tourPackage.getTheme();
	    if (theme != null) {
	        tourPackageVm.setThemeName(theme.getThemeName());
	    }	   
	}
	 public void deletePackage() {
	        FacesContext context = FacesContext.getCurrentInstance();
	        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	        String id = request.getParameter("id"); // Get the value of the "id" parameter

	        tourPackageService.deleteTourPackage(Long.parseLong(id));
	    }
	 
	
	
	
	//Update
//	public void updatePackage() {
//	    // Retrieve the tourPackageId from the view parameter
//	    FacesContext context = FacesContext.getCurrentInstance();
//	    String tourPackageName = context.getExternalContext().getRequestParameterMap().get("tourPackageName");
//
//	    // Check if the package already exists based on its unique identifier (e.g., name)
//	    TourPackage existingPackage = tourPackageService.getTourPackageByName(tourPackageName);
//
//	    if (existingPackage != null) {
//	        // Update the existing package with the values from the view model
//	        existingPackage.setName(tourPackageVm.getName());
//	        existingPackage.getPrice().setAmount(tourPackageVm.getAmount());
//	        existingPackage.getPrice().setCurrency(tourPackageVm.getCurrency());
//	        existingPackage.getDestination().setCityName(tourPackageVm.getCityName());
//	        existingPackage.getDestination().setState(tourPackageVm.getState());
//	        existingPackage.getDestination().setCountry(tourPackageVm.getCountry());
//	        existingPackage.getTheme().setThemeName(tourPackageVm.getThemeName());
//
//	        // Now, when you submit the form, the existing package will be updated in the database
//	        // using the createOrUpdate method in the DAO
//	        tourPackageService.updateTourPackage(tourPackageVm);
//	    }
//	}
//	
	//Delete
//	public void deletePackage() {
//	    FacesContext context = FacesContext.getCurrentInstance();
//	    String tourPackageIdString = context.getExternalContext().getRequestParameterMap().get("tourPackageId");
//	    Long tourPackageId = Long.valueOf(tourPackageIdString);
//	    
//	    tourPackageService.deleteTourPackage(tourPackageId);
//	}



	// Search
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

	// Clear after creating new id (have to check again)
	public void clear() {
		tourPackageVm = new TourPackageFormViewModel();
	}

	// GETTERS SETTERS
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
	
}


// both have stackoverflow
//<script>
//function displayResults(results) {
//    // Implement the logic to display the search results on the page
//    // For example, you can loop through the results and create HTML elements to show the results.
//
//    // For demonstration purposes, let's log the results to the console
//    console.log(results);
//}
//
//document.getElementById('formId:searchBtn').addEventListener('click', function() {
//    var jsonResults = document.getElementById('formId:jsonResults').value;
//    var results = JSON.parse(jsonResults);
//    displayResults(results);
//});
//</script>  

//<script>
//function displayResults(results) {
//    // Implement the logic to display the search results on the page
//    // For example, you can loop through the results and create HTML elements to show the results.
//
//    // For demonstration purposes, let's log the results to the console
//    console.log(results);
//}
//
//// Function to handle the search button click
//function performSearch() {
//    // Perform the search action using JSF form submit
//    document.getElementById('formId:searchBtn').click();
//}
//
//// Function to be executed after the form is submitted and results are available
//function onSearchComplete() {
//    var jsonResults = document.getElementById('formId:jsonResults').value;
//    var results = JSON.parse(jsonResults);
//    displayResults(results);
//}
//
//// Add an event listener to execute onSearchComplete when the form is submitted
//document.getElementById('formId').addEventListener('submit', onSearchComplete);
//</script>
