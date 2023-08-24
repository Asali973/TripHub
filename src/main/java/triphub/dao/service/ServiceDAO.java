package triphub.dao.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import triphub.entity.product.service.Service;
import triphub.entity.product.service.ServiceType;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.AccommodationType;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.entity.subservices.TransportationType;

@Stateless
public class ServiceDAO {

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

	public List<Accommodation> advancedSearchAccommodations(String city, String country, BigDecimal minPrice,
			BigDecimal maxPrice, String name, AccommodationType accommodationType) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Accommodation> query = cb.createQuery(Accommodation.class);
		Root<Accommodation> root = query.from(Accommodation.class);

		List<Predicate> predicates = new ArrayList<>();

		// Add predicates for each search criterion
		if (city != null && !city.isEmpty()) {
			predicates.add(cb.like(root.get("address").get("city"), "%" + city + "%"));
		}

		if (country != null && !country.isEmpty()) {
			predicates.add(cb.like(root.get("address").get("country"), "%" + country + "%"));
		}

		if (minPrice != null && maxPrice != null) {
			predicates.add(cb.between(root.get("service").get("price").get("amount"), minPrice, maxPrice));
		} else if (minPrice != null) {
			predicates.add(cb.greaterThanOrEqualTo(root.get("service").get("price").get("amount"), minPrice));
		} else if (maxPrice != null) {
			predicates.add(cb.lessThanOrEqualTo(root.get("service").get("price").get("amount"), maxPrice));
		}

		if (name != null && !name.isEmpty()) {
			predicates.add(cb.like(root.get("name"), "%" + name + "%"));
		}

		if (accommodationType != null) {
			predicates.add(cb.equal(root.get("accommodationType"), accommodationType));
		}

		query.select(root).where(predicates.toArray(new Predicate[0]));

		TypedQuery<Accommodation> typedQuery = em.createQuery(query);
		return typedQuery.getResultList();
	}

	public List<Transportation> advancedSearchTransportations(String departureCity, String departureCountry,
			String arrivalCity, String arrivalCountry, BigDecimal minPrice, BigDecimal maxPrice, String name,
			TransportationType transportationType) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Transportation> query = cb.createQuery(Transportation.class);
		Root<Transportation> root = query.from(Transportation.class);

		List<Predicate> predicates = new ArrayList<>();

		// Add predicates for each search criterion
		if (departureCity != null && !departureCity.isEmpty()) {
			predicates.add(cb.like(root.get("departure").get("city"), "%" + departureCity + "%"));
		}

		if (departureCountry != null && !departureCountry.isEmpty()) {
			predicates.add(cb.like(root.get("departure").get("country"), "%" + departureCountry + "%"));
		}

		if (arrivalCity != null && !arrivalCity.isEmpty()) {
			predicates.add(cb.like(root.get("arrival").get("city"), "%" + arrivalCity + "%"));
		}

		if (arrivalCountry != null && !arrivalCountry.isEmpty()) {
			predicates.add(cb.like(root.get("arrival").get("country"), "%" + arrivalCountry + "%"));
		}

		if (minPrice != null && maxPrice != null) {
			predicates.add(cb.between(root.get("service").get("price").get("amount"), minPrice, maxPrice));
		} else if (minPrice != null) {
			predicates.add(cb.greaterThanOrEqualTo(root.get("service").get("price").get("amount"), minPrice));
		} else if (maxPrice != null) {
			predicates.add(cb.lessThanOrEqualTo(root.get("service").get("price").get("amount"), maxPrice));
		}

		if (name != null && !name.isEmpty()) {
			predicates.add(cb.like(root.get("name"), "%" + name + "%"));
		}

		if (transportationType != null) {
			predicates.add(cb.equal(root.get("transportationType"), transportationType));
		}

		query.select(root).where(predicates.toArray(new Predicate[0]));

		TypedQuery<Transportation> typedQuery = em.createQuery(query);
		return typedQuery.getResultList();
	}

	public List<Restaurant> advancedSearchRestaurants(String name, String city, String country, BigDecimal minPrice, BigDecimal maxPrice) {
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Restaurant> query = cb.createQuery(Restaurant.class);
	    Root<Restaurant> root = query.from(Restaurant.class);

	    List<Predicate> predicates = new ArrayList<>();

	    if (name != null && !name.isEmpty()) {
	        predicates.add(cb.like(root.get("name"), "%" + name + "%"));
	    }

	    if (city != null && !city.isEmpty()) {
	        predicates.add(cb.like(root.get("address").get("city"), "%" + city + "%"));
	    }

	    if (country != null && !country.isEmpty()) {
	        predicates.add(cb.like(root.get("address").get("country"), "%" + country + "%"));
	    }

	    if (minPrice != null && maxPrice != null) {
	        predicates.add(cb.between(root.get("service").get("price").get("amount"), minPrice, maxPrice));
	    } else if (minPrice != null) {
	        predicates.add(cb.greaterThanOrEqualTo(root.get("service").get("price").get("amount"), minPrice));
	    } else if (maxPrice != null) {
	        predicates.add(cb.lessThanOrEqualTo(root.get("service").get("price").get("amount"), maxPrice));
	    }

	    query.select(root).where(predicates.toArray(new Predicate[0]));

	    TypedQuery<Restaurant> typedQuery = em.createQuery(query);
	    return typedQuery.getResultList();
	}


}
