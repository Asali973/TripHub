package triphub.dao.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import javax.persistence.TypedQuery;

import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.accommodation.AccommodationType;
import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;

@Stateless
public class AccommodationDAO {
	
	@PersistenceContext
	private EntityManager em;

	
	public AccommodationDAO(EntityManager em) {
	this.em = em;
	
	}
	
	public AccommodationDAO() {}
	
	public Accommodation createAccommodation(SubServicesViewModel accommodationVm) {
		
		// creer Accommodation 
		Accommodation accommodation = new Accommodation();
		
		accommodation.setName(accommodationVm.getName());
		accommodation.setDescription(accommodationVm.getDescription());
		accommodation.setAccommodationType(accommodationVm.getAccommodationType());
		
		
//		Picture picture = new Picture();
//		picture.setLink(formService.getLink());
//		accommodation.setPicture(picture);
		
		Address addressAccommodation = new Address();
		addressAccommodation.setNum(accommodationVm.getNum());
		addressAccommodation.setStreet(accommodationVm.getStreet());
		addressAccommodation.setCity(accommodationVm.getCity());
		addressAccommodation.setState(accommodationVm.getState());
		addressAccommodation.setCountry(accommodationVm.getCountry());
		accommodation.setAddresAccommodation(addressAccommodation);
		
		
		
//		em.persist(picture);
		em.persist(addressAccommodation);
		em.persist(accommodation);
		
		accommodation.setAddresAccommodation(addressAccommodation);
		
		return accommodation;
	}
	// Méthode pour modifer une entité Accommodation
	 public void updateAccommodation(Accommodation accommodation) {
	        if (accommodation != null) {
	            // Mettre à jour l'entité dans la base de données
	            em.merge(accommodation);
	        
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
