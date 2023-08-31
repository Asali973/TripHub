package triphub.services;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import triphub.dao.service.RestaurantDAO;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.subservices.Restaurant;

import triphub.helpers.FacesMessageUtil;
import triphub.viewModel.SubServicesViewModel;

/**
 * This class represents a service for managing restaurants within the TripHub application.
 * It provides methods to create, read, update, and delete restaurants, as well as retrieve
 * information about restaurants.
 *
 */


@ApplicationScoped
public class RestaurantService implements ServiceInterface,Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private RestaurantDAO restaurantDAO;

	// void Constructor
	public RestaurantService() {
	}

	// charged Constructor
	public RestaurantService(RestaurantDAO restaurantDAO) {
		this.restaurantDAO = restaurantDAO;
	}

	
	 /**
     * Creates a restaurant based on the provided view model and user information.
     *
     * @param restaurantvm The view model containing restaurant information.
     * @param userId       The ID of the user creating the restaurant.
     * @param userType     The type of user creating the restaurant.
     * @return The created Restaurant object.
     */
	@Transactional
	@Override
	public Restaurant create(SubServicesViewModel restaurantvm, Long userId, String userType) {
		
		try {
            return restaurantDAO.create(restaurantvm, userId, userType); // Call create() method of DAO
        } catch (Exception e) {
            // Handle any unexpected exceptions that might occur during the create process
            FacesMessageUtil.addErrorMessage("Failed to create restaurant. An unexpected error occurred.");
        }
		return null;		
	}
	
	 /**
     * Retrieves a restaurant by its ID.
     *
     * @param id The ID of the restaurant to retrieve.
     * @return The retrieved Restaurant object, or null if not found.
     */
	@Override
	public Restaurant read(Long id) {
		return restaurantDAO.read(id);
	}

	/**
     * Updates a restaurant based on the provided view model.
     *
     * @param restaurantvm The view model containing updated restaurant information.
     */	
	public void update(SubServicesViewModel restaurantvm) {
		try {
			restaurantDAO.update(restaurantvm);
		} catch (IllegalArgumentException e) {
			// Handle the case when the restaurant with the provided ID was not found in
			// the DAO
			FacesMessageUtil.addErrorMessage("Failed to update restaurant: " + e.getMessage());
		} catch (Exception e) {
			// Handle any other unexpected exceptions that might occur during the update
			// process
			FacesMessageUtil.addErrorMessage("Failed to update restaurant. An unexpected error occurred.");
		}
		
	}
	  /**
     * Deletes a restaurant based on the provided view model.
     *
     * @param restaurantvm The view model containing restaurant information to be deleted.
     */

	@Override
	public void delete(SubServicesViewModel restaurantvm) {
		restaurantDAO.delete(restaurantvm);		
	}
	 /**
     * Initializes a sub-service (restaurant) based on the provided ID.
     *
     * @param id The ID of the restaurant to be initialized.
     * @return The initialized SubServicesViewModel representing the restaurant.
     */

	@Override
	public SubServicesViewModel initSubService(Long id) {
		 Restaurant restaurant = restaurantDAO.findById(id);
	        if (restaurant == null) {
	            return null;
	        }
	        return restaurant.initRestaurantViewModel();
	}

	 /**
     * Retrieves a list of all restaurants.
     *
     * @return A list containing all restaurants.
     */
    @Override
    public List<Restaurant> getAll() {
        return restaurantDAO.getAll();
    }

    /**
     * Finds a restaurant by its name.
     *
     * @param name The name of the restaurant to find.
     * @return The found Restaurant object, or null if not found.
     */
    @Override
    public Restaurant findByName(String name) {
        return restaurantDAO.findByName(name);
    }

    /**
     * Retrieves a restaurant by its ID.
     *
     * @param id The ID of the restaurant to retrieve.
     * @return The retrieved Restaurant object, or null if not found.
     */
    @Override
    public Restaurant findById(Long id) {
        return restaurantDAO.findById(id);
    }
    
    /**
     * Retrieves a restaurant by its ID.
     *
     * @param id The ID of the restaurant to retrieve.
     * @return The retrieved Restaurant object, or null if not found.
     */
    public Restaurant getRestaurantById(Long id) {
        return restaurantDAO.read(id);
    }
    
    /**
     * Retrieves a list of restaurants associated with a specific organizer.
     *
     * @param organizerId The ID of the organizer.
     * @return A list of restaurants associated with the organizer.
     */
    public List<Restaurant> getRestaurantForOrganizer(Long organizerId) {
        return restaurantDAO.getRestaurantForOrganizer(organizerId);
    }
    
    /**
     * Retrieves a list of restaurants associated with a specific provider.
     *
     * @param providerId The ID of the provider.
     * @return A list of restaurants associated with the provider.
     */
    public List<Restaurant> getRestaurantForProvider(Long providerId) {
        return restaurantDAO.getRestaurantForProvider(providerId);
    }
}