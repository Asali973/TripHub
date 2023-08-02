package triphub.dao.product;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import javax.persistence.NoResultException;

import javax.persistence.PersistenceContext;

import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import triphub.entity.product.Theme;


@Stateless
public class ThemeDAO {
	@PersistenceUnit

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
	 
	 public Theme findByThemeName(String themeName) {
		    TypedQuery<Theme> query = em.createQuery("SELECT t FROM Theme t WHERE t.themeName = :themeName", Theme.class);
		    query.setParameter("themeName", themeName);
		    
		    try {
		        return query.getSingleResult();
		    } catch (NoResultException e) {
		        // Theme with the given name does not exist
		        return null;
		    }
		}
	 public  Theme findOrCreateThemeByName(String themeName) {
		    Theme existingTheme = findByThemeName(themeName);

		    if (existingTheme == null) {
		        // Theme with the given name doesn't exist, create a new one
		        Theme newTheme = new Theme();
		        newTheme.setThemeName(themeName);
		        // Set any other properties if needed
		        em.persist(newTheme); // Persist the new theme
		        return newTheme;
		    } else {
		        return existingTheme;
		    }
		}

}
