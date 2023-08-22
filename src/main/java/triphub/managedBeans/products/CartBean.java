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
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import triphub.entity.product.CartItem;
import triphub.entity.product.TourPackage;
import triphub.entity.service.Accommodation;
import triphub.entity.service.Restaurant;
import triphub.entity.service.Transportation;
import triphub.entity.user.User;
import triphub.helpers.FacesMessageUtil;
import triphub.services.AccommodationService;
import triphub.services.ICartService;
import triphub.services.RestaurantService;
import triphub.services.TourPackageService;
import triphub.services.TransportationService;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;

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
	RestaurantService  restaurantService;	
	@Inject
	TransportationService transportationService;
	@Inject
	private TourPackageBean tourPackageBean;
	@Inject 
	private AccommodationBean accoBean;
	
	private UserViewModel userViewModel = new UserViewModel();
	
	private CartItem currentCartItem;
	private List<CartItem> cartItems;
	private TourPackage selectedTourPackage;
	private Accommodation selectedAccommodation;
	private Restaurant selectedRestaurant;
	private Transportation selectedTransportation;
	private Long selectedPackageId;
	private int selectedQuantity;
	private Date dateOfPurchase;

	@PostConstruct
	public void init() {
		//cartItems = iCartService.getCartItemsWithTourPackages();
		cartItems = iCartService.getCartItemsWithProducts();
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		String type = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("type");

		if (id != null && type != null) {
		    Long entityId = Long.parseLong(id);

		    switch (type) {
		        case "tourPackage":
		            selectedTourPackage = tourPackageService.getTourPackageById(entityId);
		            if (selectedTourPackage == null) {
		                // Handle case where tour package is not found
		            }
		            break;

		        case "accommodation":
		            selectedAccommodation = accommodationService.findById(entityId);
		            if (selectedAccommodation == null) {
		                // Handle case where accommodation is not found
		            }
		            break;

		        case "restaurant":
		            selectedRestaurant = restaurantService.findById(entityId);
		            if (selectedRestaurant == null) {
		                // Handle case where restaurant is not found
		            }
		            break;

		        case "transportation":
		            selectedTransportation = transportationService.findById(entityId);
		            if (selectedTransportation == null) {
		                // Handle case where transportation is not found
		            }
		            break;

		        default:
		            // Handle case where the type is unknown or not provided
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

	public String addToCart() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String selectedPackageIdParam = params.get("selectedPackageId");
		String selectedQuantityParam = params.get("quantity");

		if (selectedPackageIdParam != null) {
			Long selectedPackageId = Long.parseLong(selectedPackageIdParam);
			int selectedQuantity = Integer.parseInt(selectedQuantityParam);
			TourPackage selectedTourPackage = tourPackageService.getTourPackageById(selectedPackageId);

			if (selectedTourPackage != null) {
				User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

				iCartService.addToCart(selectedTourPackage, user, selectedQuantity); // Use the updated addToCart method

				// Set the date of purchase here, after the item is successfully added to the
				// cart
				dateOfPurchase = new Date();

				// Redirect to the Cart Page
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("cart.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null; // Return null to stay on the same page
	}

	public void initUserData(Long userId) {
		UserViewModel temp = userService.initUser(userId);
		if (temp != null) {
			this.userViewModel = temp;
		} else {
			FacesMessageUtil.addErrorMessage("Initialization failed: User does not exist");
		}
	}

	public BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
		BigDecimal totalPrice = BigDecimal.ZERO;

		for (CartItem cartItem : cartItems) {
			BigDecimal itemPrice = BigDecimal.ZERO;

			if (cartItem.getTourPackage() != null) {
				itemPrice = cartItem.getTourPackage().getPrice().getAmount();
			} else if (cartItem.getService() != null) {
				itemPrice = cartItem.getService().getPrice().getAmount();
			}

			// Use the sum of existing quantity and new quantity for the calculation
			// int totalQuantity = cartItem.getQuantity() + cartItem.getNewQuantity();
			// totalPrice =
			// totalPrice.add(itemPrice.multiply(BigDecimal.valueOf(totalQuantity)));
			totalPrice = totalPrice.add(itemPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity())));
		}

		return totalPrice;
	}

	public String removeFromCart(Long cartItemId, User user) {
	    iCartService.removeFromCart(cartItemId, user);
	    FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_INFO, "Removed from Cart", "Item removed from your cart."));

	    return "cart?faces-redirect=true";
	}


	public List<SelectItem> getQuantityOptions() {
		List<SelectItem> options = new ArrayList<>();
		// adjust the range based on requirements
		for (int i = 1; i <= 10; i++) {
			options.add(new SelectItem(i, String.valueOf(i)));
		}
		return options;
	}

	public void updateCartItemQuantity(CartItem cartItem) {
		// Retrieve the User object from the session map
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
//&& cartItem.getNewQuantity() != cartItem.getQuantity()
		if (user != null) {
			if (cartItem.getNewQuantity() > 0) { //
				cartItem.setQuantity(cartItem.getNewQuantity());
				iCartService.updateCartItem(cartItem);
			} else if (cartItem.getNewQuantity() == 0) {
				// Remove the cart item if the new quantity is set to 0
				iCartService.removeFromCart(cartItem.getId(), user);
			} else {
				// Handle other cases or invalid input as needed
			}
		} else {
			// Handle the case when the user is not available in the session
		}
	}

	////// Related to CheckOutBean/////

	public String goToCheckout() {
		List<String> itemIdsAsString = cartItems.stream().map(cartItem -> Long.toString(cartItem.getId()))
				.collect(Collectors.toList());
		String idsParam = String.join(",", itemIdsAsString);
		BigDecimal totalPrice = calculateTotalPrice(cartItems);

		return "CheckOut.xhtml?ids=" + idsParam + "&totalPrice=" + totalPrice + "&faces-redirect=true";
	}

	public String navigateToCheckout() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		CheckoutBean checkoutBean = (CheckoutBean) externalContext.getSessionMap().get("checkoutBean");

		if (checkoutBean != null) {
			checkoutBean.setCartItems(cartItems);
			checkoutBean.setTotalPrice(calculateTotalPrice(cartItems));
			
		}
		return "checkout.xhtml?faces-redirect=true"; // Navigate to checkout page
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

}

//public String addToCart() {
//Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//String selectedPackageIdParam = params.get("selectedPackageId");
//String selectedQuantityParam = params.get("quantity");
//
//if (selectedPackageIdParam != null) {
//    Long selectedPackageId = Long.parseLong(selectedPackageIdParam);
//    int selectedQuantity = Integer.parseInt(selectedQuantityParam);
//    TourPackage selectedTourPackage = tourPackageService.getTourPackageById(selectedPackageId);
//
//    if (selectedTourPackage != null) {
//        User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
//
//        CartItem cartItem = new CartItem();
//        cartItem.setTourPackage(selectedTourPackage);
//        cartItem.setQuantity(selectedQuantity);
//      
//        //cartItems.add(cartItem);// Here I add new item to list
//        iCartService.addToCart(selectedTourPackage, user);
//
//        // Set the date of purchase here, after the item is successfully added to the cart
//        dateOfPurchase = new Date();
//
//        // Redirect to the Cart Page
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("cart.xhtml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//return null; // Return null to stay on the same page
//}

//<h:form>
//<h:selectOneMenu id="quantitySelect" value="#{cartBean.selectedQuantity}">
//    <f:selectItems value="#{cartBean.quantityOptions}" />
//</h:selectOneMenu>
//<h:commandButton value="Add to Cart" action="#{cartBean.addToCart}">
//<f:param name="selectedPackageId" value="#{param.id}" />
//<f:param name="quantity" value="#{cartBean.selectedQuantity}" />
//</h:commandButton>
//</h:form>