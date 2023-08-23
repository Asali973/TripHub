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
import triphub.entity.subservices.Accommodation;
import triphub.entity.util.Address;
import triphub.entity.util.Calendar;
import triphub.viewModel.SubServicesViewModel;

@ApplicationScoped
public class AccommodationDAO implements ServiceInterface {

	@PersistenceContext
	private EntityManager em;

	public AccommodationDAO(EntityManager em) {
		this.em = em;

	}

	public AccommodationDAO() {
	}

	public Accommodation create(SubServicesViewModel accommodationVm) {

	    // Create Service
	    Service service = Service.createServiceFromViewModel(accommodationVm);
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
	


	@Override
	public void delete(SubServicesViewModel accommodationvm) {

		Accommodation accommodation = em.find(Accommodation.class, accommodationvm.getId());
		if (accommodation == null) {
			throw new IllegalArgumentException("Accommodation with ID " + accommodationvm.getId() + " not found.");
		}
		accommodation.updateAccommodationViewModel(accommodationvm);
		em.remove(accommodationvm);
		em.flush();

	}

	@Override
	public SubServicesViewModel initSubService(Long id) {
		Accommodation accommodation = em.find(Accommodation.class, id);
		if (accommodation == null) {
			return null;
		}
		return accommodation.initAccommodationViewModel();
	}

	@Override
	public Accommodation read(Long id) {
		return em.find(Accommodation.class, id);
	}

	@Override
	public List<Accommodation> getAll() {
		TypedQuery<Accommodation> query = em.createQuery("SELECT a FROM Accommodation a", Accommodation.class);

		return query.getResultList();
	}

	@Override
	public Accommodation findByName(String name) {
		TypedQuery<Accommodation> query = em.createQuery("SELECT a FROM Accommodation a WHERE a.name = :name",
				Accommodation.class);
		query.setParameter("name", name);

		List<Accommodation> accommodations = query.getResultList();
		return accommodations.isEmpty() ? null : accommodations.get(0);
	}

	@Override
	public Accommodation findById(Long id) {
		return em.find(Accommodation.class, id);
	}


}
