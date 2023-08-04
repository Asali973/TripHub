package triphub.managedBeans.registration;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpSession;

import triphub.entity.user.*;
import triphub.helpers.FacesMessageUtil;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;
import org.mindrot.jbcrypt.BCrypt;

@Named
@SessionScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UserService userService;

	private UserViewModel userViewModel = new UserViewModel();

	public LoginBean() {
	}

	public String login() {
		User user = userService.findByEmailUser(userViewModel.getEmail());

		if (user != null && BCrypt.checkpw(userViewModel.getPassword(), user.getPassword())) {
			// Add user object to the session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userId", user.getId());

			// Initialize the UserViewModel
			initUserData(user.getId());

			Customer customer = userService.findByUserCustomer(user);
			if (customer != null) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "customer");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("customerId", customer.getId());
				this.userViewModel = userService.initCustomer(customer.getId());
			}
//			Organizer organizer = userService.findByUserOrganizer(user);
//			if (organizer != null) {
//				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "organizer");
//				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("oranizerId", organizer.getId());
//				this.userViewModel = userService.initOrganizer(organizer.getId());
//			}
//
//			Provider provider = userService.findByUserProvider(user);
//			if (provider != null) {
//				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "provider");
//				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("providerId", provider.getId());
//				this.userViewModel = userService.initProvider(provider.getId());
//			}
//
//			SuperAdmin superAdmin = userService.findByUserSuperAdmin(user);
//			if (superAdmin != null) {
//				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "superAdmin");
//				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("customerId", superAdmin.getId());
//				this.userViewModel = userService.initSuperAdmin(superAdmin.getId());
//			}
			
			// Save the UserViewModel in the session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userViewModel",
					this.userViewModel);

			return "/views/home?faces-redirect=true";
		}
		return "login";
	}

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		User user = (User) session.getAttribute("user");
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
