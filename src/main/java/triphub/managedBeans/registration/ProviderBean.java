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

import triphub.entity.user.Provider;
import triphub.helpers.FacesMessageUtil;
import triphub.helpers.ImageHelper;
import triphub.helpers.RegistrationException;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;

/**
 * Managed bean for handling provider functionalities such as registration,
 * update, and delete.
 */
@Named("providerBean")
@RequestScoped
public class ProviderBean implements Serializable {

	@Inject
	private UserService userService;

	private static final long serialVersionUID = 1L;

	private UserViewModel userViewModel = new UserViewModel();
	private Part logoPicture;
	private Part companyPicture;

	private List<Provider> allProviders;

	public ProviderBean() {
	}

	/**
	 * Registers a new provider after validating the data. If successful, redirects
	 * to login.
	 *
	 * @throws IOException if there's an error during redirection.
	 */
	public void register() throws IOException {
		if (!userViewModel.getPassword().equals(userViewModel.getConfirmPassword())) {
			FacesMessageUtil.addErrorMessage("Passwords do not match!");
			return;
		}

		try {
			Provider email = userService.findByEmailProvider(userViewModel.getEmail());
			if (email != null) {
				throw new RegistrationException("This email is already used");
			}

			Provider newProvider = userService.createProvider(userViewModel);

			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.setAttribute("providerId", newProvider.getId());

			initFormData(newProvider.getId());

			userViewModel.clear();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Provider created successfully!", null));

			// Redirection to login.xhtml
			context.getExternalContext().redirect("/triphub/views/loginAndAccount/login.xhtml");

		} catch (RegistrationException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration failed: " + e.getMessage(), null));
		}
	}

	/**
	 * Initializes the form data based on the given provider ID.
	 *
	 * @param providerId ID of the provider to initialize the form data with.
	 */
	public void initFormData(Long providerId) {
		UserViewModel temp = userService.initProvider(providerId);
		if (temp != null) {
			this.userViewModel = temp;
		} else {
			FacesMessageUtil.addErrorMessage("Initialization failed: Provider does not exist");
		}
	}

	/**
	 * Post-construct method to initialize necessary data.
	 */
	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Long providerId = (Long) session.getAttribute("providerId");

		if (providerId != null) {
			initFormData(providerId);
		}

		allProviders = userService.getAllProviders();
	}

	/**
	 * Updates the provider's details including images.
	 */
	public void updateProvider() {
		try {
			String logoPicName = ImageHelper.processProfilePicture(logoPicture);
			String companyPicName = ImageHelper.processProfilePicture(companyPicture);
			if (logoPicName != null) {
				userViewModel.setCompanyLogoLink(logoPicName);
			}
			if (companyPicName != null) {
				userViewModel.setCompanyPictureLink(companyPicName);
			}
			userViewModel = userService.updateProviderWithImage(userViewModel);
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Update failed: " + e.getMessage());
		}
	}

	/**
	 * Deletes the specified provider and then redirects to home.
	 */
	public void deleteProvider() {
		try {
			userService.deleteProvider(userViewModel.getProviderId());
			FacesMessageUtil.addSuccessMessage("Provider deleted successfully!");

			FacesContext context = FacesContext.getCurrentInstance();

			context.getExternalContext().redirect("/triphub/views/home.xhtml");

		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Delete failed: " + e.getMessage());
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

	public Part getLogoPicture() {
		return logoPicture;
	}

	public void setLogoPicture(Part logoPicture) {
		this.logoPicture = logoPicture;
	}

	public Part getCompanyPicture() {
		return companyPicture;
	}

	public void setCompanyPicture(Part companyPicture) {
		this.companyPicture = companyPicture;
	}

	public List<Provider> getAllProviders() {
		return allProviders;
	}

	public void setAllProviders(List<Provider> allProviders) {
		this.allProviders = allProviders;
	}

}