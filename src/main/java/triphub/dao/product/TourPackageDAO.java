package triphub.dao.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import triphub.entity.product.Destination;

import triphub.entity.util.*;

import triphub.entity.product.Price;
import triphub.entity.product.Theme;
import triphub.entity.product.TourPackage;
import triphub.viewModel.TourPackageFormViewModel;

@Stateless
public class TourPackageDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "triphub")
	private EntityManager em;

	public TourPackageDAO() {
	}

	public TourPackage create(TourPackageFormViewModel tourPackageVm) {

		TourPackage newPackage = new TourPackage();
		newPackage.setName(tourPackageVm.getName());
		newPackage.setDescription(tourPackageVm.getDescription());

		Destination newDestination = new Destination();
		newDestination.setCityName(tourPackageVm.getCityName());
		newDestination.setState(tourPackageVm.getState());
		newDestination.setCountry(tourPackageVm.getCountry());
		newPackage.setDestination(newDestination);

		Theme newTheme = new Theme();
		newTheme.setThemeName(tourPackageVm.getThemeName());
		newPackage.setTheme(newTheme);

		Price newPrice = new Price();
		newPrice.setAmount(tourPackageVm.getAmount());
		newPrice.setCurrency(tourPackageVm.getCurrency());
		newPackage.setPrice(newPrice);
		
//		List<Picture> pictures = tourPackageVm.getPictureslinks();
//	    newPackage.setPictures(pictures);    
	  
		em.persist(newDestination);
		em.persist(newTheme);
		em.persist(newPrice);
		em.persist(newPackage);
	//	em.persist(pictures);
		em.flush();
		return newPackage;
	}

	public TourPackageFormViewModel updateTourPackage(TourPackageFormViewModel tourPackageVm) {

		TourPackage tourPackage = em.find(TourPackage.class, tourPackageVm.getId());
		if (tourPackage == null) {
			throw new IllegalArgumentException("Tour package with ID " + tourPackageVm.getId() + " not found.");
		}

		tourPackage.updateTourPackageFormViewModel(tourPackageVm);
		tourPackage = em.merge(tourPackage);
		em.flush();

		// Convert the updated entity back to the view model and return it
		return tourPackage.initTourPackageFormViewModel();
		// return tourPackageVm;
	}

	public void delete(TourPackageFormViewModel tourPackageVm) {
		TourPackage tourPackage = em.find(TourPackage.class, tourPackageVm.getId());
		if (tourPackage == null) {
			throw new IllegalArgumentException("Tour package with ID " + tourPackageVm.getId() + " not found.");
		}

		tourPackage.updateTourPackageFormViewModel(tourPackageVm);
		em.remove(tourPackage);
		em.flush();
	}

	public TourPackageFormViewModel initTourPackage(Long id) {
		TourPackage tourPackage = em.find(TourPackage.class, id);
		if (tourPackage == null) {
			return null;
		}
		return tourPackage.initTourPackageFormViewModel();
	}

	public TourPackage read(Long id) {
		return em.find(TourPackage.class, id);
	}

	public void deleteTourPackageById(Long id) {
		TourPackage entity = em.find(TourPackage.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public TourPackage findTourPackageById(Long tourPackageId) {
		return em.find(TourPackage.class, tourPackageId);
	}

	public TourPackage findPackageByName(String packageName) {
		TypedQuery<TourPackage> query = em.createQuery("SELECT tp FROM TourPackage tp WHERE tp.name = :name",
				TourPackage.class);
		query.setParameter("name", packageName);

		List<TourPackage> packages = query.getResultList();
		return packages.isEmpty() ? null : packages.get(0);
	}

	public List<TourPackage> getAllTourPackages() {
		TypedQuery<TourPackage> query = em.createQuery("SELECT tp FROM TourPackage tp", TourPackage.class);

		return query.getResultList();
	}

	public List<TourPackage> advancedSearch(String city, String state, String country, BigDecimal minPrice,
			BigDecimal maxPrice, String name, String themeName) {
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TourPackage> query = cb.createQuery(TourPackage.class);
		Root<TourPackage> root = query.from(TourPackage.class);

		List<Predicate> predicates = new ArrayList<>();

		// Add predicates for each search criterion
		if (city != null && !city.isEmpty()) {
			predicates.add(cb.like(root.get("destination").get("cityName"), "%" + city + "%"));
		}

		if (state != null && !state.isEmpty()) {
			predicates.add(cb.like(root.get("destination").get("state"), "%" + state + "%"));
		}

		if (country != null && !country.isEmpty()) {
			predicates.add(cb.like(root.get("destination").get("country"), "%" + country + "%"));
		}

		if (minPrice != null && maxPrice != null) {
			predicates.add(cb.between(root.get("price").get("amount"), minPrice, maxPrice));
		} else if (minPrice != null) {
			predicates.add(cb.greaterThanOrEqualTo(root.get("price").get("amount"), minPrice));
		} else if (maxPrice != null) {
			predicates.add(cb.lessThanOrEqualTo(root.get("price").get("amount"), maxPrice));
		}

		if (name != null && !name.isEmpty()) {
			predicates.add(cb.like(root.get("name"), "%" + name + "%"));
		}

		if (themeName != null && !themeName.isEmpty()) {
			predicates.add(cb.equal(root.get("theme").get("themeName"), themeName));
		}

		query.select(root).where(predicates.toArray(new Predicate[0]));

		TypedQuery<TourPackage> typedQuery = em.createQuery(query);
		return typedQuery.getResultList();
	}

}
