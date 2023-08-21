package triphub.managedBeans.products;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import triphub.entity.product.CartItem;
import triphub.entity.product.TourPackage;
import triphub.entity.user.User;
import triphub.helpers.FacesMessageUtil;
import triphub.services.ICartService;
import triphub.services.TourPackageService;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;

@Named("cartBean")
@RequestScoped
public class CartBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private ICartService iCartService;
	private CartItem currentCartItem;
	private List<CartItem> cartItems;
	private TourPackage selectedTourPackage;
	@Inject
	private UserService userService;
	private UserViewModel userViewModel = new UserViewModel();
	private TourPackageBean tourPackageBean;
	@Inject
	private TourPackageService tourPackageService;
	private Long selectedPackageId;
	private int selectedQuantity;
	private Date dateOfPurchase;

	@PostConstruct
	public void init() {
		cartItems = iCartService.getCartItemsWithTourPackages();
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if (id != null) {
			Long tourPackageId = Long.parseLong(id);
			selectedTourPackage = tourPackageService.getTourPackageById(tourPackageId);
			if (selectedTourPackage == null) {
				// Handle case where tour package is not found
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

//	public String addToCart() {
//	    Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//	    String selectedPackageIdParam = params.get("selectedPackageId");
//	    String selectedQuantityParam = params.get("quantity");
//
//	    if (selectedPackageIdParam != null) {
//	        Long selectedPackageId = Long.parseLong(selectedPackageIdParam);
//	        int selectedQuantity = Integer.parseInt(selectedQuantityParam);
//	        TourPackage selectedTourPackage = tourPackageService.getTourPackageById(selectedPackageId);
//
//	        if (selectedTourPackage != null) {
//	            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
//
//	            CartItem cartItem = new CartItem();
//	            cartItem.setTourPackage(selectedTourPackage);
//	            cartItem.setQuantity(selectedQuantity);
//	          
//	            //cartItems.add(cartItem);// Here I add new item to list
//	            iCartService.addToCart(selectedTourPackage, user);
//
//	            // Set the date of purchase here, after the item is successfully added to the cart
//	            dateOfPurchase = new Date();
//
//	            // Redirect to the Cart Page
//	            try {
//	                FacesContext.getCurrentInstance().getExternalContext().redirect("cart.xhtml");
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	    return null; // Return null to stay on the same page
//	}


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

	            // Set the date of purchase here, after the item is successfully added to the cart
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
	      //  int totalQuantity = cartItem.getQuantity() + cartItem.getNewQuantity();
	       // totalPrice = totalPrice.add(itemPrice.multiply(BigDecimal.valueOf(totalQuantity)));
	        totalPrice = totalPrice.add(itemPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity())));
	    }
	    
	    return totalPrice;
	}


	public void removeFromCart(Long cartItemId, User user) {

		iCartService.removeFromCart(cartItemId, user);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Removed from Cart", "Item removed from your cart."));
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
	        if (cartItem.getNewQuantity() > 0 ) { //
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

//<h:form>
//<h:selectOneMenu id="quantitySelect" value="#{cartBean.selectedQuantity}">
//    <f:selectItems value="#{cartBean.quantityOptions}" />
//</h:selectOneMenu>
//<h:commandButton value="Add to Cart" action="#{cartBean.addToCart}">
//<f:param name="selectedPackageId" value="#{param.id}" />
//<f:param name="quantity" value="#{cartBean.selectedQuantity}" />
//</h:commandButton>
//</h:form>