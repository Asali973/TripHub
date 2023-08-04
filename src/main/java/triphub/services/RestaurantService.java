package triphub.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import triphub.dao.service.RestaurantDAO;
import triphub.entity.product.service.restaurant.Restaurant;
import triphub.viewModel.SubServicesViewModel;

	@Stateless
	public class RestaurantService {

		@Inject
		private RestaurantDAO restaurantDAO;

		// void Constructor
		public RestaurantService() {
		}

		// charged Constructor
		public RestaurantService(RestaurantDAO restaurantDAO) {
			this.restaurantDAO = restaurantDAO;
		}
		
		public Restaurant create(SubServicesViewModel restaurantvm) {
			return restaurantDAO.create(restaurantvm);
		}
		
		public Restaurant read(Long id) {
			return restaurantDAO.read(id);
		}
		
		public Restaurant update(Restaurant restaurant) {
			return restaurantDAO.update(restaurant);
		}
		
		public void delete(Long id) {
			restaurantDAO.delete(id);
		}
		
		 public List<Restaurant> getAllRestaurant() {
			 return restaurantDAO.getAllRestaurants();
		 }

	}