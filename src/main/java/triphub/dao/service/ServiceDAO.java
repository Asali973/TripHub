package triphub.dao.service;


import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.service.Service;
import triphub.entity.product.service.ServiceType;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.AccommodationType;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.entity.subservices.TransportationType;

@Stateless
public class ServiceDAO  {
	
	
	@PersistenceContext
	private EntityManager em;
	
	
	public ServiceDAO() {
		
	}
	
	
	public Service createService(Service service) {
		em.persist(service);
		return service;
	}


	public Service read(Long id) {
		return em.find(Service.class, id);
	}
	
	public Service findById(Long id) {
		
		try {
			return em.find(Service.class, id);
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Service> getAll() {
		TypedQuery<Service> query = em.createQuery("SELECT s FROM Service s", Service.class);

		return query.getResultList();
	}
	
	public List<Accommodation> advancedSearchAccommodations(
		   String name, String city, String country, AccommodationType accommodationType) {
		    
		    TypedQuery<Accommodation> query = em.createQuery(
		        "SELECT a FROM Accommodation a WHERE a.name LIKE :name " +
		        "AND a.address.city LIKE :city " +
		        "AND a.address.country LIKE :country " +
		        "AND a.accommodationType = :accommodationType", Accommodation.class);

		    query.setParameter("name", "%" + name + "%"); // Using % for partial matching
		    query.setParameter("city", "%" + city + "%"); // Using % for partial matching
		    query.setParameter("country", "%" + country + "%"); // Using % for partial matching
		    query.setParameter("accommodationType", accommodationType);
		    
		    return query.getResultList();
		}



	public List<Transportation> advancedSearchTransportations(
	        String name, 
	        String departureCity, String departureCountry, String arrivalCity, String arrivalCountry,
	        TransportationType transportationType) {

	    TypedQuery<Transportation> query = em.createQuery(
	            "SELECT t FROM Transportation t " +
	            "JOIN t.departure d " +  // Joining with the departure Address entity
	            "JOIN t.arrival a " +    // Joining with the arrival Address entity
	            "WHERE t.name LIKE :name " +
	            "AND d.city LIKE :departureCity " +
	            "AND d.country LIKE :departureCountry " +
	            "AND a.city LIKE :arrivalCity " +
	            "AND a.country LIKE :arrivalCountry " +
	            "AND t.transportationType = :transportationType", Transportation.class);

	    query.setParameter("name", "%" + name + "%"); // Using % for partial matching
	    query.setParameter("departureCity", "%" + departureCity + "%");
	    query.setParameter("departureCountry", "%" + departureCountry + "%");
	    query.setParameter("arrivalCity", "%" + arrivalCity + "%");
	    query.setParameter("arrivalCountry", "%" + arrivalCountry + "%");
	    query.setParameter("transportationType", transportationType);
	    
	    return query.getResultList();
	}





	public List<Restaurant> advancedSearchRestaurants(
	        String name, String city, String country) {

	    TypedQuery<Restaurant> query = em.createQuery(
	            "SELECT r FROM Restaurant r WHERE r.name LIKE :name " +
	            "AND r.address.city LIKE :city " +
	            "AND r.address.country LIKE :country ", Restaurant.class);

	    query.setParameter("name", "%" + name + "%"); // Using % for partial matching
	    query.setParameter("city", "%" + city + "%");
	    query.setParameter("country", "%" + country + "%");
	    
	    return query.getResultList();
	}

}
