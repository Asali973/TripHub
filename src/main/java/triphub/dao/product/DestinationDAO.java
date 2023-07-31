package triphub.dao.product;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import triphub.entity.product.Destination;
import triphub.entity.product.Theme;


public class DestinationDAO {
	
	private EntityManager em;
	
	public DestinationDAO(EntityManager em) {
		this.em = em;
	}
	
	public Destination create(Destination destination) {
		em.persist(destination);
		return destination;
	}
	
	public Destination read(Long id) {
		return em.find(Destination.class, id);
	}
	
	public Destination update(Destination destination) {
	    return em.merge(destination);
	}
	
	public void delete(Long id) {
		Destination destination = em.find(Destination.class, id);
	    if (destination != null) {
	        em.remove(destination);
	    }
	}
	
	 public List<Destination> getAllDestinations() {
		 TypedQuery<Destination> query = em.createQuery("SELECT d FROM Destination d", Destination.class);
		 
         return query.getResultList();
       
	 }
	
}
