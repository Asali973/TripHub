package triphub.managedBeans.registration;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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

/**
 * Managed bean responsible for handling user login, session management, and
 * user search operations.
 */
@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UserService userService;

	private UserViewModel userViewModel = new UserViewModel();

	private String userType;
	private User user;
	private List<User> searchResults;

	public LoginBean() {
	}

	/**
	 * Attempts to log in the user based on the provided email and password.
	 * Redirects to the appropriate home page based on the user type upon successful
	 * login.
	 *
	 * @return The navigation outcome to redirect the user.
	 */
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
				this.setUserType("customer");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("customerId",
						customer.getId());
				this.userViewModel = userService.initCustomer(customer.getId());
			}
			Organizer organizer = userService.findByUserOrganizer(user);
			if (organizer != null) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "organizer");
				this.setUserType("organizer");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("organizerId",
						organizer.getId());
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

			// return "/views/home?faces-redirect=true";

			switch (this.userType) {
			case "customer":
				return "/views/customer_home?faces-redirect=true";
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

	/**
	 * Loads user data for a specified user and redirects to the user's profile
	 * page.
	 *
	 * @return The navigation outcome to redirect to the user's profile page.
	 */
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
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("organizerId",
						organizer.getId());
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

	/**
	 * Post-construct method to initialize session data and user attributes.
	 */
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

	/**
	 * Initializes the user data for a specified user.
	 *
	 * @param userId The ID of the user.
	 */
	public void initUserData(Long userId) {
		UserViewModel temp = userService.initUser(userId);
		if (temp != null) {
			this.userViewModel = temp;
		} else {
			FacesMessageUtil.addErrorMessage("Initialization failed: User does not exist");
		}
	}

	/**
	 * Logs out the user and invalidates the session, then redirects to the home
	 * page.
	 */
	public void logout() {
		// Invalidate session
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/triphub/views/home.xhtml");
		} catch (IOException e) {
		}
	}

	/**
	 * Performs an advanced search for users based on various search parameters.
	 * Displays search results and appropriate messages.
	 */
	public void performUserSearch() {
		// Get search parameters from the ViewModel
		String firstName = userViewModel.getFirstName();
		String lastName = userViewModel.getLastName();
		String city = userViewModel.getCity();
		String street = userViewModel.getStreet();
		String zipCode = userViewModel.getZipCode();
		String country = userViewModel.getCountry();
		String email = userViewModel.getEmail();
		String phoneNum = userViewModel.getPhoneNum();
		String CCNumber = userViewModel.getCCNumber();

		// Call the service method
		List<User> searchResults = userService.advancedSearch(firstName, lastName, city, street, zipCode, country,
				email, phoneNum, CCNumber);
		setSearchResults(searchResults);
		// Process the search results
		if (searchResults.isEmpty()) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"No users found based on the given criteria.", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "User search successful.", null);
			FacesContext.getCurrentInstance().addMessage(null, message);

			for (User user : searchResults) {
				String userDetail = "User Name: " + user.getFirstName() + " " + user.getLastName() + ", Email: "
						+ user.getEmail() + ", City: " + user.getAddress().getCity() + ", Street Address: "
						+ user.getAddress().getStreet() + ", Zipcode: " + user.getAddress().getZipCode() + ", Country: "
						+ user.getAddress().getCountry() + ", Phone Number: " + user.getPhoneNum();

				FacesMessage userMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, userDetail, null);
				FacesContext.getCurrentInstance().addMessage(null, userMessage);
			}
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<User> searchResults) {
		this.searchResults = searchResults;
	}

}
