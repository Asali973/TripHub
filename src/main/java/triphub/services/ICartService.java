package triphub.services;

import java.math.BigDecimal;
import java.util.List;

import triphub.entity.product.CartItem;
import triphub.entity.user.User;

public interface ICartService {

	

	public void addToCart(Object cartItemObject, User user, int quantity);

	void removeFromCart(Long cartItemId, User user);

	public List<CartItem> getCartItemsByUser(User user);

	void updateCartItem(CartItem cartItem);

	public BigDecimal calculateTotalPrice(List<CartItem> cartItems);

	public List<CartItem> getCartItemsWithProducts();
}
