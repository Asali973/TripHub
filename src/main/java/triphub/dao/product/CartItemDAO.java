package triphub.dao.product;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.dao.user.UserDAO;
import triphub.entity.product.CartItem;
import triphub.entity.product.TourPackage;
import triphub.entity.product.service.Service;
import triphub.entity.service.Accommodation;
import triphub.entity.service.Restaurant;
import triphub.entity.service.Transportation;
import triphub.entity.user.User;
import triphub.viewModel.CartViewModel;

@Stateless
public class CartItemDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "triphub")
	private EntityManager em;
	@Inject
	private UserDAO userDAO;

	public void addToCart(CartItem cartItem) {
		em.persist(cartItem);
		em.flush();
	}

	public void updateCartItem(CartItem cartItem) {
		em.merge(cartItem);
		em.flush();
	}

	public void deleteCartItemById(Long cartItemId) {
		CartItem cartItem = em.find(CartItem.class, cartItemId);
		if (cartItem != null) {
			em.remove(cartItem);
			em.flush();
		}
	}

	public List<CartItem> getCartItemsByUser(User user) {
		TypedQuery<CartItem> query = em.createQuery("SELECT ci FROM CartItem ci WHERE ci.user = :user", CartItem.class);
		query.setParameter("user", user);
		return query.getResultList();
	}

	public List<CartItem> getCartItemsWithTourPackages() {
		TypedQuery<CartItem> query = em.createQuery("SELECT c FROM CartItem c", CartItem.class);
		List<CartItem> cartItems = query.getResultList();

		// Populate the selectedTourPackage property for each cart item
		for (CartItem item : cartItems) {
			Long productId = item.getTourPackage().getId();
			TourPackage tourPackage = em.find(TourPackage.class, productId);
			item.setTourPackage(tourPackage);

		}

		return cartItems;
	}

	public List<CartItem> getCartItemsWithProducts() {
	    TypedQuery<CartItem> query = em.createQuery("SELECT c FROM CartItem c", CartItem.class);
	    List<CartItem> cartItems = query.getResultList();

	    for (CartItem item : cartItems) {
	        // You don't need to fetch these entities again if you've set up your JPA relationships correctly 
	        // with lazy loading because the entities will be fetched automatically when accessed.	     

	        if (item.getTourPackage() != null) {
	            item.setTourPackage(em.find(TourPackage.class, item.getTourPackage().getId()));
	        } 
	        if (item.getAccommodation() != null) {
	            item.setAccommodation(em.find(Accommodation.class, item.getAccommodation().getId()));
	        } 
	        if (item.getRestaurant() != null) {
	            item.setRestaurant(em.find(Restaurant.class, item.getRestaurant().getId()));
	        } 
	        if (item.getTransportation() != null) {
	            item.setTransportation(em.find(Transportation.class, item.getTransportation().getId()));
	        }
	    }

	    return cartItems;
	}

//	public CartItem getCartItemByTourPackageAndUser(TourPackage tourPackage, User user) {
//		TypedQuery<CartItem> query = em.createQuery(
//				"SELECT c FROM CartItem c WHERE c.tourPackage = :tourPackage AND c.user = :user", CartItem.class);
//		query.setParameter("tourPackage", tourPackage);
//		query.setParameter("user", user);
//
//		List<CartItem> results = query.getResultList();
//
//		if (results.isEmpty()) {
//			return null;
//		} else if (results.size() == 1) {
//			return results.get(0);
//		} else {
//			// Handle multiple matches (log an error, return a specific result, etc.)
//			// For example, you might throw an exception:
//			throw new NonUniqueResultException("Multiple cart items found for the given criteria");
//		}
//	}
	public List<CartItem> getCartItemsByTourPackageAndUser(TourPackage tourPackage, User user) {
	    TypedQuery<CartItem> query = em.createQuery(
	        "SELECT c FROM CartItem c WHERE c.tourPackage = :tourPackage AND c.user = :user", CartItem.class);
	    query.setParameter("tourPackage", tourPackage);
	    query.setParameter("user", user);

	    return query.getResultList();
	}

	public CartItem getCartItemByServiceAndUser(Service service, User user) {
		TypedQuery<CartItem> query = em
				.createQuery("SELECT c FROM CartItem c WHERE c.user = :user AND c.service = :service", CartItem.class);
		query.setParameter("user", user);
		query.setParameter("service", service);

		List<CartItem> results = query.getResultList();

		if (results.isEmpty()) {
			return null;
		} else if (results.size() == 1) {
			return results.get(0);
		} else {
			// Handle multiple matches (log an error, return a specific result, etc.)
			// For example, you might throw an exception:
			throw new NonUniqueResultException("Multiple cart items found for the given criteria");
		}
	}

}
