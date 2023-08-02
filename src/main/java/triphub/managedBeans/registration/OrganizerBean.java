package triphub.managedBeans.registration;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import triphub.helpers.FacesMessageUtil;
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

    public OrganizerBean() {
    }
   
    public void register() {
        if (!userViewModel.getPassword().equals(userViewModel.getConfirmPassword())) {
            FacesMessageUtil.addErrorMessage("Passwords do not match!");
            return;
        }
        
        try {
            userService.createOrganizer(userViewModel);
        } catch (RegistrationException e) {
            FacesMessageUtil.addErrorMessage("Registration failed: " + e.getMessage());
        }
    }
}