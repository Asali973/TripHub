package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import triphub.entity.product.service.Service;
import triphub.entity.product.service.ServiceType;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.AccommodationType;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.entity.subservices.TransportationType;
import triphub.helpers.FacesMessageUtil;
import triphub.services.ServiceService;
import triphub.viewModel.SubServicesViewModel;

@Named("serviceBean")
@RequestScoped
public class ServiceBean implements Serializable {

	@Inject
	private ServiceService serviceService;

	@Inject
	private SubServicesViewModel servicevm;

	private static final long serialVersionUID = 1L;

	private ServiceType selectedServiceType;

	private List<Service> allServices;

	private Date searchAvailableFrom;
	private Date searchAvailableTill;
	private ServiceType accommodationType;
	private ServiceType transportationType;
	private ServiceType restaurantType;

	private List<Transportation> transportationList;
	private List<Restaurant> restaurantList;
	
	private List<Accommodation> accommodationList;
	private AccommodationType selectedAccommodationType;
    private String searchAccommodationName;
    private String searchAccommodationCity;
    private String searchAccommodationCountry;
    


	public ServiceBean() {
	}

	public ServiceBean(ServiceService serviceService, SubServicesViewModel servicevm, List<Service> allServices) {
		super();
		this.serviceService = serviceService;
		this.servicevm = servicevm;
		this.setAllServices(allServices);
	}

	@PostConstruct
	public void init() {
		// Initialize other properties
		selectedServiceType = null; // Initialize the selected service type
	    selectedAccommodationType = null; // Initialize the selected accommodation type
		accommodationList = new ArrayList<>();
		transportationList = new ArrayList<>();
		restaurantList = new ArrayList<>();// Initialize the selected service type
	}

	public List<Service> getFilteredServices() {
		List<Service> filteredServices = new ArrayList<>();
		if (selectedServiceType == null) {
			filteredServices = allServices;
		} else {
			for (Service service : allServices) {
				if (service.getType() == selectedServiceType) {
					filteredServices.add(service);
				}
			}
		}
		return filteredServices;
	}

	public void performAdvancedSearch() {
	    if (selectedServiceType == ServiceType.ACCOMMODATION) {
	        accommodationList = serviceService.advancedSearchAccommodations(
	            searchAvailableFrom, searchAvailableTill, searchAccommodationName, searchAccommodationCity, searchAccommodationCountry, selectedAccommodationType);
	    } else if (selectedServiceType == ServiceType.TRANSPORTATION) {
	        transportationList = serviceService.advancedSearchTransportations(
	            searchAvailableFrom, searchAvailableTill, selectedServiceType);
	    } else if (selectedServiceType == ServiceType.RESTAURANT) {
	        restaurantList = serviceService.advancedSearchRestaurants(
	            searchAvailableFrom, searchAvailableTill, selectedServiceType);
	    }
	}

	public AccommodationType[] getAllAccommodationTypes() {
        return AccommodationType.values();
    }

	public List<Service> getAllServices() {
		return allServices;
	}

	public void setAllServices(List<Service> allServices) {
		this.allServices = allServices;
	}

	public ServiceType[] getAllServiceTypes() {
		return ServiceType.values();
	}

	public SubServicesViewModel getServicevm() {
		return servicevm;
	}

	public void setServicevm(SubServicesViewModel servicevm) {
		this.servicevm = servicevm;
	}

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public ServiceType getSelectedServiceType() {
		return selectedServiceType;
	}

	public void setSelectedServiceType(ServiceType selectedServiceType) {
		this.selectedServiceType = selectedServiceType;
	}

	public Date getSearchAvailableFrom() {
		return searchAvailableFrom;
	}

	public void setSearchAvailableFrom(Date searchAvailableFrom) {
		this.searchAvailableFrom = searchAvailableFrom;
	}

	public Date getSearchAvailableTill() {
		return searchAvailableTill;
	}

	public void setSearchAvailableTill(Date searchAvailableTill) {
		this.searchAvailableTill = searchAvailableTill;
	}

	public ServiceType getAccommodationType() {
		return accommodationType;
	}

	public void setAccommodationType(ServiceType accommodationType) {
		this.accommodationType = accommodationType;
	}

	public ServiceType getTransportationType() {
		return transportationType;
	}

	public void setTransportationType(ServiceType transportationType) {
		this.transportationType = transportationType;
	}

	public ServiceType getRestaurantType() {
		return restaurantType;
	}

	public void setRestaurantType(ServiceType restaurantType) {
		this.restaurantType = restaurantType;
	}

	public List<Accommodation> getAccommodationList() {
		return accommodationList;
	}

	public void setAccommodationList(List<Accommodation> accommodationList) {
		this.accommodationList = accommodationList;
	}

	public List<Transportation> getTransportationList() {
		return transportationList;
	}

	public void setTransportationList(List<Transportation> transportationList) {
		this.transportationList = transportationList;
	}

	public List<Restaurant> getRestaurantList() {
		return restaurantList;
	}

	public void setRestaurantList(List<Restaurant> restaurantList) {
		this.restaurantList = restaurantList;
	}

	public AccommodationType getSelectedAccommodationType() {
	    return selectedAccommodationType;
	}

	public void setSelectedAccommodationType(AccommodationType selectedAccommodationType) {
	    this.selectedAccommodationType = selectedAccommodationType;
	}

	public String getSearchAccommodationName() {
		return searchAccommodationName;
	}

	public void setSearchAccommodationName(String searchAccommodationName) {
		this.searchAccommodationName = searchAccommodationName;
	}

	public String getSearchAccommodationCity() {
		return searchAccommodationCity;
	}

	public void setSearchAccommodationCity(String searchAccommodationCity) {
		this.searchAccommodationCity = searchAccommodationCity;
	}

	public String getSearchAccommodationCountry() {
		return searchAccommodationCountry;
	}

	public void setSearchAccommodationCountry(String searchAccommodationCountry) {
		this.searchAccommodationCountry = searchAccommodationCountry;
	}
	
	

}
