//package triphub.managedBeans.products;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.List;
//
//import javax.enterprise.context.SessionScoped;
//import javax.inject.Named;
//
//import triphub.entity.product.CartItem;
//
//@Named("cartSessionBean")
//@SessionScoped
//public class CartSessionBean implements Serializable {
//	private static final long serialVersionUID = 1L;
//    private List<CartItem> cartItems;
//    private BigDecimal totalPrice;
//
//    // Getter and setter methods for cartItems and totalPrice
//
//    public String setCartDataAndNavigate() {
//        cartItems = ...; // set your cartItems here
//        totalPrice = ...; // set your total price here
//        return "CheckOut.xhtml?faces-redirect=true";
//    }
//
//	public List<CartItem> getCartItems() {
//		return cartItems;
//	}
//
//	public void setCartItems(List<CartItem> cartItems) {
//		this.cartItems = cartItems;
//	}
//
//	public BigDecimal getTotalPrice() {
//		return totalPrice;
//	}
//
//	public void setTotalPrice(BigDecimal totalPrice) {
//		this.totalPrice = totalPrice;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//    
//    
//}
