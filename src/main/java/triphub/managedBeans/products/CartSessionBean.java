package triphub.managedBeans.products;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import triphub.entity.product.CartSession;
import triphub.entity.product.TourPackage;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.entity.user.User;
import triphub.managedBeans.registration.LoginBean;
import triphub.services.ICartService;
import triphub.services.TourPackageService;

@Named("cartSessionBean")
@SessionScoped
public class CartSessionBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ICartService iCartService;
	private List<CartSession> sessionCartItems = new ArrayList<>();

	@Inject
	private TourPackageService tourPackageService;

	@PostConstruct
	public void init() {
		sessionCartItems = new ArrayList<>();
	}

	public String handleAddToCart(String itemType, int quantity) {
		LoginBean loginBean = FacesContext.getCurrentInstance().getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{loginBean}", LoginBean.class);

		if (loginBean.isLoggedIn()) {
			User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			addToSessionCart(itemType, quantity);
			return "/cart.xhtml?faces-redirect=true";
		} else {
			addToSessionCart(itemType, quantity);
			return loginBean.redirectToLogin();
		}
	}

	public void addToSessionCart(String itemType, int quantity) {
		BigDecimal unitPrice = BigDecimal.ZERO;
		CartSession existingSessionCartItem = null;
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		if (itemType.equals("TourPackage")) {
			Long tourPackageId = Long.parseLong((String) params.get("selectedPackageId"));
			TourPackage tourPackage = tourPackageService.getTourPackageById(tourPackageId);
			existingSessionCartItem = findExistingItemByTourPackage(tourPackage);
			unitPrice = tourPackage.getPrice().getAmount();
			if (existingSessionCartItem != null) {
				// If the item is already in the cart, update its quantity based on cart page
				// value
				updateSessionCartItem(existingSessionCartItem, unitPrice, quantity);
			} else {
				createNewSessionCartItem(tourPackage, unitPrice, 1);
			}
		}
//	        else if (itemType.equals("TourPackage")) {
//	        Accommodation accommodation = (Accommodation) cartItemObject;
//	        existingSessionCartItem = findExistingItemByAccommodation(accommodation);
//	        unitPrice = accommodation.getService().getPrice().getAmount();
//	    } else if (itemType.equals("TourPackage")) {
//	        Restaurant restaurant = (Restaurant) cartItemObject;
//	        existingSessionCartItem = findExistingItemByRestaurant(restaurant);
//	        unitPrice = restaurant.getService().getPrice().getAmount();
//	    } else if (itemType.equals("TourPackage")) {
//	        Transportation transportation = (Transportation) cartItemObject;
//	        existingSessionCartItem = findExistingItemByTransportation(transportation);
//	        unitPrice = transportation.getService().getPrice().getAmount();
//	        }

		if (existingSessionCartItem != null) {
			// If the item is already in the cart, update its quantity based on cart page
			// value
//			updateSessionCartItem(existingSessionCartItem, unitPrice, quantity);
		} else {
			// If the item is new to the cart, create a new session cart item with an
			// initial quantity of 1
//	        if (itemType.equals("TourPackage")) {
//	            createNewSessionCartItem(tourPackage, unitPrice, 1);
//	        } else if(cartItemObject instanceof Accommodation) {
//	            createNewSessionCartItem((Accommodation) cartItemObject, unitPrice,1);
//	        } else if(cartItemObject instanceof Restaurant) {
//	            createNewSessionCartItem((Restaurant) cartItemObject, unitPrice,1);
//	        } else if(cartItemObject instanceof Transportation) {
//	            createNewSessionCartItem((Transportation) cartItemObject, unitPrice,1);
//	        }
		}
	}

	private void updateSessionCartItem(CartSession existingSessionCartItem, BigDecimal unitPrice, int quantity) {
		int newQuantity = existingSessionCartItem.getQuantity() + quantity;
		BigDecimal newTotalPrice = unitPrice.multiply(BigDecimal.valueOf(newQuantity));

		existingSessionCartItem.setQuantity(newQuantity);
		existingSessionCartItem.setTotalPrice(newTotalPrice);
	}

	private void setCommonSessionCartItemFields(CartSession cartItem, BigDecimal unitPrice) {
		cartItem.setQuantity(1); // Set initial quantity to 1
		cartItem.setTotalPrice(unitPrice);
		cartItem.setDate(new Date());
	}

	private void createNewSessionCartItem(TourPackage tourPackage, BigDecimal unitPrice, int quantity) {
		CartSession newSessionCartItem = new CartSession();
		newSessionCartItem.setTourPackage(tourPackage);
		setCommonSessionCartItemFields(newSessionCartItem, unitPrice);
		sessionCartItems.add(newSessionCartItem);
	}

	private void createNewSessionCartItem(Accommodation accommodation, BigDecimal unitPrice, int quantity) {
		CartSession newSessionCartItem = new CartSession();
		newSessionCartItem.setAccommodation(accommodation);
		setCommonSessionCartItemFields(newSessionCartItem, unitPrice);
		sessionCartItems.add(newSessionCartItem);
	}

	private void createNewSessionCartItem(Restaurant restaurant, BigDecimal unitPrice, int quantity) {
		CartSession newSessionCartItem = new CartSession();
		newSessionCartItem.setRestaurant(restaurant);
		setCommonSessionCartItemFields(newSessionCartItem, unitPrice);
		sessionCartItems.add(newSessionCartItem);
	}

	private void createNewSessionCartItem(Transportation transportation, BigDecimal unitPrice, int quantity) {
		CartSession newSessionCartItem = new CartSession();
		newSessionCartItem.setTransportation(transportation);
		setCommonSessionCartItemFields(newSessionCartItem, unitPrice);
		sessionCartItems.add(newSessionCartItem);
	}

	private CartSession findExistingItemByTourPackage(TourPackage tourPackage) {
		for (CartSession sessionCartItem : sessionCartItems) {
			if (sessionCartItem.getTourPackage().getId() == tourPackage.getId()) {
				return sessionCartItem;
			}
		}
		return null;
	}

	private CartSession findExistingItemByAccommodation(Accommodation accommodation) {
		for (CartSession sessionCartItem : sessionCartItems) {
			if (sessionCartItem.getAccommodation().equals(accommodation)) {
				return sessionCartItem;
			}
		}
		return null;
	}

	private CartSession findExistingItemByRestaurant(Restaurant restaurant) {
		for (CartSession sessionCartItem : sessionCartItems) {
			if (sessionCartItem.getRestaurant().equals(restaurant)) {
				return sessionCartItem;
			}
		}
		return null;
	}

	private CartSession findExistingItemByTransportation(Transportation transportation) {
		for (CartSession sessionCartItem : sessionCartItems) {
			if (sessionCartItem.getTransportation().equals(transportation)) {
				return sessionCartItem;
			}
		}
		return null;

	}

	public void removeFromSessionCart(CartSession sessionCartItem) {
		sessionCartItems.remove(sessionCartItem);
	}

	private CartSession findExistingItem(CartSession sessionCartItem) {
		for (CartSession existingItem : sessionCartItems) {
			if (existingItem.equals(sessionCartItem)) {
				return existingItem;
			}
		}
		return null;
	}

	public BigDecimal calculateTotalPriceForSession() {
		BigDecimal total = BigDecimal.ZERO;
		for (CartSession cartSession : sessionCartItems) {
			total = total.add(cartSession.getTotalPrice());
		}
		return total;
	}

	public List<CartSession> getSessionCartItems() {
		return sessionCartItems;
	}

	public void setSessionCartItems(List<CartSession> sessionCartItems) {
		this.sessionCartItems = sessionCartItems;
	}
}

//<td><h:outputText value="#{item.quantity}" /></td>
//<td><h:inputText value="#{item.newQuantity}" /> <h:commandButton
//		value="Add" action="#{cartBean.updateCartItemQuantity(item)}" />
//</td>
//<td><h:commandButton value="Remove"
//		action="#{cartBean.removeFromCart(item.id, item.user)}"
//		immediate="true" /></td>