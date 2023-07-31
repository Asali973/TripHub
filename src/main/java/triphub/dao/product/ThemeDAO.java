package triphub.dao.product;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.Theme;


@Stateless
public class ThemeDAO {
	@PersistenceContext
	private EntityManager em;
	
	public ThemeDAO() {}
	
	public ThemeDAO(EntityManager em) {
		this.em = em;
	}
	
	public Theme create(Theme theme) {
		em.persist(theme);
		return theme;
	}
	
	public Theme read(Long id) {
		return em.find(Theme.class, id);
	}
	
	public Theme update(Theme theme) {
	    return em.merge(theme);
	}
	
	public void delete(Long id) {
		Theme theme = em.find(Theme.class, id);
	    if (theme != null) {
	        em.remove(theme);
	    }
	}
	
	 public List<Theme> getAllThemes() {
		 TypedQuery<Theme> query = em.createQuery("SELECT t FROM Theme t", Theme.class);
		 
         return query.getResultList();       
	 }
}
