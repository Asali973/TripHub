package triphub.managedBeans.registration;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import triphub.entity.user.Customer;
import triphub.entity.user.User;
import triphub.helpers.FacesMessageUtil;
import triphub.helpers.RegistrationException;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;

@Named("customerBean")
@RequestScoped
public class CustomerBean implements Serializable {

	@Inject
	private UserService userService;

	private static final long serialVersionUID = 1L;

	private UserViewModel userViewModel = new UserViewModel();

	@Inject
	private HttpServletRequest request;

	// Picture profile
	private Part profilePicture;

	private Long customerId;

	// Login status
	private boolean loggedIn;

	public CustomerBean() {
	}

    public void register() {
        if (!userViewModel.getPassword().equals(userViewModel.getConfirmPassword())) {
            FacesMessageUtil.addErrorMessage("Passwords do not match!");
            return;
        }

        try {
            Customer email = userService.findByEmailCustomer(userViewModel.getEmail());
            if (email != null) {
                throw new RegistrationException("This email is already used");
            }

            Customer newCustomer = userService.createCustomer(userViewModel);

            // Get the session and store the customer ID
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            session.setAttribute("customerId", newCustomer.getId());
            
            // Get the customer ID after creation
            Long customerId = newCustomer.getId();

            // Initialize the form data with the newly created customer's ID
            initFormData(customerId);

            // Clear the form fields to prepare for a new customer creation
            userViewModel.setFirstName("");
            userViewModel.setLastName("");
            userViewModel.setEmail("");
            userViewModel.setPhoneNum("");
            userViewModel.setPassword("");
            userViewModel.setConfirmPassword("");
            userViewModel.setNum("");
            userViewModel.setStreet("");
            userViewModel.setCity("");
            userViewModel.setState("");
            userViewModel.setCountry("");
            userViewModel.setZipCode("");
            userViewModel.setCCNumber("");
            userViewModel.setExpirationDate(null);
            userViewModel.setProfilePicture(null);

            // Show a success message
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer created successfully!", null));
        } catch (RegistrationException e) {
            // Show an error message
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration failed: " + e.getMessage(), null));
        }
    }

//	public void register() {
//		if (!userViewModel.getPassword().equals(userViewModel.getConfirmPassword())) {
//			FacesMessageUtil.addErrorMessage("Passwords do not match!");
//			return;
//		}
//
//		try {
//			userService.createCustomer(userViewModel);
//		} catch (RegistrationException e) {
//			FacesMessageUtil.addErrorMessage("Registration failed: " + e.getMessage());
//		}
//	}

	public void initFormData(Long customerId) {
		UserViewModel temp = userService.initCustomer(customerId);
		if (temp != null) {
			this.userViewModel = temp;
		} else {
			FacesMessageUtil.addErrorMessage("Initialization failed: Customer does not exist");
		}
	}
	
	@PostConstruct
	public void init() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
	    Long customerId = (Long) session.getAttribute("customerId");

	    if (customerId != null) {
	        initFormData(customerId);
	    }
	}



	public void updateCustomer() {
	    try {
	        if (profilePicture != null) {
	            // Convertir l'InputStream en byte[]
	            byte[] bytes = IOUtils.toByteArray(profilePicture.getInputStream());

	            // Créez le chemin du fichier
	            String filename = profilePicture.getSubmittedFileName();
	            String path = "/Users/brendan/EnvJEE/Tools/wildfly-18.0.0.Final/data/triphub/images" + "/" + filename;

	            // le byte[] dans un nouveau fichier
	            try (FileOutputStream fos = new FileOutputStream(path)) {
	                fos.write(bytes);
	            }

	            // Stocke le chemin du fichier dans le UserViewModel
	            userViewModel.setProfilePicture(filename); // Notez qu'on stocke uniquement le nom du fichier ici, pas le chemin complet.
	        }
	        userViewModel = userService.updateCustomerWithImage(userViewModel);
	    } catch (Exception e) {
	        FacesMessageUtil.addErrorMessage("Update failed: " + e.getMessage());
	    }
	}

//    @PostConstruct
//    public void init() {
//        Principal principal = request.getUserPrincipal();
//        if (principal != null) {
//            try {
//                // Assuming the principal's name is the user's ID.
//                Long userId = Long.parseLong(principal.getName());
//                UserViewModel temp = userService.initCustomer(userId, customerId);
//                if (temp != null) {
//                    this.userViewModel = temp;
//                    this.customerId = userViewModel.getCustomerId(); // Set the customerId property from the UserViewModel
//                } else {
//                    FacesMessageUtil.addErrorMessage("Initialization failed: User does not exist");
//                }
//            } catch (NumberFormatException e) {
//                FacesMessageUtil.addErrorMessage("Initialization failed: Invalid user ID");
//            }
//        }
//    }

//  public void init() {
//	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//	Long idUserConnecte = (Long) session. getAttribute("connectedUserId");
//}
//    public boolean login() {
//        try {
//            Customer customer = userService.findByEmailCustomer(userViewModel.getEmail());
//            if (customer != null
//                    && PasswordUtils.getInstance().checkPassword(userViewModel.getPassword(), customer.getUser().getPassword())) {
//                loggedIn = true;
//                return true;
//            } else {
//                loggedIn = false;
//                return false;
//            }
//        } catch (RegistrationException e) {
//            loggedIn = false;
//            FacesMessageUtil.addErrorMessage("Registration failed: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public void logout() {
//        loggedIn = false;
//    }

	public Part getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(Part profilePicture) {
		this.profilePicture = profilePicture;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

}
