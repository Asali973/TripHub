package triphub.dao.service;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.Price;
import triphub.entity.product.service.Service;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.product.service.ServiceType;
import triphub.entity.service.Restaurant;
import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;

@ApplicationScoped
public class RestaurantDAO implements ServiceInterface{


	@PersistenceContext
	private EntityManager em;

	public RestaurantDAO() {
	}

	public RestaurantDAO(EntityManager em) {
		this.em = em;
		}

	@Override
	public Restaurant create(SubServicesViewModel restaurantvm) {


		// create service 
		
		Service service = Service.createServiceFromViewModel(restaurantvm);
		service.setType(ServiceType.RESTAURANT);
		
		Price price = Price.createPriceFromViewModel(restaurantvm);
		service.setPrice(price);
		
		service.setAvailability(restaurantvm.isAvailability());
		service.setAvailableFrom(restaurantvm.getAvailableFrom());
		service.setAvailableTill(restaurantvm.getAvailableTill());
		
		
		// Create Restaurant
		Restaurant restaurant = new Restaurant();
		restaurant.setName(restaurantvm.getName());
		restaurant.setDescription(restaurantvm.getDescription());
		restaurant.setService(service);

		// Create Address
		Address address = new Address();
		address.setNum(restaurantvm.getAddress().getNum());
		address.setStreet(restaurantvm.getAddress().getStreet());
		address.setCity(restaurantvm.getAddress().getCity());
		address.setState(restaurantvm.getAddress().getState());
		address.setCountry(restaurantvm.getAddress().getCountry());
		address.setZipCode(restaurantvm.getAddress().getZipCode());
		
		restaurant.setAddress(address);


		// Set Picture
//		Picture picture = new Picture();
//		picture.setLink(formService.getLink());
//		restaurant.setPicture(picture);

		em.persist(service);
		em.persist(price);
		em.persist(address);
		em.persist(restaurant);
//		em.persist(picture);
		em.flush();
		
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