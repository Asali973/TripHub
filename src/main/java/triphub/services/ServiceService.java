package triphub.services;

import java.math.BigDecimal;
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
import triphub.entity.subservices.TransportationType;
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

	public List<Accommodation> advancedSearchAccommodations(String city, String country, BigDecimal minPrice,
            BigDecimal maxPrice, String name, AccommodationType accommodationType) {
		return serviceDAO.advancedSearchAccommodations(city, country, minPrice, maxPrice, name,
				accommodationType);
	}

	public List<Transportation> advancedSearchTransportations(String departureCity, String departureCountry, String arrivalCity, String arrivalCountry,
			BigDecimal minPrice, BigDecimal maxPrice, String name, TransportationType transportationType) {
		return serviceDAO.advancedSearchTransportations(departureCity, departureCountry, arrivalCity, arrivalCountry, minPrice, maxPrice, name,
				transportationType);
	}

	public List<Restaurant> advancedSearchRestaurants(String name, String city, String country) {
		return serviceDAO.advancedSearchRestaurants(name, city, country);
	}

}