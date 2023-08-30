package triphub.dao.product;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.PersistenceContext;

import triphub.dao.user.UserDAO;
import triphub.entity.product.CartItem;
import triphub.entity.product.TourPackage;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.entity.user.User;

/**
 * DAO for managing cart items related to product purchases.
 */
@Stateless
public class CartItemDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "triphub")
    private EntityManager em;

    @Inject
    private UserDAO userDAO;

    /**
     * Adds a cart item to the database.
     * 
     * @param cartItem The cart item to be added.
     */
    public void addToCart(CartItem cartItem) {
        em.persist(cartItem);
        em.flush();
    }

    /**
     * Updates an existing cart item in the database.
     * 
     * @param cartItem The cart item to be updated.
     */
    public void updateCartItem(CartItem cartItem) {
        em.merge(cartItem);
        em.flush();
    }

    /**
     * Removes a cart item from the database using its ID.
     * 
     * @param cartItemId The ID of the cart item to be removed.
     */
    public void deleteCartItemById(Long cartItemId) {
        CartItem cartItem = em.find(CartItem.class, cartItemId);
        if (cartItem != null) {
            em.remove(cartItem);
            em.flush();
        }
    }

    /**
     * Fetches cart items associated with a specific user.
     * 
     * @param user The user whose cart items need to be fetched.
     * @return List of cart items associated with the user.
     */
    public List<CartItem> getCartItemsByUser(User user) {
        TypedQuery<CartItem> query = em.createQuery("SELECT ci FROM CartItem ci WHERE ci.user = :user", CartItem.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    /**
     * Fetches all cart items with associated tour packages.
     * 
     * @return List of cart items with populated tour packages.
     */
    public List<CartItem> getCartItemsWithTourPackages() {
        TypedQuery<CartItem> query = em.createQuery("SELECT c FROM CartItem c", CartItem.class);
        List<CartItem> cartItems = query.getResultList();

        for (CartItem item : cartItems) {
            Long productId = item.getTourPackage().getId();
            TourPackage tourPackage = em.find(TourPackage.class, productId);
            item.setTourPackage(tourPackage);
        }

        return cartItems;
    }

    /**
     * Fetches all cart items with their associated products.
     * 
     * @return List of cart items with populated products.
     */
    public List<CartItem> getCartItemsWithProducts() {
        TypedQuery<CartItem> query = em.createQuery("SELECT c FROM CartItem c", CartItem.class);
        List<CartItem> cartItems = query.getResultList();

        for (CartItem item : cartItems) {
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
	
	// Below are methods to fetch cart items based on a combination of a specific product and user.
    // Each method is designed for a different product type (TourPackage, Accommodation, Restaurant, Transportation).

	public List<CartItem> getCartItemsByTourPackageAndUser(TourPackage tourPackage, User user) {
	    TypedQuery<CartItem> query = em.createQuery(
	        "SELECT c FROM CartItem c WHERE c.tourPackage = :tourPackage AND c.user = :user", CartItem.class);
	    query.setParameter("tourPackage", tourPackage);
	    query.setParameter("user", user);

	    return query.getResultList();
	}

	public List<CartItem> getCartItemsByAccommodationAndUser(Accommodation accommodation, User user) {
	    TypedQuery<CartItem> query = em.createQuery(
	        "SELECT c FROM CartItem c WHERE c.accommodation = :accommodation AND c.user = :user", CartItem.class);
	    query.setParameter("accommodation", accommodation);
	    query.setParameter("user", user);

	    return query.getResultList();
	}

	public List<CartItem> getCartItemsByRestaurantAndUser(Restaurant restaurant, User user) {
	    TypedQuery<CartItem> query = em.createQuery(
	        "SELECT c FROM CartItem c WHERE c.restaurant = :restaurant AND c.user = :user", CartItem.class);
	    query.setParameter("restaurant", restaurant);
	    query.setParameter("user", user);

	    return query.getResultList();
	}
	public List<CartItem> getCartItemsByTransportationAndUser(Transportation transportation, User user) {
	    TypedQuery<CartItem> query = em.createQuery(
	        "SELECT c FROM CartItem c WHERE c.transportation = :transportation AND c.user = :user", CartItem.class);
	    query.setParameter("transportation", transportation);
	    query.setParameter("user", user);

	    return query.getResultList();
	}

}
