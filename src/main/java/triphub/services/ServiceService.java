package triphub.services;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import triphub.dao.service.ServiceDAO;
import triphub.entity.product.service.Service;
import triphub.entity.product.service.ServiceType;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.AccommodationType;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.viewModel.SubServicesViewModel;

@ApplicationScoped
public class ServiceService {

	@Inject
	private ServiceDAO serviceDAO;

	public Service createService(Service service) {
		return serviceDAO.createService(service);
	}

	public Service read(Long id) {
		return serviceDAO.read(id);
	}

	public Service findById(Long id) {
		return serviceDAO.findById(id);
	}

	public List<Service> getAll() {
		return serviceDAO.getAll();
	}
	
	public SubServicesViewModel initService(Long id) {
		 Service service = serviceDAO.findById(id);
	        if (service == null) {
	            return null;
	        }
	        return service.initServiceViewModel();
	}
	
	public List<Accommodation> advancedSearchAccommodations(
		    Date availableFrom, Date availableTill, String accommodationName,
		    String accommodationCity, String accommodationCountry, AccommodationType accommodationType) {
			return serviceDAO.advancedSearchAccommodations(availableFrom, availableTill, accommodationName, accommodationCity, accommodationCountry, accommodationType);
	}


	    public List<Transportation> advancedSearchTransportations(Date availableFrom, Date availableTill, ServiceType type) {
	        return serviceDAO.advancedSearchTransportations(availableFrom, availableTill, type);
	    }

	    public List<Restaurant> advancedSearchRestaurants(Date availableFrom, Date availableTill, ServiceType type) {
	        return serviceDAO.advancedSearchRestaurants(availableFrom, availableTill, type);
	    }

}