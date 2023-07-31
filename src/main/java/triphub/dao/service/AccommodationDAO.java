package triphub.dao.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.accommodation.AccommodationType;
import triphub.entity.util.Address;


public class AccommodationDAO {

	private EntityManager em;

	public AccommodationDAO(EntityManager em) {
	this.em = em;
	
	}
	
	public Accommodation create(Accommodation accommodation) {
		em.persist(accommodation);
		return accommodation;
	}
	// Méthode pour modifer une entité Accommodation
	 public void updateAccommodation(Accommodation accommodation) {
	        // Vérifier si l'entité existe dans la base de données
	        Accommodation existingAccommodation = em.find(Accommodation.class, accommodation.getId());

	        if (existingAccommodation != null) {
	        	
	            // Copier les nouvelles informations dans l'entité existante
	            existingAccommodation.setAccommodation(accommodation.getAccommodation());
	            existingAccommodation.setAddress(accommodation.getAddress());
	            
	            // Mettre à jour l'entité dans la base de données
	            em.merge(existingAccommodation);
	        } else {
	            // Gérer le cas où l'entité n'a pas été trouvée dans la base de données
	            throw new IllegalArgumentException("Accommodation with ID " + accommodation.getId() + " not found.");
	        }
	    }
	
	// Méthode pour supprimer une entité Accommodation en utilisant son id
    public void deleteAccommodation(Long id) {
        // Rechercher l'entité Accommodation par son id
        Accommodation accommodationToDelete = em.find(Accommodation.class, id);

        // Vérifier si l'entité existe
        if (accommodationToDelete != null) {
            // Supprimer l'entité de la base de données
            em.remove(accommodationToDelete);
        } else {
            // Gérer le cas où l'entité n'a pas été trouvée dans la base de données
            throw new IllegalArgumentException("Accommodation with ID " + id + " not found.");
        }
    }
    
    public Optional<Accommodation> search(Address address, AccommodationType accommodationType) {
    	String reqSelect="SELECT * FROM Client WHERE adress=? AND accommodationType=?";
    	Query query=em.createNativeQuery(reqSelect, Accommodation.class);
    	query =query.setParameter(1, address);
    	query= query.setParameter(2, accommodationType);
        return Optional.ofNullable((Accommodation)query.getSingleResult());
}
    
}
