package triphub.entity.product.service;

import java.util.List;

import triphub.viewModel.SubServicesViewModel;

/**
 * This interface defines the basic operations that a service related class must
 * provide. It declares methods for creating, reading, deleting, and retrieving
 * services, as well as methods to find services by name or ID and initialize a
 * SubServicesViewModel.
 *
 * @param <T> The type of entity that this service interface deals with.
 */
public interface ServiceInterface<T> {

	/**
	 * Creates a new instance of the entity using the provided SubServicesViewModel
	 * and other parameters.
	 *
	 * @param subservicesvm The SubServicesViewModel containing information for
	 *                         the new entity.
	 * @param userId           The ID of the user associated with the entity.
	 * @param userType         The type of the user associated with the entity.
	 * @return The newly created entity.
	 */
	T create(SubServicesViewModel subservicesvm, Long userId, String userType);

	/**
	 * Reads the entity with the specified ID.
	 *
	 * @param id The ID of the entity to be read.
	 * @return The entity with the given ID, or null if not found.
	 */
	T read(Long id);

	/**
	 * Deletes the entity associated with the provided SubServicesViewModel.
	 *
	 * @param subservicevm The SubServicesViewModel containing information about the
	 *                     entity to be deleted.
	 */
	void delete(SubServicesViewModel subservicesvm);

	/**
	 * Initializes a SubServicesViewModel with data from the entity with the
	 * specified ID.
	 *
	 * @param id The ID of the entity for which the SubServicesViewModel should be
	 *           initialized.
	 * @return The initialized SubServicesViewModel, or null if the entity is not
	 *         found.
	 */
	SubServicesViewModel initSubService(Long id);

	/**
	 * Retrieves a list of all entities of the specified type.
	 *
	 * @return A list containing all instances of the entity type.
	 */
	List<T> getAll();

	/**
	 * Finds an entity by its name.
	 *
	 * @param name The name of the entity to be found.
	 * @return The entity with the given name, or null if not found.
	 */
	T findByName(String name);

	/**
	 * Finds an entity by its ID.
	 *
	 * @param id The ID of the entity to be found.
	 * @return The entity with the given ID, or null if not found.
	 */
	T findById(Long id);
}
