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

	public OrganizerBean() {
	}

	public void searchOrganizers() {
		searchResults = userService.findOrganizerByCompanyOrCountry(searchCompanyName, searchCountry);
	}

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

	@PostConstruct
	public void init() {
		Long organizerId = getOrganizerIdFromSessionOrRequest();

		if (organizerId != null) {
			initFormData(organizerId);
			setupLayoutForOrganizer(organizerId);
		} else {
			allOrganizers = userService.getAllOrganizers();
			availableLayouts = layoutDAO.getAllLayouts();
		}
	}

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
//	@PostConstruct
//	public void init() {
//	    FacesContext context = FacesContext.getCurrentInstance();
//	    HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
//	    Long organizerId = (Long) session.getAttribute("organizerId");
//
//	    if (organizerId != null) {
//	        initFormData(organizerId);
//	        Organizer organizer = userService.readOrganizer(organizerId);
//	        
//
//	        if (organizer != null && organizer.getSubscription() != null && organizer.getSubscription().getType() != null) {
//	            SubscriptionType type = organizer.getSubscription().getType();
//	            availableLayouts = determineAvailableLayouts(type);
//	            
//	            if (!availableLayouts.isEmpty()) {
//	                userViewModel.setLayout(availableLayouts.get(0));
//	            }
//	        } else {
//	            Layout basicLayout = layoutDAO.getLayoutByName("Basic");
//	            availableLayouts = new ArrayList<>();
//	            availableLayouts.add(basicLayout);
//	            userViewModel.setLayout(basicLayout);
//	        }
//	    } else {
//	        allOrganizers = userService.getAllOrganizers();
//	        availableLayouts = layoutDAO.getAllLayouts(); 
//	    }
//	}
//	
//	
//	public void initFromRequestParam() {
//	    FacesContext context = FacesContext.getCurrentInstance();
//	    Map<String, String> params = context.getExternalContext().getRequestParameterMap();
//	    String organizerIdParam = params.get("organizerId");
//
//	    if (organizerIdParam != null) {
//	        try {
//	            Long organizerId = Long.parseLong(organizerIdParam);
//	            initFormData(organizerId);
//
//	            // Charger les layouts disponibles pour cet organizer spécifique
//	            Organizer organizer = userService.readOrganizer(organizerId);
//	            if (organizer != null && organizer.getSubscription() != null && organizer.getSubscription().getType() != null) {
//	                SubscriptionType type = organizer.getSubscription().getType();
//	                availableLayouts = determineAvailableLayouts(type);
//
//	                if (!availableLayouts.isEmpty()) {
//	                    userViewModel.setLayout(availableLayouts.get(0)); // Ceci est juste un exemple, adaptez-le selon votre besoin.
//	                }
//	            } else {
//	                Layout basicLayout = layoutDAO.getLayoutByName("Basic");
//	                availableLayouts = new ArrayList<>();
//	                availableLayouts.add(basicLayout);
//	                userViewModel.setLayout(basicLayout);
//	            }
//	        } catch (NumberFormatException e) {
//	            FacesMessageUtil.addErrorMessage("Invalid organizer ID format.");
//	        }
//	    } else {
//	        FacesMessageUtil.addErrorMessage("Organizer ID is not provided.");
//	    }
//	}

	public String getXhtmlFile(Organizer organizer) {
		if (organizer != null && organizer.getSubscription() != null
				&& organizer.getSubscription().getCustomization() != null
				&& organizer.getSubscription().getCustomization().getLayout() != null) {
			return organizer.getSubscription().getCustomization().getLayout().getXhtmlFile();
		}
		return null;
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
		chosenSubscription.setType(userViewModel.getType());

		if (organizerId != null && chosenSubscription != null) {
			userService.updateSubscription(organizerId, chosenSubscription);
			FacesMessageUtil.addSuccessMessage("Subscription saved successfully!");
		}
	}

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
			addLayoutToList(layouts, "Basic");
			break;
		case DELUXE:
			addLayoutToList(layouts, "Basic");
			addLayoutToList(layouts, "Advanced");
			addLayoutToList(layouts, "Advanced+");
			break;
		case PREMIUM:
			addLayoutToList(layouts, "Basic");
			addLayoutToList(layouts, "Advanced");
			addLayoutToList(layouts, "Advanced+");
			addLayoutToList(layouts, "Elite");
			addLayoutToList(layouts, "Elite+");
			addLayoutToList(layouts, "Elite Pro");
			break;
		default:
			break;
		}
		return layouts;
	}

	private void addLayoutToList(List<Layout> layouts, String layoutName) {
		Layout layout = layoutDAO.getLayoutByName(layoutName);
		if (layout == null) {
			System.out.println("Layout " + layoutName + " is null!");
		} else {
			layouts.add(layout);
		}
	}

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
}