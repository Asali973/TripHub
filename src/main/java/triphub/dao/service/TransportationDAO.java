package triphub.dao.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import triphub.entity.product.Theme;
import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.transportation.Transportation;
import triphub.entity.product.service.transportation.TransportationType;
import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;

@Stateless
public class TransportationDAO {
	@PersistenceContext
	private EntityManager em;

	public TransportationDAO(EntityManager em) {
		this.em = em;
	}

	public TransportationDAO() {
	}

	public Transportation create(SubServicesViewModel formService) {

		Transportation transportation = new Transportation();
		transportation.setNameTransportation(formService.getName());
		transportation.setTransportation(formService.getTransportationType());
		transportation.setDescription(formService.getDescription());

		Address departure = new Address();
		departure.setNum(formService.getNum());
		departure.setStreet(formService.getStreet());
		departure.setCity(formService.getCity());
		departure.setState(formService.getState());
		departure.setCountry(formService.getCountry());
		departure.setZipCode(formService.getZipCode());

		Address arrival = new Address();
		arrival.setNum(formService.getNum());
		arrival.setStreet(formService.getStreet());
		arrival.setCity(formService.getCity());
		arrival.setState(formService.getState());
		arrival.setCountry(formService.getCountry());
		arrival.setZipCode(formService.getZipCode());

		Picture picture = new Picture();
		picture.setLink(formService.getLink());

		em.persist(transportation);
		em.persist(departure);
		em.persist(arrival);
		em.persist(picture);
		return transportation;
	}

	public Transportation read(Long id) {
		return em.find(Transportation.class, id);
	}

	public void update(Transportation transportation) {
		if (transportation != null) {
			em.merge(transportation);
		}
	}

	public void delete(Long id) {
		Transportation transportation = em.find(Transportation.class, id);
		if (transportation != null) {
			em.remove(transportation);
		}
	}

	public List<Transportation> findByType(TransportationType transportationType) {
		TypedQuery<Transportation> query = em.createQuery(
				"SELECT t FROM Transportation t WHERE t.transportation = :transportation", Transportation.class);
		query.setParameter("transportation", transportationType);
		return query.getResultList();
	}

	public List<Transportation> getAllTransportation() {
		TypedQuery<Transportation> query = em.createQuery("SELECT t FROM Transportation t", Transportation.class);
		return query.getResultList();
	}

}
