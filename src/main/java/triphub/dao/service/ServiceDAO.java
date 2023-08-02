package triphub.dao.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.service.Service;
import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.restaurant.Restaurant;
import triphub.entity.product.service.transportation.Transportation;
import triphub.viewModel.ServiceViewModel;

public class ServiceDAO {

	@PersistenceContext
	private EntityManager em;

	public ServiceDAO() {
		
	}

	public Service create(ServiceViewModel servicevm) {
		Service service = new Service();
		service.setAccomodation(servicevm.getAccommodation());
		service.setRestaurant(servicevm.getRestaurant());
		service.setTransportation(servicevm.getTransportation());
		service.setPrice(servicevm.getPrice());
		service.setAvailability(servicevm.isAvailability());
		service.setAvailableFrom(servicevm.getAvailableFrom());
		service.setAvailableTill(servicevm.getAvailableTill());
		service.setStartDate(servicevm.getStartDate());
		service.setEndDate(servicevm.getEndDate());
		em.persist(service);
		return service;
	}
	
	public Service read(Long id) {
		return em.find(Service.class, id);
	}

	public Service update(Service service) {
		return em.merge(service);
	}

	public void delete(Long id) {
		Service service = em.find(Service.class, id);
		if (service != null) {
			em.remove(service);
		}
	}

	public List<Service> findAccommodation(Accommodation accommodation) {
		TypedQuery<Service> query = em.createQuery(
				"SELECT a FROM Accommodation a WHERE a.accommodation = :accommodation", Service.class);
		query.setParameter("accommodation", accommodation);
		return query.getResultList();
	}
	
	public List<Service> findRestaurant(Restaurant restaurant) {
		TypedQuery<Service> query = em.createQuery(
				"SELECT r FROM Restaurant r WHERE r.restaurant = :restaurant", Service.class);
		query.setParameter("restaurant", restaurant);
		return query.getResultList();
	}
	
	public List<Service> findTransportation(Transportation transportation) {
		TypedQuery<Service> query = em.createQuery(
				"SELECT t FROM Transportation t WHERE t.transportation = :transportation", Service.class);
		query.setParameter("transportation", transportation);
		return query.getResultList();
	}
	
	public List<Service> findByAvailabilityFrom(Date availableFrom){
		TypedQuery<Service> query = em.createQuery(
				"SELECT avf FROM AvailableFrom avf WHERE avf.availableFrom = :availableFrom", Service.class);
		query.setParameter("availableFrom", availableFrom);
		return query.getResultList();
	}
	
	public List<Service> findByAvailabilityTill(Date availableTill){
		TypedQuery<Service> query = em.createQuery(
				"SELECT avt FROM AvailableTill avt WHERE avt.availableTill", Service.class);
		query.setParameter("availableTill", availableTill);
		return query.getResultList();
	}

	public List<Service> getAllServices() {
		TypedQuery<Service> query = em.createQuery("SELECT s FROM Service s", Service.class);
		return query.getResultList();
	}
}
