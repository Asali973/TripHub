package triphub.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.inject.Inject;
import triphub.dao.product.CartItemDAO;
import triphub.dao.user.UserDAO;
import triphub.entity.product.CartItem;
import triphub.entity.product.TourPackage;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.entity.user.User;

/**
 * * Provides business logic services related to Cart.
 * This service class manages cart operations such as adding items to the cart,
 * removing items from the cart, updating cart items, and calculating the total
 * price for a user's cart.
 * 
 * <p>
 * It interacts with two data access objects (DAOs) to handle operations related
 * to CartItem and User.
 * 
 * <p>
 * The {@code @Inject} annotations are used to inject the required DAOs into
 * this service, allowing it to interact with the underlying database and manage
 * cart operations for the platform.
 * 
 * @see CartItem
 * @see User
 */
@Stateless
public class CartService implements ICartService, Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private CartItemDAO cartItemDAO;
	@Inject
	private UserDAO userDAO;

	/**
	 * Adds the specified item to the user's cart. The method is capable of handling
	 * multiple types of items such as TourPackages, Accommodations, Restaurants,
	 * and Transportations.
	 * 
	 * If the item is already present in the user's cart, the quantity and total
	 * price of the item will be updated. If the item is not present, a new CartItem
	 * will be created.
	 *
	 * After processing the item, the method recalculates the total price for all
	 * items in the user's cart and updates the user's cart total.
	 * 
	 * @param cartItemObject The item object to be added to the cart. Must be an
	 *                       instance of TourPackage, Accommodation, Restaurant, or
	 *                       Transportation.
	 * @param user           The user for whom the item is to be added to the cart.
	 * @param quantity       The quantity of the item to be added.
	 * 
	 * @throws IllegalArgumentException if cartItemObject is not an instance of any
	 *                                  of the expected types.
	 * @throws NullPointerException     if any of the required fields in the cart
	 *                                  item or service are null.
	 */

	@Override
	public void addToCart(Object cartItemObject, User user, int quantity) {
		if (cartItemObject instanceof TourPackage) {

			TourPackage tourPackage = (TourPackage) cartItemObject;
			List<CartItem> existingCartItems = cartItemDAO.getCartItemsByTourPackageAndUser(tourPackage, user);

			if (!existingCartItems.isEmpty()) {
				// If there are existing cart items for the tour package, update the quantities
				for (CartItem existingCartItem : existingCartItems) {
					existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
					if (existingCartItem.getTotalPrice() == null) {
						existingCartItem.setTotalPrice(BigDecimal.ZERO);
					}
					BigDecimal newTotalPrice = existingCartItem.getTotalPrice()
							.add(tourPackage.getPrice().getAmount().multiply(BigDecimal.valueOf(quantity)));
					existingCartItem.setTotalPrice(newTotalPrice);
					cartItemDAO.updateCartItem(existingCartItem);

				}
			} else {
				// If there are no existing cart items, create a new one
				CartItem newCartItem = new CartItem();
				newCartItem.setUser(user);
				newCartItem.setTourPackage(tourPackage);
				newCartItem.setQuantity(1);
				newCartItem.setTotalPrice(tourPackage.getPrice().getAmount().multiply(BigDecimal.valueOf(quantity)));
				newCartItem.setDateOfOrder(new Date());
				cartItemDAO.addToCart(newCartItem);
			}
		} else if (cartItemObject instanceof Accommodation) {
			Accommodation accommodation = (Accommodation) cartItemObject;
			List<CartItem> existingCartItems = cartItemDAO.getCartItemsByAccommodationAndUser(accommodation, user);

			if (!existingCartItems.isEmpty()) {

				for (CartItem existingCartItem : existingCartItems) {
					existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
					if (existingCartItem.getTotalPrice() == null) {
						existingCartItem.setTotalPrice(BigDecimal.ZERO);
					}
					BigDecimal newTotalPrice = existingCartItem.getTotalPrice().add(
							accommodation.getService().getPrice().getAmount().multiply(BigDecimal.valueOf(quantity)));
					existingCartItem.setTotalPrice(newTotalPrice);
					cartItemDAO.updateCartItem(existingCartItem);
				}
			} else {

				CartItem newCartItem = new CartItem();
				newCartItem.setUser(user);
				newCartItem.setAccommodation(accommodation);
				newCartItem.setQuantity(1);
				newCartItem.setTotalPrice(
						accommodation.getService().getPrice().getAmount().multiply(BigDecimal.valueOf(quantity)));
				newCartItem.setDateOfOrder(new Date());
				cartItemDAO.addToCart(newCartItem);
			}
		} else if (cartItemObject instanceof Restaurant) {
			Restaurant restaurant = (Restaurant) cartItemObject;
			List<CartItem> existingCartItems = cartItemDAO.getCartItemsByRestaurantAndUser(restaurant, user);

			if (!existingCartItems.isEmpty()) {

				for (CartItem existingCartItem : existingCartItems) {
					existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
					if (existingCartItem.getTotalPrice() == null) {
						existingCartItem.setTotalPrice(BigDecimal.ZERO);
					}
					BigDecimal newTotalPrice = existingCartItem.getTotalPrice()
							.add(restaurant.getService().getPrice().getAmount().multiply(BigDecimal.valueOf(quantity)));
					existingCartItem.setTotalPrice(newTotalPrice);
					cartItemDAO.updateCartItem(existingCartItem);
				}
			} else {

				CartItem newCartItem = new CartItem();
				newCartItem.setUser(user);
				newCartItem.setRestaurant(restaurant);
				newCartItem.setQuantity(1);
				newCartItem.setTotalPrice(
						restaurant.getService().getPrice().getAmount().multiply(BigDecimal.valueOf(quantity)));
				newCartItem.setDateOfOrder(new Date());
				cartItemDAO.addToCart(newCartItem);
			}

		} else if (cartItemObject instanceof Transportation) {
			Transportation transportation = (Transportation) cartItemObject;
			List<CartItem> existingCartItems = cartItemDAO.getCartItemsByTransportationAndUser(transportation, user);

			if (!existingCartItems.isEmpty()) {

				for (CartItem existingCartItem : existingCartItems) {
					existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
					if (existingCartItem.getTotalPrice() == null) {
						existingCartItem.setTotalPrice(BigDecimal.ZERO);
					}
					BigDecimal newTotalPrice = existingCartItem.getTotalPrice().add(
							transportation.getService().getPrice().getAmount().multiply(BigDecimal.valueOf(quantity)));
					existingCartItem.setTotalPrice(newTotalPrice);
					cartItemDAO.updateCartItem(existingCartItem);
				}
			} else {

				CartItem newCartItem = new CartItem();
				newCartItem.setUser(user);
				newCartItem.setTransportation(transportation);
				newCartItem.setQuantity(1);
				newCartItem.setTotalPrice(
						transportation.getService().getPrice().getAmount().multiply(BigDecimal.valueOf(quantity)));
				newCartItem.setDateOfOrder(new Date());
				cartItemDAO.addToCart(newCartItem);
			}
		}

		// Recalculate the total price and update the user's cart total
		BigDecimal totalPrice = calculateTotalPrice(cartItemDAO.getCartItemsByUser(user));
		user.setCartTotal(totalPrice);
		userDAO.updateUser(user);
	}

	/**
	 * Helper method: Retrieves the list of cart items associated with a specific user.
	 * 
	 * @param user The user for whom the cart items are to be retrieved.
	 * @return A list of CartItem objects associated with the given user.
	 */
	@Override
	public List<CartItem> getCartItemsByUser(User user) {
		List<CartItem> cartItems = new ArrayList<>();
		List<CartItem> cartItemEntities = cartItemDAO.getCartItemsByUser(user);

		for (CartItem cartItem : cartItemEntities) {
			cartItems.add(cartItem);
		}
		return cartItems;
	}
	
	/**
	 * Calculates the total price of a list of cart items. If the list is null, 
	 * this method will return a total price of ZERO.
	 * 
	 * @param cartItems A list of CartItem objects for which the total price is to be calculated.
	 * @return The total price of all the cart items provided.
	 */
	@Override
	public BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
		if (cartItems == null) {
			return BigDecimal.ZERO;
		}
		return cartItems.stream().map(CartItem::getTotalPrice).filter(Objects::nonNull).reduce(BigDecimal.ZERO,
				BigDecimal::add);
	}
	
	/**
	 * Removes a specific cart item from the cart based on the provided cart item ID.
	 * After removing the item, the method recalculates the total price for all items 
	 * in the user's cart and updates the user's cart total.
	 * 
	 * @param cartItemId The ID of the cart item to be removed.
	 * @param user The user from whose cart the item is to be removed.
	 */
	@Override
	public void removeFromCart(Long cartItemId, User user) {
		cartItemDAO.deleteCartItemById(cartItemId);

		// Recalculate the total price and update the user's cart
		BigDecimal totalPrice = calculateTotalPrice(cartItemDAO.getCartItemsByUser(user));
		user.setCartTotal(totalPrice);
	}

	@Override
	public void updateCartItem(CartItem cartItem) {
		cartItemDAO.updateCartItem(cartItem);
	}

	@Override
	public List<CartItem> getCartItemsWithProducts() {
		return cartItemDAO.getCartItemsWithProducts();
	}
}
