package triphub.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import triphub.dao.service.RestaurantDAO;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.product.service.restaurant.Restaurant;
import triphub.viewModel.RestaurantViewModel;
import triphub.viewModel.SubServicesViewModel;

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
		public void update() {
			Restaurant restaurant = new Restaurant();
			restaurantDAO.update(restaurant);			
		}

	}