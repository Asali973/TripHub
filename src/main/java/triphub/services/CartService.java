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
import triphub.entity.user.User;

@Stateless
public class CartService implements ICartService,Serializable {
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

    private void addToCart(Service service, User user) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setService(service);
        cartItem.setDateOfOrder(new Date());
        cartItem.setQuantity(1);
        cartItemDAO.addToCart(cartItem);

        // Optionally, you can recalculate the total price and update the user's cart
        BigDecimal totalPrice = calculateTotalPrice(cartItemDAO.getCartItemsByUser(user));
        user.setCartTotal(totalPrice);
    }
    
//    @Override
//    public void addToCart(Object cartItemObject, User user, int quantity) {    
//
//        if (cartItemObject instanceof TourPackage) {
//            TourPackage tourPackage = (TourPackage) cartItemObject;
//            CartItem existingCartItem = cartItemDAO.getCartItemByTourPackageAndUser(tourPackage, user);
//
//            if (existingCartItem != null) {
//                // If the cart item already exists, update the quantity
//                existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
//                existingCartItem.setTotalPrice(existingCartItem.getTotalPrice().add(tourPackage.getPrice().getAmount().multiply(BigDecimal.valueOf(quantity))));
//                cartItemDAO.updateCartItem(existingCartItem);
//            } else {
//                // If the cart item doesn't exist, create a new one
//                CartItem newCartItem = new CartItem();
//                newCartItem.setUser(user);
//                newCartItem.setTourPackage(tourPackage);
//                newCartItem.setQuantity(quantity);
//                newCartItem.setTotalPrice(tourPackage.getPrice().getAmount().multiply(BigDecimal.valueOf(quantity)));
//                newCartItem.setDateOfOrder(new Date());
//                cartItemDAO.addToCart(newCartItem);
//            }
//        } else if (cartItemObject instanceof Service) {
//            Service service = (Service) cartItemObject;
//            CartItem existingCartItem = cartItemDAO.getCartItemByServiceAndUser(service, user);
//
//            if (existingCartItem != null) {
//                // If the cart item already exists, update the quantity
//                existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
//                existingCartItem.setTotalPrice(existingCartItem.getTotalPrice().add(service.getPrice().getAmount().multiply(BigDecimal.valueOf(quantity))));
//                cartItemDAO.updateCartItem(existingCartItem);
//            } else {
//                // If the cart item doesn't exist, create a new one
//                CartItem newCartItem = new CartItem();
//                newCartItem.setUser(user);
//                newCartItem.setService(service);
//                newCartItem.setQuantity(quantity);
//                newCartItem.setTotalPrice(service.getPrice().getAmount().multiply(BigDecimal.valueOf(quantity)));
//                newCartItem.setDateOfOrder(new Date());
//                cartItemDAO.addToCart(newCartItem);
//            }
//        }
//    }



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
            if (cartItem.getTourPackage() != null) {
                totalPrice = totalPrice.add(cartItem.getTourPackage().getPrice().getAmount());
            } else if (cartItem.getService() != null) {
                totalPrice = totalPrice.add(cartItem.getService().getPrice().getAmount());
            }
            // Adjust the conditions and calculation based on your cart item structure
        }
        return totalPrice;
    }
    
    @Override
    public void removeFromCart(Long cartItemId, User user) {
        cartItemDAO.deleteCartItemById(cartItemId);

        // Recalculate the total price and update the user's cart
        BigDecimal totalPrice = calculateTotalPrice(cartItemDAO.getCartItemsByUser(user));
        user.setCartTotal(totalPrice);

        // Update the cart items' quantities (example: set to 0)
        List<CartItem> cartItems = cartItemDAO.getCartItemsByUser(user);
        for (CartItem cartItem : cartItems) {
            cartItem.setQuantity(0);
            cartItemDAO.updateCartItem(cartItem);
        }
    }
    @Override
    public List<CartItem> getCartItemsWithTourPackages() {
        return cartItemDAO.getCartItemsWithTourPackages();
    }
    @Override
    public void updateCartItem(CartItem cartItem) {
        cartItemDAO.updateCartItem(cartItem);
    }
    
}





