package triphub.managedBeans.products;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import triphub.entity.product.CartItem;
import triphub.services.ICartService;

/**
 * Represents the JSF managed bean for handling checkout processes in the triphub application.
 */
@Named("checkoutBean")
@RequestScoped
public class CheckoutBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private CartBean cartBean;
	@Inject
	private ICartService iCartService;
	private List<CartItem> cartItems; 
    private BigDecimal totalPrice;
    private String organizerId;
    
    /**
     * Initializes the checkout bean with data retrieved from request parameters.
     */
    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> params = externalContext.getRequestParameterMap();

        String idsParam = params.get("ids");
        if (idsParam != null && !idsParam.isEmpty()) {
            String[] idsArray = idsParam.split(",");
            List<Long> ids = new ArrayList<>();
            for (String id : idsArray) {
                ids.add(Long.parseLong(id));
            }
            cartItems =iCartService.getCartItemsWithProducts();
        }

        String totalPriceParam = params.get("totalPrice");
        if (totalPriceParam != null && !totalPriceParam.isEmpty()) {
            totalPrice = new BigDecimal(totalPriceParam);
        }
        
        organizerId = params.get("organizerId");
        
        System.out.println("Inside CheckoutBean init");
        System.out.println("organizerId from params: " + params.get("organizerId"));


        
    }
    
    /**
     * Completes the purchase and redirects to the success page.
     * @return the name of the success page.
     */
    public String completePurchase() {      
        return "purchaseSuccessful.xhtml?faces-redirect=true";
    }

    /**
     * Retrieves the full name of the user associated with the first cart item.
     * @return full name of the user or null if cart items are empty.
     */
    public String getFullName() {
        if (cartItems != null && !cartItems.isEmpty()) {
            return cartItems.get(0).getUser().getFirstName() + " " + cartItems.get(0).getUser().getLastName();
        }
        return null; 
    }


	public List<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getOrganizerId() {
	    return organizerId;
	}

	public void setOrganizerId(String organizerId) {
	    this.organizerId = organizerId;
	}

    
    
}
