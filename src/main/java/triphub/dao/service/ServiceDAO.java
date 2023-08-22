package triphub.dao.service;


import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.service.Service;

@Stateless
public class ServiceDAO  {
	
	
	@PersistenceContext
	private EntityManager em;
	
	
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

	public List<Service> getAll() {
		TypedQuery<Service> query = em.createQuery("SELECT s FROM Service s", Service.class);

		return query.getResultList();
	}
	
	
}
