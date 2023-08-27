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

@Stateless
public class TransportationDAO{
	@PersistenceContext
	private EntityManager em;

	public TransportationDAO(EntityManager em) {
		this.em = em;
	}

	public TransportationDAO() {
	}
	

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


	public List<Transportation> findByType(TransportationType transportationType) {
		TypedQuery<Transportation> query = em.createQuery(
				"SELECT t FROM Transportation t WHERE t.transportationType = :transportationType", Transportation.class);
		query.setParameter("transportationType", transportationType);
		return query.getResultList();
	}



	
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

	
	public void delete(SubServicesViewModel transportationvm) {
	    Transportation transportation = em.find(Transportation.class, transportationvm.getId());
	    if (transportation == null) {
	        throw new IllegalArgumentException("Transportation with ID " + transportationvm.getId() + " not found.");
	    }
	    em.remove(transportation);
	    em.flush();
	}


	
	public SubServicesViewModel initSubService(Long id) {
		Transportation transportation = em.find(Transportation.class, id);
		if (transportation == null) {
		return null;
		}
		return transportation.initTransportationViewModel();
	}

	
	public Transportation read(Long id) {
		return em.find(Transportation.class, id);
	}
	
	
	public List<Transportation> getAll() {
		TypedQuery<Transportation> query = em.createQuery("SELECT t FROM Transportation t", Transportation.class);

		return query.getResultList();
	}

	
	public Transportation findByName(String name) {
		TypedQuery<Transportation> query = em.createQuery("SELECT t FROM Transportation t WHERE t.name = :name", Transportation.class);
		query.setParameter("name", name);

		List<Transportation> transportations = query.getResultList();
		return transportations.isEmpty() ? null : transportations.get(0);
	}

	public Transportation findById(Long id) {
		return em.find(Transportation.class,id);

	}
	
	public List<Transportation> getTransportationForOrganizer(Long organizerId) {
		TypedQuery<Transportation> query = em
				.createQuery("SELECT tp FROM Transportation tp WHERE tp.organizer.id = :organizerId", Transportation.class);
		query.setParameter("organizerId", organizerId);
		return query.getResultList();
	}

	public List<Transportation> getTransportationForProvider(Long providerId) {
		TypedQuery<Transportation> query = em
				.createQuery("SELECT tp FROM Transportation tp WHERE tp.organizer.id = :providerId", Transportation.class);
		query.setParameter("providerId", providerId);
		return query.getResultList();
	}
	
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
