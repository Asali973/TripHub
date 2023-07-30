package triphub.dao.product;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import triphub.entity.product.Theme;
import triphub.entity.product.TourPackage;


public class TourPackageDAO {
	private EntityManager em;
		
	public TourPackageDAO(EntityManager em) {
		this.em = em;
	}
	//basic, to be removed later
	public TourPackage create(TourPackage tourPackage) {
		em.persist(tourPackage);
		return tourPackage;
	}
	
	public TourPackage read(Long id) {
		return em.find(TourPackage.class, id);
	}
	//basic, to be removed later
	public TourPackage update(TourPackage tourPackage) {
	    return em.merge(tourPackage);
	}
	
	public void delete(Long id) {
	    TourPackage tourPackage = em.find(TourPackage.class, id);
	    if (tourPackage != null) {
	        em.remove(tourPackage);
	    }
	}
	 public TourPackage findPackageByName(String packageName) {
	        TypedQuery<TourPackage> query = em.createQuery(
	            "SELECT tp FROM TourPackage tp WHERE tp.name = :name", TourPackage.class
	        );
	        query.setParameter("name", packageName);

	        List<TourPackage> packages = query.getResultList();
	        return packages.isEmpty() ? null : packages.get(0);
	    }
	 
	 public List<TourPackage> getAllTourPackages() {
		 TypedQuery<TourPackage> query = em.createQuery("SELECT tp FROM TourPackage tp", TourPackage.class);
		 
         return query.getResultList();       
	 }
	 
	 public TourPackage createOrUpdate(TourPackage tourPackage) {
	        // Check if the package already exists based on its unique identifier (e.g., name)
	        TourPackage existingPackage = findPackageByName(tourPackage.getName());

	        if (existingPackage != null) {
	        	
	        	// Detach the existing package from the persistence context to avoid hibernate tracking changes in the images collection
	            em.detach(existingPackage);
	            
	            boolean isModified = false;

	            // Compare attributes and update if necessary
	            if (!Objects.equals(existingPackage.getPrice(), tourPackage.getPrice())) {
	                existingPackage.setPrice(tourPackage.getPrice());
	                isModified = true;
	            }

	            if (!Objects.equals(existingPackage.getDestination(), tourPackage.getDestination())) {
	                existingPackage.setDestination(tourPackage.getDestination());
	                isModified = true;
	            }

	            if (!Objects.equals(existingPackage.getTheme(), tourPackage.getTheme())) {
	                existingPackage.setTheme(tourPackage.getTheme());
	                isModified = true;
	            }
	            // If there are images in the new tourPackage, update the existing package
	            // without triggering the orphan removal behavior
	            //when the front is developped, we can rewrite it as above
	            if (tourPackage.getImages() != null && !tourPackage.getImages().isEmpty()) {
	                existingPackage.setImages(tourPackage.getImages());
	                isModified = true;
	            }
	            //case 1: If changes were made, update the package
	            if (isModified) {
	                return em.merge(existingPackage);
	            }
	        } else {
	            // case 2: if package doesn't exist, persist it as a new entity
	            em.persist(tourPackage);
	            return tourPackage;
	        }

	        // case 3: If no changes were made, return the existing package entity
	        return existingPackage;
	    }
}
