package triphub.dao.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.TourPackage;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.product.service.restaurant.Restaurant;
import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.TourPackageFormViewModel;

@ApplicationScoped
public class RestaurantDAO implements ServiceInterface {

	@PersistenceContext
	private EntityManager em;

	public RestaurantDAO() {
	}

	public RestaurantDAO(EntityManager em) {
		this.em = em;
		}

	@Override
	public Restaurant create(SubServicesViewModel restaurantvm) {

		// Create Restaurant
		Restaurant restaurant = new Restaurant();
		restaurant.setName(restaurantvm.getName());
		restaurant.setDescription(restaurantvm.getDescription());

		// Create Address
		Address address = new Address();
		address.setNum(restaurantvm.getAddress().getNum());
		address.setStreet(restaurantvm.getAddress().getStreet());
		address.setCity(restaurantvm.getAddress().getCity());
		address.setState(restaurantvm.getAddress().getState());
		address.setCountry(restaurantvm.getAddress().getCountry());
		address.setZipCode(restaurantvm.getAddress().getZipCode());

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


	@Override
	public SubServicesViewModel update(SubServicesViewModel restaurantvm) {

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

	@Override
	public void delete(SubServicesViewModel restaurantvm) {
		Restaurant restaurant = em.find(Restaurant.class, restaurantvm.getId());
		if (restaurant == null) {
			throw new IllegalArgumentException("Restaurant with ID " + restaurantvm.getId() + " not found.");
		}
		restaurant.updateRestaurantViewModel(restaurantvm);
		em.remove(restaurant);
		em.flush();
		
	}
	

	@Override
	public SubServicesViewModel initSubService(Long id) {
		Restaurant restaurant = em.find(Restaurant.class, id);
		if (restaurant == null) {
		return null;
		}
		return restaurant.initRestaurantViewModel();
	}
	
	
	@Override
	public Restaurant read(Long id) {
		return em.find(Restaurant.class, id);
	}

	
	@Override
	public List<Restaurant> getAll() {
		TypedQuery<Restaurant> query = em.createQuery("SELECT d FROM Restaurant d", Restaurant.class);

		return query.getResultList();
	}

	@Override
	public Restaurant findByName(String name) {
		TypedQuery<Restaurant> query = em.createQuery("SELECT r FROM Restaurant r WHERE r.name = :name", Restaurant.class);
		query.setParameter("name", name);

		List<Restaurant> restaurants = query.getResultList();
		return restaurants.isEmpty() ? null : restaurants.get(0);
	}

	@Override
	public Restaurant findById(Long id) {
		return em.find(Restaurant.class,id);
	}

}