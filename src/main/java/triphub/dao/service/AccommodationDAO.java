package triphub.dao.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;

import triphub.entity.product.Price;
import triphub.entity.product.TourPackage;
import triphub.entity.product.service.Service;
import triphub.entity.product.service.ServiceInterface;

import triphub.entity.product.service.ServiceType;
import triphub.entity.subservices.Accommodation;
import triphub.entity.user.Organizer;
import triphub.entity.user.Provider;
import triphub.entity.util.Address;
import triphub.entity.util.Calendar;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;

@Stateless
public class AccommodationDAO {

	@PersistenceContext
	private EntityManager em;

	public AccommodationDAO(EntityManager em) {
		this.em = em;

	}

	public AccommodationDAO() {
	}

	public Accommodation create(SubServicesViewModel accommodationVm, Long userId, String userType) {


	    // Create Service
	    Service service = Service.createServiceFromViewModel(accommodationVm);
    
    		// Create Service
		// Service service = Service.createServiceFromViewModel(accommodationVm);
		// service.setType(ServiceType.ACCOMMODATION);

		// Service service = new Service();
    
	    service.setType(ServiceType.ACCOMMODATION);

	    // Create Price
	    Price price = Price.createPriceFromViewModel(accommodationVm);
	    service.setPrice(price);

	    service.setAvailableFrom(accommodationVm.getAvailableFrom());
	    service.setAvailableTill(accommodationVm.getAvailableTill());
	    service.setAvailability(accommodationVm.isAvailability());

	    // Persist Service and Price 
	    em.persist(price);
	    em.persist(service);
	    
		Picture picture = new Picture();
		picture.setLink(accommodationVm.getLink());
		accommodationVm.setPicture(picture);
		em.persist(picture);

	    // Create Accommodation
	    Accommodation accommodation = new Accommodation();
	    accommodation.setId(accommodationVm.getId());
	    accommodation.setName(accommodationVm.getName());
	    accommodation.setDescription(accommodationVm.getDescription());
	    accommodation.setService(service);
	    accommodation.setAccommodationType(accommodationVm.getAccommodationType());

	    // Create Address
	    Address addressAccommodation = new Address();
	    addressAccommodation.setNum(accommodationVm.getAddress().getNum());
	    addressAccommodation.setStreet(accommodationVm.getAddress().getStreet());
	    addressAccommodation.setCity(accommodationVm.getAddress().getCity());
	    addressAccommodation.setState(accommodationVm.getAddress().getState());
	    addressAccommodation.setCountry(accommodationVm.getAddress().getCountry());
	    addressAccommodation.setZipCode(accommodationVm.getAddress().getZipCode());

	    // Persist Address
	    em.persist(addressAccommodation);

	    // Link the Address to the Accommodation
	    accommodation.setAddress(addressAccommodation);
	    
	    if ("organizer".equals(userType)) {
	        Organizer organizer = em.find(Organizer.class, userId);
	        if (organizer == null) {
	            throw new IllegalArgumentException("Organizer with ID " + userId + " not found.");
	        }
	        accommodation.setOrganizer(organizer); // Supposons que cette méthode existe
	    } else if ("provider".equals(userType)) {
	        Provider provider = em.find(Provider.class, userId);
	        if (provider == null) {
	            throw new IllegalArgumentException("Provider with ID " + userId + " not found.");
	        }
	        accommodation.setProvider(provider); // Supposons que cette méthode existe
	    }

	    // Persist Accommodation
	    em.persist(accommodation);
	    em.flush();

	    return accommodation;

	}

	public SubServicesViewModel update(SubServicesViewModel accommodationvm) {

		Accommodation accommodation = em.find(Accommodation.class, accommodationvm.getId());
		if (accommodation == null) {
			throw new IllegalArgumentException("Accommodation with ID " + accommodationvm.getId() + " not found.");
		}

		accommodation.updateAccommodationViewModel(accommodationvm);
	
		accommodation = em.merge(accommodation);
		em.flush();

		// Convert the updated entity back to the view model and return it
		return accommodation.initAccommodationViewModel();
	

	}

	public void delete(SubServicesViewModel accommodationvm) {

		Accommodation accommodation = em.find(Accommodation.class, accommodationvm.getId());
		if (accommodation == null) {
			throw new IllegalArgumentException("Accommodation with ID " + accommodationvm.getId() + " not found.");
		}
		accommodation.updateAccommodationViewModel(accommodationvm);
		em.remove(accommodation);
		em.flush();

	}

	public SubServicesViewModel initSubService(Long id) {
		Accommodation accommodation = em.find(Accommodation.class, id);
		if (accommodation == null) {
			return null;
		}
		return accommodation.initAccommodationViewModel();
	}

	public Accommodation read(Long id) {
		return em.find(Accommodation.class, id);
	}

	public List<Accommodation> getAll() {
		TypedQuery<Accommodation> query = em.createQuery("SELECT a FROM Accommodation a", Accommodation.class);

		return query.getResultList();
	}

	public Accommodation findByName(String name) {
		TypedQuery<Accommodation> query = em.createQuery("SELECT a FROM Accommodation a WHERE a.name = :name",
				Accommodation.class);
		query.setParameter("name", name);

		List<Accommodation> accommodations = query.getResultList();
		return accommodations.isEmpty() ? null : accommodations.get(0);
	}

	public Accommodation findById(Long id) {
		return em.find(Accommodation.class, id);
	}
	
	public List<Accommodation> getAccommodationForOrganizer(Long organizerId) {
	    TypedQuery<Accommodation> query = em.createQuery("SELECT tp FROM Accommodation tp WHERE tp.organizer.id = :organizerId", Accommodation.class);
	    query.setParameter("organizerId", organizerId);
	    return query.getResultList();
	}
	
	public List<Accommodation> getAccommodationForProvider(Long providerId) {
	    TypedQuery<Accommodation> query = em.createQuery("SELECT tp FROM Accommodation tp WHERE tp.organizer.id = :providerId", Accommodation.class);
	    query.setParameter("providerId", providerId);
	    return query.getResultList();
	}

}
