package triphub.managedBeans.products;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import triphub.dao.service.AccommodationDAO;
import triphub.dao.service.RestaurantDAO;
import triphub.dao.service.TransportationDAO;
import triphub.entity.product.CartItem;
import triphub.entity.product.TourPackage;

import triphub.entity.subservices.*;

import triphub.entity.user.User;
import triphub.helpers.FacesMessageUtil;
import triphub.services.AccommodationService;
import triphub.services.ICartService;
import triphub.services.RestaurantService;
import triphub.services.TourPackageService;
import triphub.services.TransportationService;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;

/**
 * CartBean is a managed bean responsible for handling all shopping cart
 * operations for a user. This includes tasks like adding products to the cart,
 * removing items from the cart, updating cart item quantities, and navigating
 * to the checkout process.
 * 
 */
@Named("cartBean")
@RequestScoped
public class CartBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ICartService iCartService;
	@Inject
	private TourPackageService tourPackageService;
	@Inject
	private UserService userService;
	@Inject
	AccommodationService accommodationService;
	@Inject
	RestaurantService restaurantService;
	@Inject
	TransportationService transportationService;
	@Inject
	private TourPackageBean tourPackageBean;
	@Inject
	private AccommodationBean accommodationBean;
	@Inject
	private RestaurantBean restaurantBean;
	@Inject
	private TransportationBean transportationBean;

	@Inject
	private AccommodationDAO accomodationDAO;

	@Inject
	private RestaurantDAO restaurantDAO;

	@Inject
	private TransportationDAO transportationDAO;
	private UserViewModel userViewModel = new UserViewModel();

	private CartItem currentCartItem;
	private List<CartItem> cartItems;
	private Restaurant selectedRestaurant;
	private TourPackage selectedTourPackage;
	private Accommodation selectedAccommodation;
	private Transportation selectedTransportation;
	private Long selectedPackageId;
	private Long selectedRestaurantId;
	private Long selectedAccommodationId;
	private Long selectedTransportationId;
	private int selectedQuantity;
	private Date dateOfPurchase;

	private String restaurantName;
	private BigDecimal restaurantPrice;
	private String restaurantCurrency;

	private Long organizerId;

	/**
	 * Initialization method that is called post construction of the bean. It
	 * fetches the cart items and handles parameters passed from external contexts,
	 * like the product type to be selected.
	 */
	@PostConstruct
	public void init() {

		cartItems = iCartService.getCartItemsWithProducts();
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		String type = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("type");

		if (id != null && type != null) {
			Long entityId = Long.parseLong(id);

			switch (type) {
			case "tourPackage":
				selectedTourPackage = tourPackageService.getTourPackageById(entityId);
				if (selectedTourPackage == null) {

				}
				break;

			case "accommodation":
				selectedAccommodation = accomodationDAO.findById(entityId);
				if (selectedAccommodation == null) {

				}
				break;

			case "restaurant":
				selectedRestaurant = restaurantDAO.findById(entityId);
				if (selectedRestaurant == null) {

				}
				break;

			case "transportation":
				selectedTransportation = transportationDAO.findById(entityId);
				if (selectedTransportation == null) {

				}
				break;

			default:

				break;
			}
		}

		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

		// Get the currently logged-in user from the session
		User user = (User) session.getAttribute("user");
		Long customerId = (Long) session.getAttribute("customerId");
		Long userId = (Long) session.getAttribute("userId");
		Long superAdminId = (Long) session.getAttribute("superAdminId");
		Long providerId = (Long) session.getAttribute("providerId");
		Long organizerId = (Long) session.getAttribute("organizerId");

		if (user != null) {
			initUserData(user.getId());
		}

	}

	/**
	 * Redirects the user to a given URL.
	 */
	public void navigateToURL() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("http://localhost:8080/triphub/views//customer_home.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds selected products to the cart based on parameters provided.
	 *
	 * @return A string redirecting to the cart page with the specified organizerId.
	 */
	public String addToCart() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		// For each product type, call the corresponding method:
		for (Map.Entry<String, String> param : params.entrySet()) {
			System.out.println("Key = " + param.getKey() + " - Value = " + param.getValue());
		}
		addTourPackageToCart(user, params.get("selectedPackageId"), params.get("quantity"));
		addAccommodationToCart(user, params.get("selectedAccommodationId"), params.get("quantity"));
		addRestaurantToCart(user, params.get("selectedRestaurantId"), params.get("quantity"));
		addTransportationToCart(user, params.get("selectedTransportationId"), params.get("quantity"));

		try {
			String organizerId = params.get("organizerId");
			FacesContext.getCurrentInstance().getExternalContext().redirect("cart.xhtml?organizerId=" + organizerId);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Adds a tour package to the user's cart that will be used for addToCart method
	 * above .
	 *
	 * @param user      The user adding the tour package.
	 * @param productId The ID of the tour package product.
	 * @param quantity  The quantity of the product to be added.
	 */
	public void addTourPackageToCart(User user, String productId, String quantity) {
		if (productId != null) {
			Long selectedPackageId = Long.parseLong(productId);
			int selectedQuantity = Integer.parseInt(quantity);
			TourPackage selectedTourPackage = tourPackageService.getTourPackageById(selectedPackageId);

			if (selectedTourPackage != null) {
				iCartService.addToCart(selectedTourPackage, user, selectedQuantity);
				dateOfPurchase = new Date();
			}
		}
	}

	/**
	 * Adds an accommodation to the user's cart that will be used for addToCart
	 * method above .
	 * 
	 * @param user
	 * @param productId
	 * @param quantity
	 */
	public void addAccommodationToCart(User user, String productId, String quantity) {
		if (productId != null) {
			Long selectedAccommodationId = Long.parseLong(productId);
			int selectedQuantity = Integer.parseInt(quantity);
			Accommodation selectedAccommodation = accomodationDAO.findById(selectedAccommodationId);

			if (selectedAccommodation != null) {
				iCartService.addToCart(selectedAccommodation, user, selectedQuantity);
				dateOfPurchase = new Date();
			}
		}
	}

	/**
	 * Adds a restaurant to the user's cart that will be used for addToCart method
	 * above.
	 * 
	 * @param user
	 * @param productId
	 * @param quantity
	 */

	public void addRestaurantToCart(User user, String productId, String quantity) {
		if (productId != null) {
			Long selectedRestaurantId = Long.parseLong(productId);
			int selectedQuantity = Integer.parseInt(quantity);

			Restaurant selectedRestaurant = restaurantDAO.findById(selectedRestaurantId);

			if (selectedRestaurant != null) {
				iCartService.addToCart(selectedRestaurant, user, selectedQuantity);
				dateOfPurchase = new Date();
			}
		}
	}

	/**
	 * Adds a transportation to the user's cart that will be used for addToCart
	 * method above.
	 * 
	 * @param user
	 * @param productId
	 * @param quantity
	 */
	public void addTransportationToCart(User user, String productId, String quantity) {
		if (productId != null) {
			Long selectedTransportationId = Long.parseLong(productId);
			int selectedQuantity = Integer.parseInt(quantity);
			Transportation selectedTransportation = transportationDAO.findById(selectedTransportationId);

			if (selectedTransportation != null) {
				iCartService.addToCart(selectedTransportation, user, selectedQuantity);
				dateOfPurchase = new Date();
			}
		}
	}

	/**
	 * Calculates the total price of all cart items.
	 *
	 * @param cartItems List of all items in the cart.
	 * @return The total price of all items in the cart.
	 */
	public void initUserData(Long userId) {
		UserViewModel temp = userService.initUser(userId);
		if (temp != null) {
			this.userViewModel = temp;
		} else {
			FacesMessageUtil.addErrorMessage("Initialization failed: User does not exist");
		}
	}

	/**
	 * Calculates the total price for a list of cart items. The method computes the
	 * sum based on the type of service each cart item represents (e.g.,
	 * TourPackage, Restaurant, Accommodation, or Transportation) and its respective
	 * quantity.
	 * 
	 * @param cartItems List of cart items for which the total price is to be
	 *                  calculated.
	 * @return The total price for the provided list of cart items.
	 *
	 *         <p>
	 *         - If a cart item is associated with a {@code TourPackage}, the price
	 *         is directly taken from the tour package's price. - If a cart item
	 *         represents a {@code Restaurant}, {@code Accommodation}, or
	 *         {@code Transportation}, the price is fetched from the respective
	 *         service's price.
	 *         </p>
	 */
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

	/**
	 * Removes an item from the cart.
	 *
	 * @param cartItemId The ID of the cart item to be removed.
	 * @param user       The user for whom the cart item is to be removed.
	 * @return A string redirecting to the cart page.
	 */
	public String removeFromCart(Long cartItemId, User user) {
		iCartService.removeFromCart(cartItemId, user);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Removed from Cart", "Item removed from your cart."));

		return "cart?faces-redirect=true";
	}

	/**
	 * Provides a list of quantity options available for a product.
	 *
	 * @return A list of select items representing the available quantities.
	 */
	public List<SelectItem> getQuantityOptions() {
		List<SelectItem> options = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			options.add(new SelectItem(i, String.valueOf(i)));
		}
		return options;
	}

	/**
	 * Updates the quantity of a specified cart item.
	 *
	 * @param cartItem The cart item whose quantity needs to be updated.
	 */
	public void updateCartItemQuantity(CartItem cartItem) {
		// Retrieve the User object from the session map
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (user != null) {
			if (cartItem.getNewQuantity() > 0) {
				cartItem.setQuantity(cartItem.getNewQuantity());
				iCartService.updateCartItem(cartItem);
			} else if (cartItem.getNewQuantity() == 0) {
				// Remove the cart item if the new quantity is set to 0
				iCartService.removeFromCart(cartItem.getId(), user);
			} else {
			}
		} else {

		}
	}

	////// Related to CheckOutBean/////

	/**
	 * Directs the user to the checkout page.
	 *
	 * @return A string redirecting to the checkout page with the relevant
	 *         parameters.
	 */
	public String goToCheckout() {
		List<String> itemIdsAsString = cartItems.stream().map(cartItem -> Long.toString(cartItem.getId()))
				.collect(Collectors.toList());
		String idsParam = String.join(",", itemIdsAsString);
		BigDecimal totalPrice = calculateTotalPrice(cartItems);

		return "CheckOut.xhtml?ids=" + idsParam + "&totalPrice=" + totalPrice + "&faces-redirect=true";
	}

	/**
	 * Navigates the user to the checkout page by setting relevant attributes in the
	 * CheckoutBean.
	 *
	 * @return A string redirecting to the checkout page.
	 */
	public String navigateToCheckout() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		CheckoutBean checkoutBean = (CheckoutBean) externalContext.getSessionMap().get("checkoutBean");

		if (checkoutBean != null) {
			checkoutBean.setCartItems(cartItems);
			checkoutBean.setTotalPrice(calculateTotalPrice(cartItems));

		}
		return "checkout.xhtml?faces-redirect=true";
	}

	/////// Getters & Setters///////

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public TourPackage getSelectedTourPackage() {
		return selectedTourPackage;
	}

	public void setSelectedTourPackage(TourPackage selectedTourPackage) {
		this.selectedTourPackage = selectedTourPackage;
	}

	public TourPackageBean getTourPackageBean() {
		return tourPackageBean;
	}

	public void setTourPackageBean(TourPackageBean tourPackageBean) {
		this.tourPackageBean = tourPackageBean;
	}

	public TourPackageService getTourPackageService() {
		return tourPackageService;
	}

	public void setTourPackageService(TourPackageService tourPackageService) {
		this.tourPackageService = tourPackageService;
	}

	public Long getSelectedPackageId() {
		return selectedPackageId;
	}

	public void setSelectedPackageId(Long selectedPackageId) {
		this.selectedPackageId = selectedPackageId;
	}

	public UserViewModel getUserViewModel() {
		return userViewModel;
	}

	public void setUserViewModel(UserViewModel userViewModel) {
		this.userViewModel = userViewModel;
	}

	public ICartService getiCartService() {
		return iCartService;
	}

	public void setiCartService(ICartService iCartService) {
		this.iCartService = iCartService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public CartItem getCartItem() {
		return currentCartItem;
	}

	public void setCartItem(CartItem cartItem) {
		this.currentCartItem = cartItem;
	}

	public int getSelectedQuantity() {
		return selectedQuantity;
	}

	public void setSelectedQuantity(int selectedQuantity) {
		this.selectedQuantity = selectedQuantity;
	}

	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public AccommodationService getAccommodationService() {
		return accommodationService;
	}

	public void setAccommodationService(AccommodationService accommodationService) {
		this.accommodationService = accommodationService;
	}

	public RestaurantService getRestaurantService() {
		return restaurantService;
	}

	public void setRestaurantService(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}

	public TransportationService getTransportationService() {
		return transportationService;
	}

	public void setTransportationService(TransportationService transportationService) {
		this.transportationService = transportationService;
	}

	public CartItem getCurrentCartItem() {
		return currentCartItem;
	}

	public void setCurrentCartItem(CartItem currentCartItem) {
		this.currentCartItem = currentCartItem;

	}

	public Accommodation getSelectedAccommodation() {
		return selectedAccommodation;
	}

	public void setSelectedAccommodation(Accommodation selectedAccommodation) {
		this.selectedAccommodation = selectedAccommodation;
	}

	public Restaurant getSelectedRestaurant() {
		return selectedRestaurant;
	}

	public void setSelectedRestaurant(Restaurant selectedRestaurant) {
		this.selectedRestaurant = selectedRestaurant;
	}

	public Transportation getSelectedTransportation() {
		return selectedTransportation;
	}

	public void setSelectedTransportation(Transportation selectedTransportation) {
		this.selectedTransportation = selectedTransportation;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public RestaurantBean getRestauBean() {
		return restaurantBean;
	}

	public void setRestauBean(RestaurantBean restauBean) {
		this.restaurantBean = restauBean;
	}

	public TransportationBean getTransBean() {
		return transportationBean;
	}

	public void setTransBean(TransportationBean transBean) {
		this.transportationBean = transBean;
	}

	public AccommodationBean getAccommodationBean() {
		return accommodationBean;
	}

	public void setAccommodationBean(AccommodationBean accommodationBean) {
		this.accommodationBean = accommodationBean;
	}

	public RestaurantBean getRestaurantBean() {
		return restaurantBean;
	}

	public void setRestaurantBean(RestaurantBean restaurantBean) {
		this.restaurantBean = restaurantBean;
	}

	public TransportationBean getTransportationBean() {
		return transportationBean;
	}

	public void setTransportationBean(TransportationBean transportationBean) {
		this.transportationBean = transportationBean;
	}

	public Long getSelectedRestaurantId() {
		return selectedRestaurantId;
	}

	public void setSelectedRestaurantId(Long selectedRestaurantId) {
		this.selectedRestaurantId = selectedRestaurantId;
	}

	public Long getSelectedAccommodationId() {
		return selectedAccommodationId;
	}

	public void setSelectedAccommodationId(Long selectedAccommodationId) {
		this.selectedAccommodationId = selectedAccommodationId;
	}

	public Long getSelectedTransportationId() {
		return selectedTransportationId;
	}

	public void setSelectedTransportationId(Long selectedTransportationId) {
		this.selectedTransportationId = selectedTransportationId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public BigDecimal getRestaurantPrice() {
		return restaurantPrice;
	}

	public void setRestaurantPrice(BigDecimal restaurantPrice) {
		this.restaurantPrice = restaurantPrice;
	}

	public String getRestaurantCurrency() {
		return restaurantCurrency;
	}

	public void setRestaurantCurrency(String restaurantCurrency) {
		this.restaurantCurrency = restaurantCurrency;
	}

	public Long getOrganizerId() {
		return organizerId;
	}

	public void setOrganizerId(Long organizerId) {
		this.organizerId = organizerId;
	}

}
