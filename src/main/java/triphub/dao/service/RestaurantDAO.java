package triphub.dao.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.TourPackage;
import triphub.entity.product.service.restaurant.Restaurant;
import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.TourPackageFormViewModel;

@Stateless
public class RestaurantDAO {

	@PersistenceContext
	private EntityManager em;

	public RestaurantDAO() {
	}

	public RestaurantDAO(EntityManager em) {
		this.em = em;
		}

	public Restaurant create(SubServicesViewModel formService) {

		// Create Restaurant
		Restaurant restaurant = new Restaurant();
		restaurant.setName(formService.getName());
		restaurant.setDescription(formService.getDescription());

		// Create Address
		Address address = new Address();
		address.setNum(formService.getAddress().getNum());
		address.setStreet(formService.getAddress().getStreet());
		address.setCity(formService.getAddress().getCity());
		address.setState(formService.getAddress().getState());
		address.setCountry(formService.getAddress().getCountry());
		address.setZipCode(formService.getAddress().getZipCode());

		// Set Picture
//		Picture picture = new Picture();
//		picture.setLink(formService.getLink());
//		restaurant.setPicture(picture);


		em.persist(restaurant);
		em.persist(address);
//		em.persist(picture);
		
		restaurant.setAddress(address);
		return restaurant;
	}



	public Restaurant update(Restaurant restaurant) {
		return em.merge(restaurant);
	}
	
	public SubServicesViewModel updateRestaurant(SubServicesViewModel restaurantvm) {

		Restaurant restaurant = em.find(Restaurant.class, restaurantvm.getId());
		if (restaurant == null) {
			throw new IllegalArgumentException("Restaurant with ID " + restaurantvm.getId() + " not found.");
		}

		restaurant.updateRestaurantViewModel(restaurantvm);
		restaurant = em.merge(restaurant);
		em.flush();

		// Convert the updated entity back to the view model and return it
		return restaurant.initRestaurantViewModel();
		// return tourPackageVm;
	}

	public void delete(Long id) {
		Restaurant restaurant = em.find(Restaurant.class, id);
		if (restaurant != null) {
			em.remove(restaurant);
		}
	}
	
	public Restaurant read(Long id) {
		return em.find(Restaurant.class, id);
	}

	public List<Restaurant> getAllRestaurants() {
		TypedQuery<Restaurant> query = em.createQuery("SELECT d FROM Restaurant d", Restaurant.class);

		return query.getResultList();
	}
}