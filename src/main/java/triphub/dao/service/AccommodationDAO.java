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
import triphub.entity.subservices.Restaurant;
import triphub.entity.user.Organizer;
import triphub.entity.user.Provider;
import triphub.entity.util.Address;
import triphub.entity.util.Calendar;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;

@Stateless
public class AccommodationDAO {

	/**
	 * DAO class for managing Accommodations.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Constructor with entity manager.
	 * 
	 * @param em Entity manager.
	 */
	public AccommodationDAO(EntityManager em) {
		this.em = em;

	}

	/**
	 * Default constructor.
	 */
	public AccommodationDAO() {
	}

	/**
	 * Creates and persists an accommodation.
	 * 
	 * @param accommodationVm Data to create the accommodation.
	 * @param userId          ID of the user (organizer or provider).
	 * @param userType        Type of the user, either "organizer" or "provider".
	 * @return Created accommodation.
	 */
	public Accommodation create(SubServicesViewModel accommodationVm, Long userId, String userType) {

		// Create Service
		Service service = Service.createServiceFromViewModel(accommodationVm);
		service.setType(ServiceType.ACCOMMODATION);

		// Create Price
		Price price = Price.createPriceFromViewModel(accommodationVm);
		price.setAmount(accommodationVm.getPrice().getAmount());
		price.setCurrency(accommodationVm.getCurrencyType().getLabel());
		service.setPrice(price);

		service.setAvailableFrom(accommodationVm.getAvailableFrom());
		service.setAvailableTill(accommodationVm.getAvailableTill());
		service.setAvailability(accommodationVm.isAvailability());

		// Persist Service and Price
		em.persist(price);
		em.persist(service);

		// Create Accommodation
		Accommodation accommodation = new Accommodation();
		accommodation.setId(accommodationVm.getId());
		accommodation.setName(accommodationVm.getName());
		accommodation.setDescription(accommodationVm.getDescription());
		accommodation.setService(service);
		accommodation.setAccommodationType(accommodationVm.getAccommodationType());

		Picture picture = new Picture();
		picture.setLink(accommodationVm.getLink());
		accommodation.setPicture(picture);
		em.persist(picture);

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

	/**
	 * Updates the accommodation.
	 * 
	 * @param accommodationvm Data to update the accommodation.
	 * @return Updated accommodation view model.
	 */
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

	/**
	 * Deletes the given accommodation.
	 * 
	 * @param accommodationvm Data of the accommodation to be deleted.
	 */
	public void delete(SubServicesViewModel accommodationvm) {

		Accommodation accommodation = em.find(Accommodation.class, accommodationvm.getId());
		if (accommodation == null) {
			throw new IllegalArgumentException("Accommodation with ID " + accommodationvm.getId() + " not found.");
		}
		accommodation.updateAccommodationViewModel(accommodationvm);
		em.remove(accommodation);
		em.flush();

	}

	/**
	 * Initializes a sub-service.
	 * 
	 * @param id ID of the accommodation to initialize.
	 * @return Initialized sub-service view model.
	 */
	public SubServicesViewModel initSubService(Long id) {
		Accommodation accommodation = em.find(Accommodation.class, id);
		if (accommodation == null) {
			return null;
		}
		return accommodation.initAccommodationViewModel();
	}

	/**
	 * Reads accommodation by its ID.
	 * 
	 * @param id ID of the accommodation.
	 * @return Accommodation entity.
	 */
	public Accommodation read(Long id) {
		return em.find(Accommodation.class, id);
	}

	/**
	 * Gets all the accommodations.
	 * 
	 * @return List of all accommodations.
	 */
	public List<Accommodation> getAll() {
		TypedQuery<Accommodation> query = em.createQuery("SELECT a FROM Accommodation a", Accommodation.class);

		return query.getResultList();
	}

	/**
	 * Finds accommodation by its name.
	 * 
	 * @param name Name of the accommodation.
	 * @return Found accommodation or null.
	 */
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

	/**
	 * Retrieves all accommodations for a given organizer.
	 * 
	 * @param organizerId ID of the organizer.
	 * @return List of accommodations for the organizer.
	 */
	public List<Accommodation> getAccommodationForOrganizer(Long organizerId) {
		TypedQuery<Accommodation> query = em.createQuery(
				"SELECT tp FROM Accommodation tp WHERE tp.organizer.id = :organizerId", Accommodation.class);
		query.setParameter("organizerId", organizerId);
		return query.getResultList();
	}

	/**
	 * Retrieves all accommodations for a given provider.
	 * 
	 * @param providerId ID of the provider.
	 * @return List of accommodations for the provider.
	 */
	public List<Accommodation> getAccommodationForProvider(Long providerId) {
		TypedQuery<Accommodation> query = em.createQuery(
				"SELECT tp FROM Accommodation tp WHERE tp.organizer.id = :providerId", Accommodation.class);
		query.setParameter("providerId", providerId);
		return query.getResultList();
	}

	/**
	 * Adds an accommodation to an organizer.
	 * 
	 * @param organizerId     ID of the organizer.
	 * @param accommodationId ID of the accommodation.
	 * @return true if added successfully, false otherwise.
	 */
	public boolean addAccommodationToOrganizer(Long organizerId, Long accommodationId) {
		try {

			Accommodation accommodation = em.find(Accommodation.class, accommodationId);
			if (accommodation == null) {
				throw new IllegalArgumentException("Accommodation with ID " + accommodationId + " not found.");
			}

			Organizer organizer = em.find(Organizer.class, organizerId);
			if (organizer == null) {
				throw new IllegalArgumentException("Organizer with ID " + organizerId + " not found.");
			}

			accommodation.setOrganizer(organizer);

			em.merge(accommodation);
			em.flush();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
