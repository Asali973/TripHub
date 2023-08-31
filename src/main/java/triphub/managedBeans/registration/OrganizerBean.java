package triphub.managedBeans.registration;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import triphub.dao.customization.DatabaseLayoutDAO;
import triphub.entity.subscription.Customization;
import triphub.entity.subscription.Layout;
import triphub.entity.subscription.Subscription;
import triphub.entity.subscription.SubscriptionType;
import triphub.entity.user.Organizer;
import triphub.helpers.FacesMessageUtil;
import triphub.helpers.ImageHelper;
import triphub.helpers.RegistrationException;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;

/**
 * Managed bean for handling organizer functionalities like registration,
 * update, delete, etc.
 */
@Named("organizerBean")
@RequestScoped
public class OrganizerBean implements Serializable {

	@Inject
	private UserService userService;

	@Inject
	private DatabaseLayoutDAO layoutDAO;

	private static final long serialVersionUID = 1L;

	private UserViewModel userViewModel = new UserViewModel();
	private Part logoPicture;
	private Part companyPicture;

	private List<Layout> availableLayouts;

	private List<Organizer> allOrganizers;
	private String searchCompanyName;
	private String searchCountry;

	private List<Organizer> searchResults;

	private Long organizerId;

	public OrganizerBean() {
	}

	/**
	 * Searches for organizers based on company name and country.
	 */
	public void searchOrganizers() {
		searchResults = userService.findOrganizerByCompanyOrCountry(searchCompanyName, searchCountry);
	}

	/**
	 * Register a new organizer.
	 */
	public void register() throws IOException {
		if (!userViewModel.getPassword().equals(userViewModel.getConfirmPassword())) {
			FacesMessageUtil.addErrorMessage("Passwords do not match!");
			return;
		}

		try {
			Organizer email = userService.findByEmailOrganizer(userViewModel.getEmail());
			if (email != null) {
				throw new RegistrationException("This email is already used");
			}

			// Uploading the pictures and setting the links to userViewModel
			String logoPicName = ImageHelper.processProfilePicture(logoPicture);
			String companyPicName = ImageHelper.processProfilePicture(companyPicture);
			if (logoPicName != null) {
				userViewModel.setCompanyLogoLink(logoPicName);
			}
			if (companyPicName != null) {
				userViewModel.setCompanyPictureLink(companyPicName);
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

	/**
	 * Initialize the form data based on the organizer ID.
	 *
	 * @param organizerId ID of the organizer.
	 */
	public void initFormData(Long organizerId) {
		UserViewModel temp = userService.initOrganizer(organizerId);
		if (temp != null) {
			this.userViewModel = temp;

			Layout layout = userService.getLayoutForOrganizer(organizerId);
			if (layout != null) {
				userViewModel.setXhtmlFile(layout.getXhtmlFile());
				userViewModel.setLayoutName(layout.getName());
			}

			Customization customization = userService.getCustomizationForOrganizer(organizerId);
			if (customization != null) {
				userViewModel.setPrimaryColor(customization.getPrimaryColor());
				userViewModel.setSecondaryColor(customization.getSecondaryColor());
				userViewModel.setPrimaryFont(customization.getPrimaryFont());
				userViewModel.setSecondaryFont(customization.getSecondaryFont());
			}
		} else {
			FacesMessageUtil.addErrorMessage("Initialization failed: Organizer does not exist");
		}
	}

	/**
	 * Initialize organizer information and available layouts on bean instantiation.
	 */
	@PostConstruct
	public void init() {
		organizerId = getOrganizerIdFromSessionOrRequest();

		if (organizerId != null) {
			initFormData(organizerId);
			setupLayoutForOrganizer(organizerId);
		} else {
			allOrganizers = userService.getAllOrganizers();
			availableLayouts = layoutDAO.getAllLayouts();
		}
	}

	/**
	 * Retrieves the organizer ID from either the session or request.
	 * 
	 * @return The organizer ID or null if not found.
	 */
	private Long getOrganizerIdFromSessionOrRequest() {
		// Try to get from session first
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Long organizerId = (Long) session.getAttribute("organizerId");

		// If not in session, try from request param
		if (organizerId == null) {
			Map<String, String> params = context.getExternalContext().getRequestParameterMap();
			String organizerIdParam = params.get("organizerId");
			if (organizerIdParam != null) {
				try {
					organizerId = Long.parseLong(organizerIdParam);
				} catch (NumberFormatException e) {
					FacesMessageUtil.addErrorMessage("Invalid organizer ID format.");
				}
			}
		}
		return organizerId;
	}

	/**
	 * Setup layouts based on the organizer's subscription type.
	 * 
	 * @param organizerId ID of the organizer.
	 */
	private void setupLayoutForOrganizer(Long organizerId) {
		Organizer organizer = userService.readOrganizer(organizerId);
		if (organizer != null && organizer.getSubscription() != null && organizer.getSubscription().getType() != null) {
			SubscriptionType type = organizer.getSubscription().getType();
			availableLayouts = determineAvailableLayouts(type);

			if (!availableLayouts.isEmpty()) {
				userViewModel.setLayout(availableLayouts.get(0));
			}
		} else {
			Layout basicLayout = layoutDAO.getLayoutByName("Basic");
			availableLayouts = new ArrayList<>();
			availableLayouts.add(basicLayout);
			userViewModel.setLayout(basicLayout);
		}
	}

	/**
	 * Fetches the XHTML file (template) of a given organizer.
	 * 
	 * @param organizer The organizer entity.
	 * @return Path to the XHTML file or null if not available.
	 */
	public String getXhtmlFile(Organizer organizer) {
		if (organizer != null && organizer.getSubscription() != null
				&& organizer.getSubscription().getCustomization() != null
				&& organizer.getSubscription().getCustomization().getLayout() != null) {
			return organizer.getSubscription().getCustomization().getLayout().getXhtmlFile();
		}
		return null;
	}

	/**
	 * Updates the organizer's details including images.
	 */
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

	/**
	 * Deletes the specified organizer.
	 */
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

	/**
	 * Saves the subscription details for the organizer.
	 */
	public void saveSubscription() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		Long organizerId = (Long) session.getAttribute("organizerId");

		Subscription chosenSubscription = new Subscription();
		chosenSubscription.setType(userViewModel.getType());

		if (organizerId != null && chosenSubscription != null) {
			userService.updateSubscription(organizerId, chosenSubscription);
			FacesMessageUtil.addSuccessMessage("Subscription saved successfully!");
		}
	}

	/**
	 * Determines the available layouts for a subscription type.
	 * 
	 * @param type The subscription type.
	 * @return List of layouts.
	 */
	public List<Layout> determineAvailableLayouts(SubscriptionType type) {
		List<Layout> layouts = new ArrayList<>();

		Layout basicLayout = layoutDAO.getLayoutByName("Basic");
		if (basicLayout != null) {
			layouts.add(basicLayout);
		}

		if (type == null) {
			return layouts;
		}

		switch (type) {
		case STANDARD:
			break;
		case DELUXE:
			addLayoutToList(layouts, "Advanced");
			break;
		case PREMIUM:
			addLayoutToList(layouts, "Advanced");
			addLayoutToList(layouts, "Elite");
			break;
		default:
			break;
		}

		return layouts;
	}

	/**
	 * Utility method to add a layout by its name to a list.
	 * 
	 * @param layouts    List of layouts.
	 * @param layoutName Name of the layout.
	 */
	private void addLayoutToList(List<Layout> layouts, String layoutName) {
		Layout layout = layoutDAO.getLayoutByName(layoutName);
		if (layout == null) {
			System.out.println("Layout " + layoutName + " is null!");
		} else {
			layouts.add(layout);
		}
	}

	/**
	 * Updates the graphic settings of the organizer.
	 */
	public void updateGraphicSettings() {
		try {
			userViewModel = userService.updateGraphicSettings(userViewModel);
			System.out.println("Final xhtmlFileBEAN value before returning: " + userViewModel.getXhtmlFile());

			FacesMessageUtil.addSuccessMessage("Graphic settings updated successfully!");
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Update failed: " + e.getMessage());
		}
	}

	public int getEndIndex() {
		if (allOrganizers != null) {
			return Math.min(allOrganizers.size(), 9);
		}
		return 0;
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

	public DatabaseLayoutDAO getLayoutDAO() {
		return layoutDAO;
	}

	public void setLayoutDAO(DatabaseLayoutDAO layoutDAO) {
		this.layoutDAO = layoutDAO;
	}

	public List<Layout> getAvailableLayouts() {
		return availableLayouts;
	}

	public void setAvailableLayouts(List<Layout> availableLayouts) {
		this.availableLayouts = availableLayouts;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSearchCompanyName() {
		return searchCompanyName;
	}

	public void setSearchCompanyName(String searchCompanyName) {
		this.searchCompanyName = searchCompanyName;
	}

	public String getSearchCountry() {
		return searchCountry;
	}

	public void setSearchCountry(String searchCountry) {
		this.searchCountry = searchCountry;
	}

	public List<Organizer> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<Organizer> searchResults) {
		this.searchResults = searchResults;
	}

	public Long getOrganizerId() {
		return organizerId;
	}

	public void setOrganizerId(Long organizerId) {
		this.organizerId = organizerId;
	}

}