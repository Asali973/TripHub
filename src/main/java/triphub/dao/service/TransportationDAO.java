package triphub.dao.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.Price;
import triphub.entity.product.service.Service;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.product.service.ServiceType;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.entity.subservices.TransportationType;
import triphub.entity.user.Organizer;
import triphub.entity.user.Provider;
import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;

/**
 * Data Access Object for the Transportation entity.
 */
@Stateless
public class TransportationDAO {
	@PersistenceContext
	private EntityManager em;

	/**
	 * Constructor with EntityManager parameter.
	 * 
	 * @param em EntityManager for the persistence context.
	 */
	public TransportationDAO(EntityManager em) {
		this.em = em;
	}

	/**
	 * Default constructor.
	 */
	public TransportationDAO() {
	}

	/**
	 * Creates a Transportation entity from a ViewModel.
	 * 
	 * @param transportationvm ViewModel containing transportation data.
	 * @param userId           ID of the user.
	 * @param userType         Type of user (Organizer or Provider).
	 * @return The created Transportation entity.
	 */
	public Transportation create(SubServicesViewModel transportationvm, Long userId, String userType) {

		// create service
		Service service = Service.createServiceFromViewModel(transportationvm);
		service.setType(ServiceType.TRANSPORTATION);

		Price price = Price.createPriceFromViewModel(transportationvm);
		price.setAmount(transportationvm.getPrice().getAmount());
		price.setCurrency(transportationvm.getCurrencyType().getLabel());
		service.setPrice(price);

		service.setAvailability(transportationvm.isAvailability());
		service.setAvailableFrom(transportationvm.getAvailableFrom());
		service.setAvailableTill(transportationvm.getAvailableTill());

		// create transportation
		Transportation transportation = new Transportation();
		transportation.setId(transportationvm.getId());
		transportation.setName(transportationvm.getName());
		transportation.setTransportationType(transportationvm.getTransportationType());
		transportation.setDescription(transportationvm.getDescription());
		transportation.setService(service);

		Picture picture = new Picture();
		picture.setLink(transportationvm.getLink());
		transportation.setPicture(picture);

		// create departure
		Address departure = new Address();
		departure.setNum(transportationvm.getDeparture().getNum());
		departure.setStreet(transportationvm.getDeparture().getStreet());
		departure.setCity(transportationvm.getDeparture().getCity());
		departure.setState(transportationvm.getDeparture().getState());
		departure.setCountry(transportationvm.getDeparture().getCountry());
		departure.setZipCode(transportationvm.getDeparture().getZipCode());
		em.persist(departure);
		transportation.setDeparture(departure);

		// create arrival
		Address arrival = new Address();
		arrival.setNum(transportationvm.getArrival().getNum());
		arrival.setStreet(transportationvm.getArrival().getStreet());
		arrival.setCity(transportationvm.getArrival().getCity());
		arrival.setState(transportationvm.getArrival().getState());
		arrival.setCountry(transportationvm.getArrival().getCountry());
		arrival.setZipCode(transportationvm.getArrival().getZipCode());
		em.persist(arrival);
		transportation.setArrival(arrival);

		if ("organizer".equals(userType)) {
			Organizer organizer = em.find(Organizer.class, userId);
			if (organizer == null) {
				throw new IllegalArgumentException("Organizer with ID " + userId + " not found.");
			}
			transportation.setOrganizer(organizer);
		} else if ("provider".equals(userType)) {
			Provider provider = em.find(Provider.class, userId);
			if (provider == null) {
				throw new IllegalArgumentException("Provider with ID " + userId + " not found.");
			}
			transportation.setProvider(provider);
		}

		em.persist(picture);
		em.persist(service);
		em.persist(price);
		em.persist(transportation);

		return transportation;
	}

	/**
	 * Finds Transportation entities by type.
	 * 
	 * @param transportationType The type of transportation.
	 * @return List of Transportation entities.
	 */
	public List<Transportation> findByType(TransportationType transportationType) {
		TypedQuery<Transportation> query = em.createQuery(
				"SELECT t FROM Transportation t WHERE t.transportationType = :transportationType",
				Transportation.class);
		query.setParameter("transportationType", transportationType);
		return query.getResultList();
	}

	/**
	 * Updates a Transportation entity from a ViewModel.
	 * 
	 * @param transportationvm ViewModel containing updated transportation data.
	 * @return Updated Transportation ViewModel.
	 */
	public SubServicesViewModel update(SubServicesViewModel transportationvm) {
		Transportation transportation = em.find(Transportation.class, transportationvm.getId());
		if (transportation == null) {
			throw new IllegalArgumentException("Restaurant with ID " + transportationvm.getId() + " not found.");
		}

		transportation.updateTransportationViewModel(transportationvm);
		transportation = em.merge(transportation);
		em.flush();

		return transportation.initTransportationViewModel();
	}

	/**
	 * Deletes a Transportation entity.
	 * 
	 * @param transportationvm ViewModel containing transportation data to delete.
	 */
	public void delete(SubServicesViewModel transportationvm) {
		Transportation transportation = em.find(Transportation.class, transportationvm.getId());
		if (transportation == null) {
			throw new IllegalArgumentException("Transportation with ID " + transportationvm.getId() + " not found.");
		}
		em.remove(transportation);
		em.flush();
	}

	/**
	 * Initializes a ViewModel from a Transportation entity.
	 * 
	 * @param id ID of the Transportation entity.
	 * @return Initialized ViewModel.
	 */
	public SubServicesViewModel initSubService(Long id) {
		Transportation transportation = em.find(Transportation.class, id);
		if (transportation == null) {
			return null;
		}
		return transportation.initTransportationViewModel();
	}

	/**
	 * Reads a Transportation entity by ID.
	 * 
	 * @param id ID of the Transportation entity.
	 * @return The Transportation entity.
	 */
	public Transportation read(Long id) {
		return em.find(Transportation.class, id);
	}

	/**
	 * Retrieves all Transportation entities.
	 * 
	 * @return List of all Transportation entities.
	 */
	public List<Transportation> getAll() {
		TypedQuery<Transportation> query = em.createQuery("SELECT t FROM Transportation t", Transportation.class);

		return query.getResultList();
	}

	/**
	 * Finds a Transportation entity by its name.
	 * 
	 * @param name Name of the Transportation entity.
	 * @return The found Transportation entity.
	 */
	public Transportation findByName(String name) {
		TypedQuery<Transportation> query = em.createQuery("SELECT t FROM Transportation t WHERE t.name = :name",
				Transportation.class);
		query.setParameter("name", name);

		List<Transportation> transportations = query.getResultList();
		return transportations.isEmpty() ? null : transportations.get(0);
	}

	/**
	 * Finds a Transportation entity by its ID.
	 * 
	 * @param id ID of the Transportation entity.
	 * @return The found Transportation entity.
	 */
	public Transportation findById(Long id) {
		return em.find(Transportation.class, id);

	}

	/**
	 * Retrieves Transportation entities for a given organizer.
	 * 
	 * @param organizerId ID of the organizer.
	 * @return List of Transportation entities for the organizer.
	 */
	public List<Transportation> getTransportationForOrganizer(Long organizerId) {
		TypedQuery<Transportation> query = em.createQuery(
				"SELECT tp FROM Transportation tp WHERE tp.organizer.id = :organizerId", Transportation.class);
		query.setParameter("organizerId", organizerId);
		return query.getResultList();
	}

	/**
	 * Retrieves Transportation entities for a given provider.
	 * 
	 * @param providerId ID of the provider.
	 * @return List of Transportation entities for the provider.
	 */
	public List<Transportation> getTransportationForProvider(Long providerId) {
		TypedQuery<Transportation> query = em.createQuery(
				"SELECT tp FROM Transportation tp WHERE tp.organizer.id = :providerId", Transportation.class);
		query.setParameter("providerId", providerId);
		return query.getResultList();
	}

	/**
	 * Associates a Transportation entity with an Organizer.
	 * 
	 * @param organizerId      ID of the organizer.
	 * @param transportationId ID of the transportation entity.
	 * @return true if successful, false otherwise.
	 */
	public boolean addTransportationToOrganizer(Long organizerId, Long transportationId) {
		try {

			Transportation transportation = em.find(Transportation.class, transportationId);
			if (transportation == null) {
				throw new IllegalArgumentException("Transportation with ID " + transportationId + " not found.");
			}

			Organizer organizer = em.find(Organizer.class, organizerId);
			if (organizer == null) {
				throw new IllegalArgumentException("Organizer with ID " + organizerId + " not found.");
			}

			transportation.setOrganizer(organizer);

			em.merge(transportation);
			em.flush();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
