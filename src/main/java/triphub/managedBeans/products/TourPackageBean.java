package triphub.managedBeans.products;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import triphub.entity.product.TourPackage;
import triphub.services.TourPackageService;
import triphub.viewModel.TourPackageFormViewModel;

@Named
@RequestScoped
public class TourPackageBean implements Serializable {

	@Inject
	private TourPackageService tourPackageService;
	private static final long serialVersionUID = 1L;

	private TourPackageFormViewModel tourPackageVm = new TourPackageFormViewModel();
	private List<TourPackage> allTourPackages;
	private String jsonResults;
	private String selectedPriceRange;

	public TourPackageBean() {
	}

	@PostConstruct
	public void init() {
		// Load all tour packages from the database
		allTourPackages = tourPackageService.getAllTourPackages();
	}

	public void createPackage() {
		tourPackageService.createTourPackage(tourPackageVm);
	}

	public void loadAllTourPackages() {
		allTourPackages = tourPackageService.getAllTourPackages();
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

	public List<BigDecimal> getMinPriceOptions() {
	    return generateNumberOptions(tourPackageVm.getMaxPrice());
	}

	public List<BigDecimal> getMaxPriceOptions() {
	    return generateNumberOptions(tourPackageVm.getMinPrice());
	}


// not successful , have stackoverflow errors
	private String convertResultsToJson(List<TourPackage> searchResults) {
		try {
			Gson gson = new Gson();
			return gson.toJson(searchResults);
		} catch (Exception e) {
			e.printStackTrace();
			return "[]"; // Return an empty JSON array in case of an error
		}
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

	public String getJsonResults() {
		return jsonResults;
	}

	public void setJsonResults(String jsonResults) {
		this.jsonResults = jsonResults;
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
