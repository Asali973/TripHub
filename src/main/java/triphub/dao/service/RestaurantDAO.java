package triphub.dao.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.Price;
import triphub.entity.product.service.Service;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.product.service.ServiceType;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.Restaurant;
import triphub.entity.user.Organizer;
import triphub.entity.user.Provider;
import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;

@Stateless
public class RestaurantDAO  {


	@PersistenceContext
	private EntityManager em;

	public RestaurantDAO() {
	}

	public RestaurantDAO(EntityManager em) {
		this.em = em;
		}


	public Restaurant create(SubServicesViewModel restaurantvm, Long userId, String userType) {


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
		restaurant.setId(restaurantvm.getId());
		restaurant.setName(restaurantvm.getName());
		restaurant.setDescription(restaurantvm.getDescription());
		restaurant.setService(service);
		
		Picture picture = new Picture();
		picture.setLink(restaurantvm.getLink());
		restaurantvm.setPicture(picture);
		em.persist(picture);

		// Create Address
		Address address = new Address();
		address.setNum(restaurantvm.getAddress().getNum());
		address.setStreet(restaurantvm.getAddress().getStreet());
		address.setCity(restaurantvm.getAddress().getCity());
		address.setState(restaurantvm.getAddress().getState());
		address.setCountry(restaurantvm.getAddress().getCountry());
		address.setZipCode(restaurantvm.getAddress().getZipCode());
		
		restaurant.setAddress(address);
		
	    if ("organizer".equals(userType)) {
	        Organizer organizer = em.find(Organizer.class, userId);
	        if (organizer == null) {
	            throw new IllegalArgumentException("Organizer with ID " + userId + " not found.");
	        }
	        restaurant.setOrganizer(organizer); // Supposons que cette méthode existe
	    } else if ("provider".equals(userType)) {
	        Provider provider = em.find(Provider.class, userId);
	        if (provider == null) {
	            throw new IllegalArgumentException("Provider with ID " + userId + " not found.");
	        }
	        restaurant.setProvider(provider); // Supposons que cette méthode existe
	    }



		
		em.persist(price);
		em.persist(service);
		em.persist(address);
		em.persist(restaurant);

		em.flush();
		
		return restaurant;
	}

	
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


	public void delete(SubServicesViewModel restaurantvm) {
		Restaurant restaurant = em.find(Restaurant.class, restaurantvm.getId());
		if (restaurant == null) {
			throw new IllegalArgumentException("Restaurant with ID " + restaurantvm.getId() + " not found.");
		}
		restaurant.updateRestaurantViewModel(restaurantvm);
		em.remove(restaurant);
		em.flush();
		
	}
	

	public SubServicesViewModel initSubService(Long id) {
		Restaurant restaurant = em.find(Restaurant.class, id);
		if (restaurant == null) {
		return null;
		}
		return restaurant.initRestaurantViewModel();
	}
	

	public Restaurant read(Long id) {
		return em.find(Restaurant.class, id);
	}

	public List<Restaurant> getAll() {
		TypedQuery<Restaurant> query = em.createQuery("SELECT d FROM Restaurant d", Restaurant.class);

		return query.getResultList();
	}
	

	public Restaurant findByName(String name) {
		TypedQuery<Restaurant> query = em.createQuery("SELECT r FROM Restaurant r WHERE r.name = :name", Restaurant.class);
		query.setParameter("name", name);

		List<Restaurant> restaurants = query.getResultList();
		return restaurants.isEmpty() ? null : restaurants.get(0);
	}

	
	public Restaurant findById(Long id) {
		return em.find(Restaurant.class,id);
	}
	
	public List<Restaurant> getRestaurantForOrganizer(Long organizerId) {
	    TypedQuery<Restaurant> query = em.createQuery("SELECT tp FROM Restaurant tp WHERE tp.organizer.id = :organizerId", Restaurant.class);
	    query.setParameter("organizerId", organizerId);
	    return query.getResultList();
	}
	
	public List<Restaurant> getRestaurantForProvider(Long providerId) {
	    TypedQuery<Restaurant> query = em.createQuery("SELECT tp FROM Restaurant tp WHERE tp.organizer.id = :providerId", Restaurant.class);
	    query.setParameter("providerId", providerId);
	    return query.getResultList();
	}

}