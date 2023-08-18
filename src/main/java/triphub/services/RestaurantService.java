package triphub.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import triphub.dao.service.RestaurantDAO;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.product.service.restaurant.Restaurant;
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

	public void delete(Long id) {
		restaurantDAO.delete(id);
	}

	public List<Restaurant> getAllRestaurant() {
		return restaurantDAO.getAllRestaurants();
	}

	@Override
	public void create(SubServicesViewModel restaurantvm) {
		restaurantDAO.create(restaurantvm);
	}

	public List<Restaurant> getAllRestaurants() {
		return restaurantDAO.getAllRestaurants();
	}

	@Override
	public void read(Long id) {
		restaurantDAO.read(id);
	}

	@Override
	public void update(SubServicesViewModel restaurantvm) {
		try {
			restaurantDAO.updateRestaurant(restaurantvm);
		} catch (IllegalArgumentException e) {
			// Handle the case when the tour package with the provided ID was not found in
			// the DAO
			FacesMessageUtil.addErrorMessage("Failed to update restaurant: " + e.getMessage());
		} catch (Exception e) {
			// Handle any other unexpected exceptions that might occur during the update
			// process
			FacesMessageUtil.addErrorMessage("Failed to update restaurant. An unexpected error occurred.");
		}
	}
}