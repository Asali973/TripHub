package triphub.dao.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.Price;
import triphub.entity.product.service.Service;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.product.service.ServiceType;
import triphub.entity.subservices.Transportation;
import triphub.entity.subservices.TransportationType;
import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;

@ApplicationScoped
public class TransportationDAO implements ServiceInterface{
	@PersistenceContext
	private EntityManager em;

	public TransportationDAO(EntityManager em) {
		this.em = em;
	}

	public TransportationDAO() {
	}
	
	@Override
	public Transportation create(SubServicesViewModel transportationvm) {

		// create service
		Service service = Service.createServiceFromViewModel(transportationvm);
		service.setType(ServiceType.TRANSPORTATION);
		
		Price price = Price.createPriceFromViewModel(transportationvm);
		service.setPrice(price);
		
		service.setAvailability(transportationvm.isAvailability());
		service.setAvailableFrom(transportationvm.getAvailableFrom());
		service.setAvailableTill(transportationvm.getAvailableTill());
		
		// create transportation
		Transportation transportation = new Transportation();
		transportation.setId(transportationvm.getId());
		transportation.setName(transportationvm.getName());
		transportation.setTransportation(transportationvm.getTransportationType());
		transportation.setDescription(transportationvm.getDescription());
		transportation.setService(service);

		// create departure
		Address departure = new Address();
		departure.setNum(transportationvm.getDeparture().getNum());
		departure.setStreet(transportationvm.getDeparture().getStreet());
		departure.setCity(transportationvm.getDeparture().getCity());
		departure.setState(transportationvm.getDeparture().getState());
		departure.setCountry(transportationvm.getDeparture().getCountry());
		departure.setZipCode(transportationvm.getDeparture().getZipCode());

		
		// create arrival
		Address arrival = new Address();
		arrival.setNum(transportationvm.getArrival().getNum());
		arrival.setStreet(transportationvm.getArrival().getStreet());
		arrival.setCity(transportationvm.getArrival().getCity());
		arrival.setState(transportationvm.getArrival().getState());
		arrival.setCountry(transportationvm.getArrival().getCountry());
		arrival.setZipCode(transportationvm.getArrival().getZipCode());

		//create departure/arrival in transportation
		transportation.setDeparture(departure);
	    transportation.setArrival(arrival);
	    
//		Picture picture = new Picture();
//		picture.setLink(transportationvm.getLink());

	    em.persist(service);
	    em.persist(price);
		em.persist(departure);
		em.persist(arrival);
		em.persist(transportation);
//		em.persist(picture);
		
		return transportation;
	}


	public List<Transportation> findByType(TransportationType transportationType) {
		TypedQuery<Transportation> query = em.createQuery(
				"SELECT t FROM Transportation t WHERE t.transportation = :transportation", Transportation.class);
		query.setParameter("transportation", transportationType);
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

		// Convert the updated entity back to the view model and return it
		return transportation.initTransportationViewModel();
	}

	@Override
	public void delete(SubServicesViewModel transportationvm) {
	    Transportation transportation = em.find(Transportation.class, transportationvm.getId());
	    if (transportation == null) {
	        throw new IllegalArgumentException("Transportation with ID " + transportationvm.getId() + " not found.");
	    }
	    em.remove(transportation); // Remove the entity directly
	    em.flush();
	}


	@Override
	public SubServicesViewModel initSubService(Long id) {
		Transportation transportation = em.find(Transportation.class, id);
		if (transportation == null) {
		return null;
		}
		return transportation.initTransportationViewModel();
	}

	@Override
	public Transportation read(Long id) {
		return em.find(Transportation.class, id);
	}
	
	@Override
	public List<Transportation> getAll() {
		TypedQuery<Transportation> query = em.createQuery("SELECT t FROM Transportation t", Transportation.class);

		return query.getResultList();
	}

	@Override
	public Transportation findByName(String name) {
		TypedQuery<Transportation> query = em.createQuery("SELECT t FROM Transportation t WHERE t.name = :name", Transportation.class);
		query.setParameter("name", name);

		List<Transportation> transportations = query.getResultList();
		return transportations.isEmpty() ? null : transportations.get(0);
	}

	@Override
	public Transportation findById(Long id) {
		return em.find(Transportation.class,id);

	}

}
