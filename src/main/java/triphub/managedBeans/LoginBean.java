package triphub.managedBeans;


import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import triphub.dao.*;
import triphub.entity.user.*;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;

import javax.persistence.*;

import org.mindrot.jbcrypt.BCrypt;

@Named
@RequestScoped
public class LoginBean {
	
    @Inject
    private UserService userService;

    private static final long serialVersionUID = 1L;

    private UserViewModel userViewModel = new UserViewModel();
    //private User user;

    public LoginBean() {
    }

    public String login() {

        User user = userService.findByEmailUser(userViewModel.getEmail());
 
        if(user != null && BCrypt.checkpw(userViewModel.getPassword(), user.getPassword())) {
            //this.user = user;
            // Add user object to the session
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
            
            Customer customer = userService.findByUserCustomer(user);
            if (customer != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "customer");
                return "home?faces-redirect=true";
            }
            
            Organizer organizer = userService.findByUserOrganizer(user);
            if (organizer != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "organizer");
                return "home?faces-redirect=true";
            }

            Provider provider = userService.findByUserProvider(user);
            if (provider != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "provider");
                return "home?faces-redirect=true";
            }

            SuperAdmin superAdmin = userService.findByUserSuperAdmin(user);
            if (superAdmin != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "superAdmin");
                return "home?faces-redirect=true";
            }

        }
        return "login"; 
    }
    
    public String logout() {
        // Invalidate session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true"; 
    }



    
    
}

