package triphub.dao.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import triphub.entity.product.Theme;
import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.transportation.Transportation;

@Stateless
public class TransportationDAO {
@PersistenceUnit
	private EntityManager em;

	public TransportationDAO(EntityManager em) {
		this.em = em;
	}
	
	public Transportation create(Transportation transportation) {
		em.persist(transportation);
		return transportation;
	}
	
	public Transportation read(Long id) {
		return em.find(Transportation.class, id);
	}
	
	public Transportation update(Transportation transportation) {
	    return em.merge(transportation);
	}
	
	public void delete(Long id) {
		Transportation transportation = em.find(Transportation.class, id);
	    if (transportation != null) {
	        em.remove(transportation);
	    }
	}
	
	 public List<Transportation> getAllTransportation() {
		 TypedQuery<Transportation> query = em.createQuery("SELECT t FROM Transportation", Transportation.class);
		 
         return query.getResultList();       
	 }

}
