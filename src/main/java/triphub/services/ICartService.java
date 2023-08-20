package triphub.services;

import java.math.BigDecimal;
import java.util.List;

import triphub.entity.product.CartItem;
import triphub.entity.user.User;

public interface ICartService {
	
	    void addToCart(Object cartItemObject, User user);
	    void removeFromCart(Long cartItemId, User user);
	    List<Object> getCartItemsByUser(User user);
	    BigDecimal calculateTotalPrice(List<CartItem> cartItems);
	    List<CartItem> getCartItemsWithTourPackages();
	    void updateCartItem(CartItem cartItem);
	
}
