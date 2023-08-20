package triphub.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import triphub.dao.service.RestaurantDAO;
import triphub.entity.product.TourPackage;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.service.Restaurant;
import triphub.helpers.FacesMessageUtil;
import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.TourPackageFormViewModel;

@ApplicationScoped
public class RestaurantService implements ServiceInterface {

	@Inject
	private RestaurantDAO restaurantDAO;

	// void Constructor
	public RestaurantService() {
	}

	// charged Constructor
	public RestaurantService(RestaurantDAO restaurantDAO) {
		this.restaurantDAO = restaurantDAO;
	}

	public List<Restaurant> getAllRestaurant() {
		return restaurantDAO.getAll();
	}


	
	
	@Override
	public Restaurant create(SubServicesViewModel restaurantvm) {
		return restaurantDAO.create(restaurantvm);
	}

	@Override
	public Restaurant read(Long id) {
		return restaurantDAO.read(id);
	}

	@Override
	public SubServicesViewModel update(SubServicesViewModel restaurantvm) {
		try {
			restaurantDAO.update(restaurantvm);
		} catch (IllegalArgumentException e) {
			// Handle the case when the tour package with the provided ID was not found in
			// the DAO
			FacesMessageUtil.addErrorMessage("Failed to update restaurant: " + e.getMessage());
		} catch (Exception e) {
			// Handle any other unexpected exceptions that might occur during the update
			// process
			FacesMessageUtil.addErrorMessage("Failed to update restaurant. An unexpected error occurred.");
		}
		return restaurantvm;
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
}