package triphub.managedBeans.registration;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import triphub.entity.user.Customer;
import triphub.helpers.FacesMessageUtil;
import triphub.helpers.ImageHelper;
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

	private Part profilePicture;
	
	private List<Customer> allCustomers;

	public CustomerBean() {
	}

	public void register() throws IOException{
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

			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.setAttribute("customerId", newCustomer.getId());

			initFormData(newCustomer.getId());

			userViewModel.clear();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer created successfully!", null));

	        // Redirection to login.xhtml
	        context.getExternalContext().redirect("/triphub/views/loginAndAccount/login.xhtml");
	        
		} catch (RegistrationException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration failed: " + e.getMessage(), null));
		}
	}

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
		
	    allCustomers = userService.getAllCustomers();
	}

	public void updateCustomer() {
		try {
			String profilePicName = ImageHelper.processProfilePicture(profilePicture);
			if (profilePicName != null) {
				userViewModel.setProfilePicture(profilePicName);
			}
			userViewModel = userService.updateCustomerWithImage(userViewModel);
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Update failed: " + e.getMessage());
		}
	}
	
	public void deleteCustomer() {
	    try {
	        userService.deleteCustomer(userViewModel.getCustomerId());
	        FacesMessageUtil.addSuccessMessage("Customer deleted successfully!");
	        
	        FacesContext context = FacesContext.getCurrentInstance();

	        context.getExternalContext().redirect("/triphub/views/home.xhtml");
	        
	    } catch (Exception e) {
	        FacesMessageUtil.addErrorMessage("Delete failed: " + e.getMessage());
	    }
	}
	
	


	public Part getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(Part profilePicture) {
		this.profilePicture = profilePicture;
	}

	public UserViewModel getUserViewModel() {
		return userViewModel;
	}

	public void setUserViewModel(UserViewModel userViewModel) {
		this.userViewModel = userViewModel;
	}
	
	public List<Customer> getAllCustomers() {
	    return allCustomers;
	}

	public void setAllCustomers(List<Customer> allCustomers) {
	    this.allCustomers = allCustomers;
	}
}
