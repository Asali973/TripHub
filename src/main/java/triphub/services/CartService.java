package triphub.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import triphub.dao.product.CartItemDAO;
import triphub.dao.product.TourPackageDAO;
import triphub.dao.service.AccommodationDAO;
import triphub.dao.service.RestaurantDAO;
import triphub.dao.service.TransportationDAO;
import triphub.entity.product.CartItem;
import triphub.entity.product.TourPackage;
import triphub.entity.product.service.Service;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.entity.user.User;

@Stateless
public class CartService implements ICartService, Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private TourPackageDAO tourPackageDAO;

	@Inject
	private AccommodationDAO accomodationDAO;

	@Inject
	private RestaurantDAO restaurantDAO;

	@Inject
	private TransportationDAO transportationDAO;
	@Inject
	private CartItemDAO cartItemDAO;

	@Override
	public void addToCart(Object cartItemObject, User user) {
		if (cartItemObject instanceof TourPackage) {
			TourPackage tourPackage = (TourPackage) cartItemObject;
			addToCart(tourPackage, user);
		} else if (cartItemObject instanceof Service) {
			Service service = (Service) cartItemObject;
			addToCart(service, user);
		}
	}

	private void addToCart(TourPackage tourPackage, User user) {
		CartItem cartItem = new CartItem();
		cartItem.setUser(user);
		cartItem.setTourPackage(tourPackage);
		cartItem.setDateOfOrder(new Date());
		cartItem.setQuantity(1);

		cartItemDAO.addToCart(cartItem);

		// Optionally, you can recalculate the total price and update the user's cart
		BigDecimal totalPrice = calculateTotalPrice(cartItemDAO.getCartItemsByUser(user));
		user.setCartTotal(totalPrice);
	}

//	private void addToCart(Service service, User user) {
//		CartItem cartItem = new CartItem();
//		cartItem.setUser(user);
//		cartItem.setService(service);
//		cartItem.setDateOfOrder(new Date());
//		cartItem.setQuantity(1);
//		cartItemDAO.addToCart(cartItem);
//
//		// Optionally, you can recalculate the total price and update the user's cart
//		BigDecimal totalPrice = calculateTotalPrice(cartItemDAO.getCartItemsByUser(user));
//		user.setCartTotal(totalPrice);
//	}

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
				// If there are existing cart items for the restaurant, update the quantities
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
				// If there are no existing cart items, create a new one
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
	}

	@Override
	public List<CartItem> getCartItemsByUser(User user) {
		List<CartItem> cartItems = new ArrayList<>();
		List<CartItem> cartItemEntities = cartItemDAO.getCartItemsByUser(user);

		for (CartItem cartItem : cartItemEntities) {
			cartItems.add(cartItem);
		}
		return cartItems;
	}

	@Override
	public BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
	    BigDecimal totalPrice = BigDecimal.ZERO;

	    for (CartItem cartItem : cartItems) {
	        BigDecimal itemPrice = BigDecimal.ZERO;

	        if (cartItem.getTourPackage() != null) {
	            itemPrice = cartItem.getTourPackage().getPrice().getAmount();
	        } else if (cartItem.getRestaurant() != null) {
	            itemPrice = cartItem.getRestaurant().getService().getPrice().getAmount();
	        } else if (cartItem.getAccommodation() != null) {
	            itemPrice = cartItem.getAccommodation().getService().getPrice().getAmount();
	        } else if (cartItem.getTransportation() != null) {
	            itemPrice = cartItem.getTransportation().getService().getPrice().getAmount();
	        }

	        totalPrice = totalPrice.add(itemPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity())));
	    }

	    return totalPrice;
	}


	@Override
	public void removeFromCart(Long cartItemId, User user) {
		cartItemDAO.deleteCartItemById(cartItemId);

		// Recalculate the total price and update the user's cart
		BigDecimal totalPrice = calculateTotalPrice(cartItemDAO.getCartItemsByUser(user));
		user.setCartTotal(totalPrice);
	}

	@Override
	public List<CartItem> getCartItemsWithTourPackages() {
		return cartItemDAO.getCartItemsWithTourPackages();
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
