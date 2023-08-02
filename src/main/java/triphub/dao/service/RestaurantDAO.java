package triphub.dao.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.service.restaurant.Restaurant;
import triphub.viewModel.RestaurantViewModel;

public class RestaurantDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public RestaurantDAO() {
	}
	
	public RestaurantDAO(EntityManager em) {
		this.em = em;
		}
	
	public Restaurant create(RestaurantViewModel restaurantvm) {
		
		Restaurant restaurant = new Restaurant();
		restaurant.setNameRestaurant(restaurantvm.getNameRestaurant());
		restaurant.setAddress(restaurantvm.getAddressRestaurant());
		restaurant.setDescription(restaurantvm.getDescription());
		
		em.persist(restaurant);
		
		return restaurant;
	}
	
	
	public Restaurant read(Long id) {
		return em.find(Restaurant.class, id);
	}

	public Restaurant update(Restaurant restaurant) {
	    return em.merge(restaurant);
	}
	
	public void delete(Long id) {
		Restaurant restaurant = em.find(Restaurant.class, id);
	    if (restaurant != null) {
	        em.remove(restaurant);
	    }
	}
	
	 public List<Restaurant> getAllRestaurants() {
		 TypedQuery<Restaurant> query = em.createQuery("SELECT d FROM Restaurant d", Restaurant.class);
		 
         return query.getResultList();
	 }
}