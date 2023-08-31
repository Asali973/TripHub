package triphub.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

import triphub.dao.product.TourPackageDAO;
import triphub.dao.user.OrganizerDAO;
import triphub.entity.product.TourPackage;
import triphub.helpers.FacesMessageUtil;
import triphub.viewModel.TourPackageFormViewModel;

/**
 * Provides business logic services related to tour packages. This service class
 * provides methods for retrieving, creating, updating, and deleting tour
 * packages. Additionally, it offers advanced search capabilities and methods to
 * fetch tour packages for a specific organizer.
 */
@Stateless
public class TourPackageService implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private TourPackageDAO tourPackageDAO;

	@Inject
	private OrganizerDAO organizerDAO;

	public TourPackageService() {
	}

	/**
	 * Retrieves a list of all available tour packages.
	 * 
	 * @return A list of TourPackage entities.
	 */
	public List<TourPackage> getAllTourPackages() {
		return tourPackageDAO.getAllTourPackages();
	}

	/**
	 * Retrieves a tour package based on its unique identifier.
	 * 
	 * @param id The unique identifier of the tour package.
	 * @return The TourPackage entity if found, null otherwise.
	 */
	public TourPackage getTourPackageById(Long id) {
		return tourPackageDAO.read(id);
	}

	/**
	 * Retrieves a tour package based on its name.
	 * 
	 * @param packageName The name of the tour package.
	 * @return The TourPackage entity if found, null otherwise.
	 */

	public TourPackage getTourPackageByName(String packageName) {
		return tourPackageDAO.findPackageByName(packageName);
	}

	/**
	 * Creates a new tour package using the provided view model and organizer ID.
	 * 
	 * @param tourPackageVm The view model containing details of the tour package.
	 * @param organizerId   The unique identifier of the organizer.
	 * @return The created TourPackage entity.
	 */
	public TourPackage createTourPackage(TourPackageFormViewModel tourPackageVm, Long organizerId) {
		return tourPackageDAO.create(tourPackageVm, organizerId);
	}

	/**
	 * Initializes a tour package view model based on the provided tour package
	 * identifier.
	 * 
	 * @param id The unique identifier of the tour package.
	 * @return The initialized TourPackageFormViewModel, or null if the tour package
	 *         was not found.
	 */
	public TourPackageFormViewModel initTourPackage(Long id) {
		TourPackage tourPackage = tourPackageDAO.findTourPackageById(id);
		if (tourPackage == null) {
			return null;
		}
		return tourPackage.initTourPackageFormViewModel();
	}

	/**
	 * Updates an existing tour package based on the provided view model. In case of
	 * any errors during the update, appropriate messages are displayed to the user.
	 * 
	 * @param tourPackageVm The view model containing updated details of the tour
	 *                      package.
	 */
	public void updateTourPackage(TourPackageFormViewModel tourPackageVm) {
		try {
			tourPackageDAO.updateTourPackage(tourPackageVm);
		} catch (IllegalArgumentException e) {
			// Handle the case when the tour package with the provided ID was not found in
			// the DAO
			FacesMessageUtil.addErrorMessage("Failed to update tour package: " + e.getMessage());
		} catch (Exception e) {
			// Handle any other unexpected exceptions that might occur during the update
			// process
			FacesMessageUtil.addErrorMessage("Failed to update tour package. An unexpected error occurred.");
		}
	}

	/**
	 * Deletes a tour package based on the provided view model.
	 * 
	 * @param tourPackageVm The view model of the tour package to be deleted.
	 */

	public void deleteTourPackage(TourPackageFormViewModel tourPackageVm) {
		tourPackageDAO.delete(tourPackageVm);

	}

	/**
	 * Performs an advanced search for tour packages based on various criteria.
	 * 
	 * @param city      The city where the tour package is offered.
	 * @param state     The state where the tour package is offered.
	 * @param country   The country where the tour package is offered.
	 * @param minPrice  The minimum price of the tour package.
	 * @param maxPrice  The maximum price of the tour package.
	 * @param name      The name of the tour package.
	 * @param themeName The theme associated with the tour package.
	 * @return A list of TourPackage entities matching the search criteria.
	 */
	public List<TourPackage> advancedSearch(String city, String state, String country, BigDecimal minPrice,
			BigDecimal maxPrice, String name, String themeName) {
		return tourPackageDAO.advancedSearch(city, state, country, minPrice, maxPrice, name, themeName);
	}

	/**
	 * Retrieves a list of tour packages associated with a specific organizer.
	 * 
	 * @param organizerId The unique identifier of the organizer.
	 * @return A list of TourPackage entities associated with the given organizer.
	 */
	public List<TourPackage> getTourPackagesForOrganizer(Long organizerId) {
		return tourPackageDAO.getTourPackagesForOrganizer(organizerId);
	}

}
