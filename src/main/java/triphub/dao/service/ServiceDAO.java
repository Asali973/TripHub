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
		    Date availableFrom, Date availableTill, String name, String city, String country, AccommodationType accommodationType) {
		    
		    TypedQuery<Accommodation> query = em.createQuery(
		        "SELECT a FROM Accommodation a WHERE a.service.availableFrom <= :availableFrom " +
		        "AND a.service.availableTill >= :availableTill " +
		        "AND a.name LIKE :name " +
		        "AND a.address.city LIKE :city " +
		        "AND a.address.country LIKE :country " +
		        "AND a.accommodationType = :accommodationType", Accommodation.class);
		    
		    query.setParameter("availableFrom", availableFrom);
		    query.setParameter("availableTill", availableTill);
		    query.setParameter("name", "%" + name + "%"); // Using % for partial matching
		    query.setParameter("city", "%" + city + "%"); // Using % for partial matching
		    query.setParameter("country", "%" + country + "%"); // Using % for partial matching
		    query.setParameter("accommodationType", accommodationType);
		    
		    return query.getResultList();
		}



    public List<Transportation> advancedSearchTransportations(Date availableFrom, Date availableTill, ServiceType type) {
        TypedQuery<Transportation> query = em.createQuery(
                "SELECT t FROM Transportation t WHERE t.availableFrom >= :availableFrom AND t.availableTill <= :availableTill AND t.type = :type",
                Transportation.class);
        query.setParameter("availableFrom", availableFrom);
        query.setParameter("availableTill", availableTill);
        query.setParameter("type", type);
        return query.getResultList();
    }

    public List<Restaurant> advancedSearchRestaurants(Date availableFrom, Date availableTill, ServiceType type) {
        TypedQuery<Restaurant> query = em.createQuery(
                "SELECT r FROM Restaurant r WHERE r.availableFrom >= :availableFrom AND r.availableTill <= :availableTill AND r.type = :type",
                Restaurant.class);
        query.setParameter("availableFrom", availableFrom);
        query.setParameter("availableTill", availableTill);
        query.setParameter("type", type);
        return query.getResultList();
    }
}
