package triphub.dao.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.service.Service;
import triphub.entity.product.service.ServiceInterface;

import triphub.entity.user.User;
import triphub.viewModel.SubServicesViewModel;

@Stateless
public class ServiceDAO  {
	
	
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private AccommodationDAO accommodationDAO;
	
	@Inject
	private RestaurantDAO restaurantDAO;
	
	@Inject
	private TransportationDAO transportationDAO;
	
	
	
	public ServiceDAO() {
		
	}
	
	
	public Service createService(Service service) {
		em.persist(service);
		return service;
	}


	public Service read(Long id) {
		return em.find(Service.class, id);
	}
	
	public Service findById(Long id) {
		
		try {
			return em.find(Service.class, id);
		} catch (NoResultException e) {
			return null;
		}
	}


	
	
}
