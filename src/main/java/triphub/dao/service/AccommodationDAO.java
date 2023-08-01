package triphub.dao.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.accommodation.AccommodationType;
import triphub.entity.util.Address;
import triphub.helpers.RegistrationException;
import triphub.viewModel.AccommodationViewModel;


public class AccommodationDAO {
	@PersistenceUnit
	private EntityManager em;

	public AccommodationDAO(EntityManager em) {
	this.em = em;
	
	}
	
	public Accommodation create(AccommodationViewModel accommodationVm) {
		Accommodation accommodation = new Accommodation();
		accommodation.setNameAccommodation(accommodationVm.getNameAccommodation());
		accommodation.setAddress(accommodationVm.getAddress());
		accommodation.setAccommodationType(accommodationVm.getAccommodationType());

		em.persist(accommodation);
		return accommodation;
	}
	// Méthode pour modifer une entité Accommodation
	 public Accommodation updateAccommodation(Accommodation accommodation) {
	        // Vérifier si l'entité existe dans la base de données
	        Accommodation existingAccommodation = em.find(Accommodation.class, accommodation.getId());

	        if (existingAccommodation != null) {
	        	
	            // Copier les nouvelles informations dans l'entité existante
	            existingAccommodation.setAccommodation(accommodation.getAccommodation());
	            existingAccommodation.setAddress(accommodation.getAddress());
	            
	            // Mettre à jour l'entité dans la base de données
	            em.merge(existingAccommodation);
	        
	        }
			return existingAccommodation;
	    }
	
	// Méthode pour supprimer une entité Accommodation en utilisant son id
    public void deleteAccommodation(Long id) {
        // Rechercher l'entité Accommodation par son id
        Accommodation accommodationToDelete = em.find(Accommodation.class, id);

        // Vérifier si l'entité existe
        if (accommodationToDelete != null) {
            // Supprimer l'entité de la base de données
            em.remove(accommodationToDelete);
        }
    }
    
    public Accommodation findAccommodationByName(String nameAccommodation)  {
		TypedQuery<Accommodation> query = em.createQuery("SELECT a FROM Accommodation a WHERE a.nameAccommodation = :nameAccommodation",Accommodation.class);
		query.setParameter("nameAccommodation", nameAccommodation);
	
			return query.getSingleResult();
		
	}
    
    
    public List<Accommodation> findByType(AccommodationType AccommodationType){
		TypedQuery<Accommodation> query = em.createQuery("SELECT a FROM Accommodation a WHERE a.accommodation = :accommodation", Accommodation.class);
		query.setParameter("accommodation", AccommodationType);
		return query.getResultList();		
	}
    
//    public Optional<Accommodation> search(Address address, AccommodationType accommodationType) {
//    	String reqSelect="SELECT * FROM Client WHERE adress=? AND accommodationType=?";
//    	Query query=em.createNativeQuery(reqSelect, Accommodation.class);
//    	query =query.setParameter(1, address);
//    	query= query.setParameter(2, accommodationType);
//        return Optional.ofNullable((Accommodation)query.getSingleResult());
//}
    
}
