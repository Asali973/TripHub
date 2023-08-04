package triphub.dao.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.service.restaurant.Restaurant;
import triphub.entity.util.Address;
import triphub.entity.util.Picture;
import triphub.viewModel.SubServicesViewModel;

@Stateless
public class RestaurantDAO {

	@PersistenceContext
	private EntityManager em;

	public RestaurantDAO() {
	}

//	public RestaurantDAO(EntityManager em) {
//		this.em = em;
//		}

	public Restaurant create(SubServicesViewModel formService) {

		// Create Restaurant
		Restaurant restaurant = new Restaurant();
		restaurant.setNameRestaurant(formService.getName());
		restaurant.setDescription(formService.getDescription());

		// Create Address
		Address address = new Address();
		address.setNum(formService.getNum());
		address.setStreet(formService.getStreet());
		address.setCity(formService.getCity());
		address.setState(formService.getState());
		address.setCountry(formService.getCountry());
		address.setZipCode(formService.getZipCode());

		// Set Picture
		Picture picture = new Picture();
		picture.setLink(formService.getLink());
		restaurant.setPicture(picture);


		em.persist(restaurant);
		em.persist(address);
		em.persist(picture);
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
