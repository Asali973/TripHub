package triphub.managedBeans.registration;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import triphub.entity.subscription.Customization;
import triphub.entity.subscription.Subscription;
import triphub.entity.subscription.SubscriptionType;
import triphub.entity.user.Organizer;
import triphub.helpers.FacesMessageUtil;
import triphub.helpers.ImageHelper;
import triphub.helpers.RegistrationException;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;

@Named
@RequestScoped
public class OrganizerBean implements Serializable {

    @Inject
    private UserService userService;

    private static final long serialVersionUID = 1L;

    private UserViewModel userViewModel = new UserViewModel();
    private Part logoPicture;
    private Part companyPicture;
 
    private List<Organizer> allOrganizers;

    public OrganizerBean() {
    }
   
    public void register() throws IOException{
        if (!userViewModel.getPassword().equals(userViewModel.getConfirmPassword())) {
            FacesMessageUtil.addErrorMessage("Passwords do not match!");
            return;
        }

        try {
            Organizer email = userService.findByEmailOrganizer(userViewModel.getEmail());
            if (email != null) {
                throw new RegistrationException("This email is already used");
            }

            Organizer newOrganizer = userService.createOrganizer(userViewModel);

			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.setAttribute("organizerId", newOrganizer.getId());

			initFormData(newOrganizer.getId());

			userViewModel.clear();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Organizer created successfully!", null));
			
	        // Redirection to login.xhtml
	        context.getExternalContext().redirect("/triphub/views/loginAndAccount/login.xhtml");
	        
		} catch (RegistrationException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration failed: " + e.getMessage(), null));
		}
	}
    
	public void initFormData(Long organizerId) {
		UserViewModel temp = userService.initOrganizer(organizerId);
		if (temp != null) {
			this.userViewModel = temp;
			
	        // Récupérer l'objet Customization de l'Organizer et le stocker dans userViewModel
	        Customization customization = userService.getCustomizationForOrganizer(organizerId);
	        if(customization != null) {
	            userViewModel.setPrimaryColor(customization.getPrimaryColor());
	            userViewModel.setSecondaryColor(customization.getSecondaryColor());
	            userViewModel.setPrimaryFont(customization.getPrimaryFont());
	            userViewModel.setSecondaryFont(customization.getSecondaryFont());
	        }
		} else {
			FacesMessageUtil.addErrorMessage("Initialization failed: Organizer does not exist");
		}
	}

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Long organizerId = (Long) session.getAttribute("organizerId");

		if (organizerId != null) {
			initFormData(organizerId);
		}
		
		allOrganizers = userService.getAllOrganizers();
	}

	public void updateOrganizer() {
		try {
			String logoPicName = ImageHelper.processProfilePicture(logoPicture);
			String companyPicName = ImageHelper.processProfilePicture(companyPicture);
			if (logoPicName != null) {
				userViewModel.setCompanyLogoLink(logoPicName);
			}
			if (companyPicName != null) {
				userViewModel.setCompanyPictureLink(companyPicName);
			}
			userViewModel = userService.updateOrganizerWithImage(userViewModel);
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Update failed: " + e.getMessage());
		}
	}
	
	public void deleteOrganizer() {
	    try {
	        userService.deleteOrganizer(userViewModel.getOrganizerId());
	        FacesMessageUtil.addSuccessMessage("Organizer deleted successfully!");
	        
	        FacesContext context = FacesContext.getCurrentInstance();

	        context.getExternalContext().redirect("/triphub/views/home.xhtml");
	        
	    } catch (Exception e) {
	        FacesMessageUtil.addErrorMessage("Delete failed: " + e.getMessage());
	    }
	}
	
    public void saveSubscription() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        Long organizerId = (Long) session.getAttribute("organizerId");

        Subscription chosenSubscription = new Subscription();

        if (organizerId != null && chosenSubscription != null) {
            userService.updateSubscription(organizerId, chosenSubscription);
            FacesMessageUtil.addSuccessMessage("Subscription saved successfully!");
        }
    }
    

	public void updateGraphicSettings() {
	    try {
	        userViewModel = userService.updateGraphicSettings(userViewModel);
	        FacesMessageUtil.addSuccessMessage("Graphic settings updated successfully!");
	    } catch (Exception e) {
	        FacesMessageUtil.addErrorMessage("Update failed: " + e.getMessage());
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
    
    public List<Organizer> getAllOrganizers() {
        return allOrganizers;
    }

    public void setAllOrganizers(List<Organizer> allOrganizers) {
        this.allOrganizers = allOrganizers;
    }
    
    
    
}