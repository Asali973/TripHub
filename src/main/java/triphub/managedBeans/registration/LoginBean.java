package triphub.managedBeans.registration;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import triphub.entity.user.*;
import triphub.helpers.FacesMessageUtil;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;
import org.mindrot.jbcrypt.BCrypt;

@Named
@RequestScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

	@Inject
	private UserService userService;
	
//    @Inject
//    private Conversation conversation;



	private UserViewModel userViewModel = new UserViewModel();
	// private User user;

	public LoginBean() {
	}

    public String login() {
//        if (!conversation.isTransient()) {
//            conversation.end();
//        }
//        conversation.begin();

		User user = userService.findByEmailUser(userViewModel.getEmail());

        if(user != null && BCrypt.checkpw(userViewModel.getPassword(), user.getPassword())) {
            // Add user object to the session
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
            
            // Initialize the UserViewModel
            initUserData(user.getId());
        
            Customer customer = userService.findByUserCustomer(user);
            if (customer != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "customer");

                // Initialize the UserViewModel
                this.userViewModel = userService.initCustomer(customer.getId());

                // Save the UserViewModel in the session
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userViewModel", this.userViewModel);

                return "/views/home?faces-redirect=true";
            }

			Organizer organizer = userService.findByUserOrganizer(user);
			if (organizer != null) {
				// Ajouter l'objet organizer à la session
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "organizer");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userSpecific", organizer);
				return "/views/home?faces-redirect=true";
			}

			Provider provider = userService.findByUserProvider(user);
			if (provider != null) {
				// Ajouter l'objet provider à la session
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "provider");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userSpecific", provider);
				return "/views/home?faces-redirect=true";
			}

			SuperAdmin superAdmin = userService.findByUserSuperAdmin(user);
			if (superAdmin != null) {
				// Ajouter l'objet superAdmin à la session
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "superAdmin");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userSpecific", superAdmin);
				return "/views/home?faces-redirect=true";
			}

		}
		return "login";
	}
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        User user = (User) session.getAttribute("user");

        if (user != null) {
            initUserData(user.getId());
        }
    }
    
    public void initUserData(Long userId) {
        UserViewModel temp = userService.initUser(userId);
        if (temp != null) {
            this.userViewModel = temp;
        } else {
            FacesMessageUtil.addErrorMessage("Initialization failed: User does not exist");
        }
    }


	public String logout() {
		// Invalidate session
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login?faces-redirect=true";
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

}
