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



import triphub.entity.service.Accommodation;

import triphub.entity.util.Address;
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
		
		// create service 
		
		Service service = Service.createServiceFromViewModel(accommodationVm);
		service.setType(ServiceType.Accommodation);
		
		
		Price price = Price.createPriceFromViewModel(accommodationVm);
		service.setPrice(price);
		
		// creer a Accommodation
		Accommodation accommodation = new Accommodation();
		accommodation.setId(accommodationVm.getId());
		accommodation.setName(accommodationVm.getName());
		accommodation.setDescription(accommodationVm.getDescription());
		accommodation.setService(service);
		accommodation.setAccommodationType(accommodationVm.getAccommodationType());
		
		
		

//		Picture picture = new Picture();
//		picture.setLink(formService.getLink());
//		accommodation.setPicture(picture);

		Address addressAccommodation = new Address();
		addressAccommodation.setNum(accommodationVm.getAddress().getNum());
		addressAccommodation.setStreet(accommodationVm.getAddress().getStreet());
		addressAccommodation.setCity(accommodationVm.getAddress().getCity());
		addressAccommodation.setState(accommodationVm.getAddress().getState());
		addressAccommodation.setCountry(accommodationVm.getAddress().getCountry());
		accommodation.setAddress(addressAccommodation);
		
		
		
		

//		em.persist(picture)
		em.persist(service);
		em.persist(price);
		em.persist(addressAccommodation);
		em.persist(accommodation);
		em.flush();
		return accommodation;
	}
	
	@Override
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
		TypedQuery<Accommodation> query = em.createQuery("SELECT a FROM Accommodation a WHERE a.name = :name", Accommodation.class);
		query.setParameter("name", name);

		List<Accommodation> accommodations = query.getResultList();
		return accommodations.isEmpty() ? null : accommodations.get(0);
	}
	
	
	@Override
	public Accommodation findById(Long id) {
		return em.find(Accommodation.class,id);
	}


	


	

	
	

	
}
