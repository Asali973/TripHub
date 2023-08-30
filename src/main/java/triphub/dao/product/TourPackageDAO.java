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
import javax.servlet.http.Part;
import javax.persistence.criteria.Predicate;
import triphub.entity.product.Destination;

import triphub.entity.util.*;

import triphub.entity.product.Price;
import triphub.entity.product.Theme;
import triphub.entity.product.TourPackage;
import triphub.entity.user.Organizer;
import triphub.viewModel.TourPackageFormViewModel;


/**
 * DAO for managing operations related to TourPackage entities.
 */
@Stateless
public class TourPackageDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "triphub")
	private EntityManager em;
	
	
	public TourPackageDAO() {
	}

	/**
     * Creates a new TourPackage based on the provided view model and organizer ID.
     *
     * @param tourPackageVm The view model with package details.
     * @param organizerId The ID of the associated organizer.
     * @return The newly created TourPackage.
     */
	public TourPackage create(TourPackageFormViewModel tourPackageVm, Long organizerId) {
		
	    Organizer organizer = em.find(Organizer.class, organizerId);
	    if (organizer == null) {
	        throw new IllegalArgumentException("Organizer with ID " + organizerId + " not found.");
	    }
		TourPackage newPackage = new TourPackage();
		newPackage.setName(tourPackageVm.getName());
		newPackage.setDescription(tourPackageVm.getDescription());
		newPackage.setOrganizer(organizer);
		
		Picture picture = new Picture();
		picture.setLink(tourPackageVm.getLink());
		newPackage.setPicture(picture);

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
		 
		em.persist(picture);
		em.persist(newDestination);
		em.persist(newTheme);
		em.persist(newPrice);
		em.persist(newPackage);

		em.flush();
		return newPackage;
	}

	
    /**
     * Updates an existing TourPackage based on the provided view model.
     *
     * @param tourPackageVm The view model with updated package details.
     * @return The updated view model.
     */
	public TourPackageFormViewModel updateTourPackage(TourPackageFormViewModel tourPackageVm) {

		TourPackage tourPackage = em.find(TourPackage.class, tourPackageVm.getId());
		if (tourPackage == null) {
			throw new IllegalArgumentException("Tour package with ID " + tourPackageVm.getId() + " not found.");
		}

		tourPackage.updateTourPackageFormViewModel(tourPackageVm);
		tourPackage = em.merge(tourPackage);
		em.flush();

		return tourPackage.initTourPackageFormViewModel();
	}
	
	
    /**
     * Deletes a TourPackage based on the provided view model.
     *
     * @param tourPackageVm The view model of the package to be deleted.
     */
	public void delete(TourPackageFormViewModel tourPackageVm) {
		TourPackage tourPackage = em.find(TourPackage.class, tourPackageVm.getId());
		if (tourPackage == null) {
			throw new IllegalArgumentException("Tour package with ID " + tourPackageVm.getId() + " not found.");
		}

		tourPackage.updateTourPackageFormViewModel(tourPackageVm);
		em.remove(tourPackage);
		em.flush();
	}

    /**
     * Initializes a TourPackageFormViewModel for the given package ID.
     *
     * @param id The ID of the TourPackage.
     * @return The initialized view model.
     */
	public TourPackageFormViewModel initTourPackage(Long id) {
		TourPackage tourPackage = em.find(TourPackage.class, id);
		if (tourPackage == null) {
			return null;
		}
		return tourPackage.initTourPackageFormViewModel();
	}

    /**
     * Reads a TourPackage based on the given ID.
     *
     * @param id The ID of the package.
     * @return The found TourPackage or null if not found.
     */
	public TourPackage read(Long id) {
		return em.find(TourPackage.class, id);
	}

    /**
     * Deletes a TourPackage based on its ID.
     *
     * @param id The ID of the package.
     */
	public void deleteTourPackageById(Long id) {
		TourPackage entity = em.find(TourPackage.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}
	
    /**
     * Searches for a TourPackage based on its name.
     *
     * @param packageName The name of the package.
     * @return The found package or null if not found.
     */
	public TourPackage findTourPackageById(Long tourPackageId) {
		return em.find(TourPackage.class, tourPackageId);
	}

    /**
     * Searches for a TourPackage based on its name.
     *
     * @param packageName The name of the package.
     * @return The found package or null if not found.
     */
	public TourPackage findPackageByName(String packageName) {
		TypedQuery<TourPackage> query = em.createQuery("SELECT tp FROM TourPackage tp WHERE tp.name = :name",
				TourPackage.class);
		query.setParameter("name", packageName);

		List<TourPackage> packages = query.getResultList();
		return packages.isEmpty() ? null : packages.get(0);
	}

    /**
     * Retrieves all stored TourPackages.
     *
     * @return A list of all packages.
     */
	public List<TourPackage> getAllTourPackages() {
		TypedQuery<TourPackage> query = em.createQuery("SELECT tp FROM TourPackage tp", TourPackage.class);

		return query.getResultList();
	}

	   /**
     * Conducts an advanced search based on the provided criteria.
     *
     * @param city The city name.
     * @param state The state name.
     * @param country The country name.
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @param name The package name.
     * @param themeName The theme name.
     * @return A list of matching packages.
     */
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
	
    /**
     * Retrieves all packages for a given organizer.
     *
     * @param organizerId The ID of the organizer.
     * @return A list of the organizer's packages.
     */
	public List<TourPackage> getTourPackagesForOrganizer(Long organizerId) {
	    TypedQuery<TourPackage> query = em.createQuery("SELECT tp FROM TourPackage tp WHERE tp.organizer.id = :organizerId", TourPackage.class);
	    query.setParameter("organizerId", organizerId);
	    return query.getResultList();
	}

}
