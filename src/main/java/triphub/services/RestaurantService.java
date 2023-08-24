package triphub.services;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import triphub.dao.service.RestaurantDAO;
import triphub.entity.product.TourPackage;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.helpers.FacesMessageUtil;
import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.TourPackageFormViewModel;

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


//	@Transactional
//	@Override
//	public Restaurant create(SubServicesViewModel restaurantvm) {
//		return restaurantDAO.create(restaurantvm);
//	}
	
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

	@Override
	public Restaurant read(Long id) {
		return restaurantDAO.read(id);
	}

	
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

	@Override
	public void delete(SubServicesViewModel restaurantvm) {
		restaurantDAO.delete(restaurantvm);		
	}

	@Override
	public SubServicesViewModel initSubService(Long id) {
		 Restaurant restaurant = restaurantDAO.findById(id);
	        if (restaurant == null) {
	            return null;
	        }
	        return restaurant.initRestaurantViewModel();
	}

	@Override
	public List<Restaurant> getAll() {
		return restaurantDAO.getAll();
	}

	@Override
	public Restaurant findByName(String name) {
		return restaurantDAO.findByName(name);
	}

	@Override
	public Restaurant findById(Long id) {
		return restaurantDAO.findById(id);
	}
	
	public List<Restaurant> getRestaurantForOrganizer(Long organizerId) {
		return restaurantDAO.getRestaurantForOrganizer(organizerId);
	}
	
	public List<Restaurant> getRestaurantForProvider(Long providerId) {
		return restaurantDAO.getRestaurantForProvider(providerId);
	}
}