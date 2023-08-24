package triphub.managedBeans.products;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import triphub.entity.user.User;

@Named
@SessionScoped
public class UserSessionBean implements Serializable {
	private static final long serialVersionUID = 1L;
    private User user;
    
    public boolean isLoggedIn() {
        return user != null;
    }
    
    public String redirectToLogin() {
        // Store intended action or page to return to
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("intendedAction", "viewCart");
        return "register";
    }

    public String afterLoginRedirect() {
        String intendedAction = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("intendedAction");
        if ("viewCart".equals(intendedAction)) {
            return "/cart.xhtml?faces-redirect=true";
        }
        // Handle other cases or default to some other page
        return "/homeForWebsite.xhtml?faces-redirect=true";
    }

 //after successful authentication
    public String doLogin() {
        // ... (authentication logic)

        String intendedAction = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("intendedAction");
        if ("viewCart".equals(intendedAction)) {
            return "/cart.xhtml?faces-redirect=true";
        }
        
        // default redirect
        return "/home.xhtml?faces-redirect=true";
    }

}
//<h:commandButton value="Add to Cart" action="#{cartBean.addToCart}">
//<f:param name="selectedPackageId" value="#{param.id}" />
//<f:param name="quantity" value="#{cartBean.selectedQuantity}" />
//</h:commandButton>

//<!-- Check if user is logged in before proceeding to checkout -->
//<h:commandButton value="Checkout" action="#{userSessionBean.isLoggedIn() ? 'checkout' : userSessionBean.redirectToLogin()}" />
