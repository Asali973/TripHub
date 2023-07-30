package triphub.dao.product;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import triphub.entity.product.Price;
import triphub.entity.product.Theme;



public class PriceDAO {
	
	private EntityManager em;
	
	public PriceDAO(EntityManager em) {
		this.em = em;
	}
	
	public Price create(Price price) {
		em.persist(price);
		return price;
	}
	
	public Price read(Long id) {
		return em.find(Price.class, id);
	}
	
	public Price update(Price price) {
	    return em.merge(price);
	}
	
	public void delete(Long id) {
		Price price = em.find(Price.class, id);
	    if (price != null) {
	        em.remove(price);
	    }
	}
	public List<Price> getAllPrices() {
		 TypedQuery<Price> query = em.createQuery("SELECT p FROM Price p", Price.class);
		 
        return query.getResultList();
      
	 }

}
