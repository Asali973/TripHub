package triphub.managedBeans.registration;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpSession;

import triphub.entity.user.*;
import triphub.helpers.FacesMessageUtil;
import triphub.managedBeans.products.CartBean;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;
import org.mindrot.jbcrypt.BCrypt;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UserService userService;
	@Inject
	private CartBean cartBean;
	private UserViewModel userViewModel = new UserViewModel();
	
	private String userType;
	
	private User user = new User();

	public User getUser() {
		return user;
	}
	public LoginBean() {
	}
	public boolean isLoggedIn() {
	    return userViewModel != null;
	}
	public String redirectToLogin() {
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("intendedAction", "viewCart");
	    return "/CustomerRegistration.xhtml?faces-redirect=true";
	}

	public String login() {
		User user = userService.findByEmailUser(userViewModel.getEmail());
		 String intendedAction = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("intendedAction");
		if (user != null && BCrypt.checkpw(userViewModel.getPassword(), user.getPassword())) {
			// Add user object to the session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userId", user.getId());

			// Initialize the UserViewModel
			initUserData(user.getId());

			Customer customer = userService.findByUserCustomer(user);
			if (customer != null) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "customer");
				this.setUserType("customer");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("customerId",
						customer.getId());
				this.userViewModel = userService.initCustomer(customer.getId());
			}
			Organizer organizer = userService.findByUserOrganizer(user);
			if (organizer != null) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "organizer");
				this.setUserType("organizer");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("organizerId", organizer.getId());
				this.userViewModel = userService.initOrganizer(organizer.getId());
			}

			Provider provider = userService.findByUserProvider(user);
			if (provider != null) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "provider");
				this.setUserType("provider");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("providerId",
						provider.getId());
				this.userViewModel = userService.initProvider(provider.getId());
			}

			SuperAdmin superAdmin = userService.findByUserSuperAdmin(user);
			if (superAdmin != null) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "superAdmin");
				this.setUserType("superAdmin");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("superAdminId",
						superAdmin.getId());
				this.userViewModel = userService.initSuperAdmin(superAdmin.getId());
			}

			// Save the UserViewModel in the session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userViewModel",
					this.userViewModel);
			
			if ("viewCart".equals(intendedAction)) {
			    cartBean.addToCart(); // Use CartBean method because this relies on UI parameters
			    return "/cart.xhtml?faces-redirect=true";
			}
				
			
			  switch (this.userType) {
	            case "customer":
	                return "/views/organizer_session/homeForWebsite?faces-redirect=true";
	            case "organizer":
	                return "/views/organizer_home?faces-redirect=true";
	            case "provider":
	                return "/views/provider_home?faces-redirect=true";
	            case "superAdmin":
	                return "/views/superadmin_home?faces-redirect=true";
	        }
	    }
		
		return "login";
	}
	
	public String loadUserData() {
	    Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	    String userIdParam = params.get("selectedUserId");
	    
	    System.out.println("selectedUserId: " + userIdParam);
	    Long userId = null;

	    if (userIdParam != null) {
	        try {
	            userId = Long.parseLong(userIdParam);
	        } catch (NumberFormatException e) {
	        }
	    }

	    if (userId != null) {
	        User user = userService.findByUserId(userId);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userId", user.getId());
	        System.out.println("User retrieved: " + user);

			// Initialize the UserViewModel
			initUserData(user.getId());

			Customer customer = userService.findByUserCustomer(user);
			if (customer != null) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "customer");
				this.setUserType("customer");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("customerId",
						customer.getId());
				this.userViewModel = userService.initCustomer(customer.getId());
			}
			Organizer organizer = userService.findByUserOrganizer(user);
			if (organizer != null) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "organizer");
				this.setUserType("organizer");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("organizerId", organizer.getId());
				this.userViewModel = userService.initOrganizer(organizer.getId());
			}

			Provider provider = userService.findByUserProvider(user);
			if (provider != null) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "provider");
				this.setUserType("provider");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("providerId",
						provider.getId());
				this.userViewModel = userService.initProvider(provider.getId());
			}

			SuperAdmin superAdmin = userService.findByUserSuperAdmin(user);
			if (superAdmin != null) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "superAdmin");
				this.setUserType("superAdmin");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("superAdminId",
						superAdmin.getId());
				this.userViewModel = userService.initSuperAdmin(superAdmin.getId());
			}

			// Save the UserViewModel in the session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userViewModel",
					this.userViewModel);

			return "/views/loginAndAccount/ProfileUser.xhtml?faces-redirect=true";
		}
		return null;
	}
	





	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		user = (User) session.getAttribute("user");
		Long customerId = (Long) session.getAttribute("customerId");
		Long userId = (Long) session.getAttribute("userId");
		Long superAdminId = (Long) session.getAttribute("superAdminId");
		Long providerId = (Long) session.getAttribute("providerId");
		Long organizerrId = (Long) session.getAttribute("providerId");

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

	public void logout() {
	    // Invalidate session
	    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	    try {
	        FacesContext.getCurrentInstance().getExternalContext().redirect("/triphub/views/home.xhtml");
	    } catch (IOException e) {
	    }
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
