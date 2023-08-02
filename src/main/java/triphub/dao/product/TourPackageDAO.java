package triphub.dao.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import triphub.entity.product.Destination;
import triphub.entity.product.Image;
import triphub.entity.product.Price;
import triphub.entity.product.Theme;
import triphub.entity.product.TourPackage;
import triphub.viewModel.TourPackageFormViewModel;

@Stateless
public class TourPackageDAO {
	@PersistenceContext(unitName = "triphub") 

	private EntityManager em;

	public TourPackageDAO() {
	}
	
	public TourPackage create(TourPackageFormViewModel tourPackageVm) {

		TourPackage newPackage = new TourPackage();
		newPackage.setName(tourPackageVm.getName());

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

		em.persist(newDestination);
		em.persist(newTheme);
		em.persist(newPrice);
		em.persist(newPackage);
		em.flush();
		return newPackage;
	}

	public TourPackage read(Long id) {
		return em.find(TourPackage.class, id);
	}

	// basic, to be removed later
	public TourPackage update(TourPackage tourPackage) {
		return em.merge(tourPackage);
	}

	public void delete(TourPackage tourPackage2) {
		TourPackage tourPackage = em.find(TourPackage.class, tourPackage2);
		if (tourPackage != null) {
			em.remove(tourPackage);
		}
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

	public TourPackage createOrUpdate(TourPackageFormViewModel tourPackageVm) {
		if (tourPackageVm == null) {
			throw new IllegalArgumentException("TourPackageFormViewModel cannot be null.");
		}

		// Check if the package already exists based on its unique identifier (e.g.,
		// name)
		TourPackage existingPackage = findPackageByName(tourPackageVm.getName());

		if (existingPackage != null) {
			// Detach the existing package from the persistence context to avoid hibernate
			// tracking changes in the images collection
			em.detach(existingPackage);

			boolean isModified = false;

			// Compare attributes and update if necessary
			if (!Objects.equals(existingPackage.getPrice().getAmount(), tourPackageVm.getAmount())) {
				existingPackage.getPrice().setAmount(tourPackageVm.getAmount());
				isModified = true;
			}

			if (!Objects.equals(existingPackage.getPrice().getCurrency(), tourPackageVm.getCurrency())) {
				existingPackage.getPrice().setCurrency(tourPackageVm.getCurrency());
				isModified = true;
			}

			if (!Objects.equals(existingPackage.getTheme().getThemeName(), tourPackageVm.getThemeName())) {
				Theme theme = new Theme();
				theme.setThemeName(tourPackageVm.getThemeName());
				existingPackage.setTheme(theme);
				isModified = true;
			}

			// If there are images in the new tourPackage, update the existing package
			// without triggering the orphan removal behavior
			// when the front is developed, we can rewrite it as above
//		        if (tourPackageVm.getImagelinks() != null && !tourPackageVm.getImagelinks().isEmpty()) {
//		            existingPackage.setImages(tourPackageVm.getImagelinks());
//		            isModified = true;
//		        }

			// case 1: If changes were made, update the package
			if (isModified) {
				return em.merge(existingPackage);
			}
		} else {
			// case 2: if package doesn't exist, persist it as a new entity
			// Convert TourPackageFormViewModel to TourPackage entity
			return create(tourPackageVm);

//		        List<Image> newImages = new ArrayList<>();
//		        if (tourPackageVm.getImagelinks() != null) {
//		            newImages.addAll(tourPackageVm.getImagelinks());
//		        }
//		        newPackage.setImages(newImages);	     
		}

		// case 3: If no changes were made, return the existing package entity
		return existingPackage;
	}
	public List<TourPackage> advancedSearch(String city, String state, String country, BigDecimal minPrice, BigDecimal maxPrice, String name, String themeName) {
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