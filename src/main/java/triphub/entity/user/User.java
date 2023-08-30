package triphub.entity.user;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import triphub.entity.product.CartItem;
import triphub.entity.util.Address;
import triphub.entity.util.FinanceInfo;
import triphub.helpers.PasswordUtils;
import triphub.viewModel.UserViewModel;

/**
 * Represents a user entity in the system. A user has attributes such as name,
 * contact details, and financial information. Additionally, a user has an
 * associated cart to keep track of items they wish to purchase.
 */
@Entity
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String lastName;
	private String email;
	private String phoneNum;
	private String password;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	@OneToOne(cascade = CascadeType.ALL)
	private FinanceInfo finance;

	@OneToMany(mappedBy = "user")
	private List<CartItem> cartItems = new ArrayList<>();

	@Column(name = "cart_total", nullable = true)
	private BigDecimal cartTotal;

	/**
	 * Adds a CartItem to the user's cart and updates the total cart value.
	 * 
	 * @param item The item to be added.
	 */
	public void addCartItem(CartItem item) {
		if (this.cartTotal == null) {
			this.cartTotal = BigDecimal.ZERO;
		}
		this.cartItems.add(item);
		if (item.getTotalPrice() != null) {
			this.cartTotal = this.cartTotal.add(item.getTotalPrice());
		}
	}

	/**
	 * Removes a CartItem from the user's cart and updates the total cart value.
	 * 
	 * @param item The item to be removed.
	 */
	public void removeCartItem(CartItem item) {
		if (this.cartTotal == null) {
			this.cartTotal = BigDecimal.ZERO;
		}
		this.cartItems.remove(item);
		if (item.getTotalPrice() != null) {
			this.cartTotal = this.cartTotal.subtract(item.getTotalPrice());
		}
	}

	/**
	 * Updates the user's cart total.
	 */
	private void updateCartTotal() {
		this.cartTotal = this.getCartTotal();
	}

	/**
	 * Constructs and returns a User entity from the provided view model.
	 * 
	 * @param form ViewModel containing user details.
	 * @return A newly created User object.
	 */
	public static User createUserFromViewModel(UserViewModel form) {
		User user = new User();
		user.setId(form.getUserId());
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setEmail(form.getEmail());
		user.setPhoneNum(form.getPhoneNum());
		user.setPassword(PasswordUtils.getInstance().hashPassword(form.getPassword()));
		user.setCartTotal(BigDecimal.ZERO); // Set to zero during initial creation

		return user;
	}

	/**
	 * Updates the current User entity's attributes from the provided view model.
	 * 
	 * @param form ViewModel containing updated user details.
	 */
	public void updateUserFromViewModel(UserViewModel form) {
		this.setFirstName(form.getFirstName());
		this.setLastName(form.getLastName());
		this.setEmail(form.getEmail());
		this.setPhoneNum(form.getPhoneNum());
		this.setPassword(PasswordUtils.getInstance().hashPassword(form.getPassword()));
		this.updateCartTotal();
	}

	/**
	 * Initializes a UserViewModel from the current User entity.
	 * 
	 * @param userViewModel ViewModel to be populated with user details.
	 */
	public void initUserViewModel(UserViewModel userViewModel) {
		userViewModel.setUserId(this.getId());
		userViewModel.setFirstName(this.getFirstName());
		userViewModel.setLastName(this.getLastName());
		userViewModel.setEmail(this.getEmail());
		userViewModel.setPhoneNum(this.getPhoneNum());
		userViewModel.setCartTotal(this.getCartTotal());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public FinanceInfo getFinance() {
		return finance;
	}

	public void setFinance(FinanceInfo finance) {
		this.finance = finance;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	/**
	 * Retrieves the total price of all items in the user's cart. If the cart is
	 * empty or null, it returns a value of 0.
	 * 
	 * @return The total price of all items in the cart.
	 */

	public BigDecimal getCartTotal() {
		// If the cart items list is null, return 0
		if (cartItems == null) {
			return BigDecimal.ZERO;
		}

		// Stream processing:
		// 1. Convert each cart item to its total price using CartItem::getTotalPrice
		// 2. Filter out any null values (items that might not have a price)
		// 3. Reduce the stream by summing all prices together
		return cartItems.stream().map(CartItem::getTotalPrice).filter(Objects::nonNull).reduce(BigDecimal.ZERO,
				BigDecimal::add);
	}

	public void setCartTotal(BigDecimal cartTotal) {
		this.cartTotal = cartTotal;
	}

}
