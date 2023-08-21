package triphub.services;

import java.math.BigDecimal;
import java.util.List;

import triphub.entity.product.CartItem;
import triphub.entity.user.User;

public interface ICartService  {
	
	  void addToCart(Object cartItemObject, User user);
	// public void addToCart(Object cartItemObject, User user, int quantity);
	    void removeFromCart(Long cartItemId, User user);
	    List<CartItem> getCartItemsByUser(User user);	  
	    List<CartItem> getCartItemsWithTourPackages();
	    void updateCartItem(CartItem cartItem);
	    BigDecimal calculateTotalPrice(List<CartItem> cartItems);
}
