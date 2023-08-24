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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import triphub.entity.product.CartItem;
import triphub.services.ICartService;

@Named("checkoutBean")
@SessionScoped
public class CheckoutBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private CartBean cartBean;
	@Inject
	private ICartService iCartService;
	private List<CartItem> cartItems; 
    private BigDecimal totalPrice;
    
    
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
            // can retrieve the CartItems using these ids from the database or wherever you have stored them.
            cartItems =iCartService.getCartItemsWithProducts();
        }

        String totalPriceParam = params.get("totalPrice");
        if (totalPriceParam != null && !totalPriceParam.isEmpty()) {
            totalPrice = new BigDecimal(totalPriceParam);
        }
    }
    
    public String completePurchase() {
        //TODO:  Handle the checkout process here. e.g., save the order, send notifications, etc.

      
        return "purchaseSuccessful.xhtml?faces-redirect=true";
    }

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
    
    
}
