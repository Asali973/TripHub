package triphub.managedBeans.registration;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import triphub.dao.CustomerDAO;
import triphub.entity.util.*;
import triphub.entity.user.*;
import triphub.helpers.AuthenticationException;
import triphub.helpers.FacesMessageUtil;
import triphub.helpers.PasswordUtils;
import triphub.helpers.RegistrationException;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;

@Named
@RequestScoped
public class CustomerBean implements Serializable {

    @Inject
    private UserService userService;

    private static final long serialVersionUID = 1L;

    private UserViewModel userViewModel = new UserViewModel();
    
    // Login status
    private boolean loggedIn;
    private String confirmPassword;

    public CustomerBean() {
    }

    public void register() {
        if (!userViewModel.getPassword().equals(confirmPassword)) {
            FacesMessageUtil.addErrorMessage("Passwords do not match!");
            return;
        }
        
        try {
            userService.create(userViewModel);
        } catch (RegistrationException e) {
            FacesMessageUtil.addErrorMessage("Registration failed: " + e.getMessage());
        }
    }

    public boolean login() {
        try {
            Customer customer = userService.findByEmail(userViewModel.getEmail());
            if (customer != null
                    && PasswordUtils.getInstance().checkPassword(userViewModel.getPassword(), customer.getUser().getPassword())) {
                loggedIn = true;
                return true;
            } else {
                loggedIn = false;
                return false;
            }
        } catch (RegistrationException e) {
            loggedIn = false;
            FacesMessageUtil.addErrorMessage("Registration failed: " + e.getMessage());
            return false;
        }
    }

    public void logout() {
        loggedIn = false;
    }

    // Check if the user is logged in
    public boolean isLoggedIn() {
        return loggedIn;
    }

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserViewModel getUserViewModel() {
		return userViewModel;
	}

	public void setUserViewModel(UserViewModel userViewModel) {
		this.userViewModel = userViewModel;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

   
}
