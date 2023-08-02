package triphub.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import triphub.dao.service.ServiceDAO;
import triphub.entity.product.service.Service;
import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.restaurant.Restaurant;
import triphub.entity.product.service.transportation.Transportation;

@Stateless
public class ServiceService {
	
	@Inject
	private ServiceDAO serviceDAO;

	public ServiceService() {
	
	}

	public ServiceService(ServiceDAO serviceDAO) {
		this.serviceDAO = serviceDAO;
	}
	
	public Service read(Long id) {
		return serviceDAO.read(id);
	}
	
	public Service update(Service service) {
		return serviceDAO.update(service);
	}
	
	public void delete(Long id) {
		serviceDAO.delete(id);
	}
	
	public List<Service> findAccommodation(Accommodation accommodation) {
		return serviceDAO.findAccommodation(accommodation);
	}
	
	public List<Service> findRestaurant(Restaurant restaurant) {
		return serviceDAO.findRestaurant(restaurant);
	}
	
	public List<Service> findTransportation(Transportation transportation) {
		return serviceDAO.findTransportation(transportation);
	}
	
	public List<Service> findByAvailabilityFrom(Date availableFrom){
		return serviceDAO.findByAvailabilityFrom(availableFrom);
	}

	public List<Service> findByAvailabilityTill(Date availableTill){
		return serviceDAO.findByAvailabilityTill(availableTill);
	}
	
	public List<Service> getAllServices() {
		return serviceDAO.getAllServices();
	}
}
