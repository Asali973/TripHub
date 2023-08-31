package triphub.services;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.Transactional;
import triphub.dao.services.AccommodationDAO;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.subservices.Accommodation;
import triphub.helpers.FacesMessageUtil;
import triphub.viewModel.SubServicesViewModel;

/**
 * This class represents a service for managing accommodations within the
 * TripHub application. It provides methods to create, read, update, and delete
 * accommodations, as well as retrieve information about accommodations.
 *
 */

@ApplicationScoped
public class AccommodationService implements ServiceInterface, Serializable {

	/**
	 * The serial version UID for serialization.
	 */

	private static final long serialVersionUID = 1L;
	@Inject
	@Default
	private AccommodationDAO accommodationDAO;

	public AccommodationService() {

	}

	public AccommodationService(AccommodationDAO accommodationDao) {
		this.accommodationDAO = accommodationDao;

	}
	 /**
     * Retrieves a list of all accommodations.
     *
     * @return A list containing all accommodations.
     */

	public List<Accommodation> getAllAccommodation() {
		return accommodationDAO.getAll();
	}
	
	 /**
     * Creates an accommodation based on the provided view model and user information.
     *
     * @param accommodationvm The view model containing accommodation information.
     * @param userId          The ID of the user creating the accommodation.
     * @param userType        The type of user creating the accommodation.
     * @return The created Accommodation object.
     */
	
	
	@Transactional
	@Override
	public Accommodation create(SubServicesViewModel accommodationvm, Long userId, String userType) {

		try {
			return accommodationDAO.create(accommodationvm, userId, userType);
		} catch (Exception e) {

			FacesMessageUtil.addErrorMessage("Failed to create restaurant. An unexpected error occurred.");
		}
		return null;
	}

	 /**
     * Retrieves an accommodation by its ID.
     *
     * @param id The ID of the accommodation to retrieve.
     * @return The retrieved Accommodation object, or null if not found.
     */
	@Override
	public Accommodation read(Long id) {
		return accommodationDAO.read(id);
	}

	/**
     * Updates an accommodation based on the provided view model.
     *
     * @param accommodationvm The view model containing updated accommodation information.
     */
	public void update(SubServicesViewModel accommodationvm) {
		try {
			accommodationDAO.update(accommodationvm);
		} catch (IllegalArgumentException e) {

			FacesMessageUtil.addErrorMessage("Failed to update accommodation: " + e.getMessage());
		} catch (Exception e) {

			FacesMessageUtil.addErrorMessage("Failed to update accommodation. An unexpected error occurred.");
		}

	}
	
	 /**
     * Deletes an accommodation based on the provided view model.
     *
     * @param accommodationvm The view model containing accommodation information to be deleted.
     */
	@Override
	public void delete(SubServicesViewModel accommodationvm) {
		accommodationDAO.delete(accommodationvm);

	}
	 /**
     * Initializes a sub-service (accommodation) based on the provided ID.
     *
     * @param id The ID of the accommodation to be initialized.
     * @return The initialized SubServicesViewModel representing the accommodation.
     */

	@Override
	public SubServicesViewModel initSubService(Long id) {
		Accommodation accommodation = accommodationDAO.findById(id);

		if (accommodation == null) {

			return null;
		}
		System.out.println("accommodationService apres find : " + accommodation);
		return accommodation.initAccommodationViewModel();
	}
	 /**
     * Retrieves a list of all accommodations.
     *
     * @return A list containing all accommodations.
     */

	@Override
	public List<Accommodation> getAll() {
		return accommodationDAO.getAll();
	}

	 /**
     * Finds an accommodation by its name.
     *
     * @param name The name of the accommodation to find.
     * @return The found Accommodation object, or null if not found.
     */
	@Override
	public Accommodation findByName(String name) {
		return accommodationDAO.findByName(name);
	}
	
	 /**
     * Retrieves an accommodation by its ID.
     *
     * @param id The ID of the accommodation to retrieve.
     * @return The retrieved Accommodation object, or null if not found.
     */
	@Override
	public Accommodation findById(Long id) {
		return accommodationDAO.findById(id);
	}

	public Accommodation getAccommodationById(Long id) {
		return accommodationDAO.read(id);
	}

	public List<Accommodation> getAccommodationForOrganizer(Long organizerId) {
		return accommodationDAO.getAccommodationForOrganizer(organizerId);
	}

	public List<Accommodation> getAccommodationForProvider(Long providerId) {
		return accommodationDAO.getAccommodationForProvider(providerId);
	}

}
