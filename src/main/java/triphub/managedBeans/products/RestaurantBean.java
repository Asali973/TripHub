package triphub.managedBeans.products;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import triphub.dao.service.RestaurantDAO;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.Restaurant;
import triphub.entity.util.CurrencyType;
import triphub.helpers.FacesMessageUtil;
import triphub.helpers.ImageHelper;
import triphub.services.RestaurantService;
import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.TourPackageFormViewModel;

/**
 * Managed bean for handling restaurant related operations.
 */
@Named("restaurantBean")
@RequestScoped
public class RestaurantBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RestaurantService restaurantService;

//	@Inject
	private SubServicesViewModel restaurantvm = new SubServicesViewModel();
	@Inject
	private RestaurantDAO restaurantDao;

	private List<Restaurant> allRestaurants;
	private Restaurant lastRestaurantAdded;
	private String selectedCurrency;
	private boolean deletionSuccessful;

	private Restaurant selectedRestaurant;

	private Long restaurantId;

	private Part pictureRestaurant;
	private String picName;

	private Long selectedRestaurantIdToAdd;

	public RestaurantBean(RestaurantService restaurantService, SubServicesViewModel restaurantvm,
			List<Restaurant> allRestaurants) {

		this.restaurantService = restaurantService;
		this.restaurantvm = restaurantvm;
		this.allRestaurants = allRestaurants;
	}

	public RestaurantBean() {
	}

	/**
	 * Initialization method executed after bean construction.
	 */
	@PostConstruct
	public void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		restaurantId = (Long) externalContext.getSessionMap().get("selectedRestaurantId");

		if (restaurantId == null) {
			String idParam = externalContext.getRequestParameterMap().get("id");
			if (idParam != null && !idParam.trim().isEmpty()) {
				try {
					restaurantId = Long.parseLong(idParam);
					externalContext.getSessionMap().put("selectedRestaurantId", restaurantId);
				} catch (NumberFormatException e) {
					FacesMessageUtil.addErrorMessage("Id not valid");
					return;
				}
			}
		}

		if (restaurantId != null) {
			restaurantvm = restaurantService.initSubService(restaurantId);
			if (restaurantvm == null) {
				FacesMessageUtil.addErrorMessage("Restaurant does not exist");
				return;
			}

			selectedRestaurant = restaurantService.findById(restaurantId);
			if (selectedRestaurant == null) {
				FacesMessageUtil.addErrorMessage("Restaurant does not exist");
				return;
			}
		} else {
			allRestaurants = restaurantService.getAll();
		}
	}

	/**
	 * Load all restaurants.
	 *
	 * @return navigation string
	 */
	public String loadAllRestaurants() {
		allRestaurants = restaurantService.getAll();

		return "restaurants";
	}

	/**
	 * Creates a new restaurant.
	 */
	public void create() {

		String userType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("userType");

		Long userId;
		if ("organizer".equals(userType)) {
			userId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("organizerId");
		} else if ("provider".equals(userType)) {
			userId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("providerId");
		} else {
			userId = null;
		}

		// Uploading the picture and setting the link to ViewModel
		try {
			picName = ImageHelper.processProfilePicture(pictureRestaurant);
		} catch (IOException e) {

			e.printStackTrace();
		}
		if (picName != null) {
			restaurantvm.setLink(picName);
		}

		restaurantService.create(restaurantvm, userId, userType);

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Restaurant added successfully !"));

		clear();
	}

	/**
	 * Update an existing restaurant's details.
	 *
	 * @return navigation string
	 */
	public String updateRestaurant() {
		try {
			restaurantService.update(restaurantvm);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Restaurant updated successfully!"));

			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/RestaurantForm.xhtml?faces-redirect=true";
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);

		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update restaurant: " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update restaurant. An unexpected error occurred.");
		}
		return null;
	}

	/**
	 * Initialize the restaurant update form.
	 *
	 * @return navigation string
	 */
	public String initFormUpdate() {
		try {

			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/RestaurantUpdate.xhtml?faces-redirect=true&id="
					+ restaurantvm.getId();
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);

		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update restaurant: " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update restaurant. An unexpected error occurred.");

		}
		return null;
	}

	/**
	 * Reset the view model.
	 */
	public void clear() {
		restaurantvm = new SubServicesViewModel();
	}

	/**
	 * Prepare for restaurant deletion by showing a confirmation dialog.
	 */
	public void deleteRestaurant() {
		Long selectedRestaurantId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("selectedRestaurantId");
		if (selectedRestaurantId == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Restaurant ID not found in session.");
			return;
		}

		SubServicesViewModel existingRestaurantVm = restaurantService.initSubService(selectedRestaurantId);
		if (existingRestaurantVm == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Restaurant does not exist.");
			return;
		}

		FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("confirmDelete();");
	}

	/**
	 * Fetch restaurants belonging to the current user.
	 *
	 * @return list of restaurants
	 */
	public List<Restaurant> getCurrentUserRestaurants() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		String userType = (String) externalContext.getSessionMap().get("userType");
		Long userId = null;

		if ("organizer".equals(userType)) {
			userId = (Long) externalContext.getSessionMap().get("organizerId");
		} else if ("provider".equals(userType)) {
			userId = (Long) externalContext.getSessionMap().get("providerId");
		}

		if (userId == null) {
			String userIdParam = externalContext.getRequestParameterMap().get("userId");
			if (userIdParam != null && !userIdParam.trim().isEmpty()) {
				try {
					userId = Long.parseLong(userIdParam);
				} catch (NumberFormatException e) {
					FacesMessageUtil.addErrorMessage("Format d'ID d'utilisateur non valide.");
					return new ArrayList<>();
				}
			}
		}

		if (userId == null) {
			return new ArrayList<>();
		}

		return restaurantService.getRestaurantForOrganizer(userId);
	}

	/**
	 * Perform the deletion of a restaurant.
	 *
	 * @return navigation string
	 */
	public String performDelete() {
		Long selectedRestaurantId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("selectedRestaurantId");
		SubServicesViewModel existingRestaurantvm = restaurantService.initSubService(selectedRestaurantId);

		if (existingRestaurantvm == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Accommodation does not exist.");
			return "Accommodation does not exist";
		}

		restaurantService.delete(existingRestaurantvm);

		deletionSuccessful = true;

		return null;
	}

	/**
	 * Add a selected restaurant to an organizer.
	 */
	public void addRestaurantToOrganizer() {
		String userType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("userType");
		Long userId;

		if ("organizer".equals(userType)) {
			userId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("organizerId");
		} else {
			FacesMessageUtil.addErrorMessage("Only an organizer can add a restaurant");
			return;
		}

		String restaurantIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("id");

		if (restaurantIdParam == null) {
			FacesMessageUtil.addErrorMessage("Select a restaurant.");
			return;
		}

		Long selectedRestaurantIdToAdd;
		try {
			selectedRestaurantIdToAdd = Long.parseLong(restaurantIdParam);
		} catch (NumberFormatException e) {
			FacesMessageUtil.addErrorMessage("Restaurant id no valid.");
			return;
		}

		boolean result = restaurantDao.addRestaurantToOrganizer(userId, selectedRestaurantIdToAdd);

		if (result) {
			FacesMessageUtil.addSuccessMessage("Restaurant added with success.");
		} else {
			FacesMessageUtil.addErrorMessage("Error when added a restaurant.");
		}
	}

	public List<Restaurant> getAllRestaurants() {
		return restaurantService.getAll();
	}

	public CurrencyType[] getAllCurrencyTypes() {
		return CurrencyType.values();
	}

	public RestaurantService getRestaurantService() {
		return restaurantService;
	}

	public void setRestaurantService(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}

	public SubServicesViewModel getRestaurantvm() {
		return restaurantvm;
	}

	public void setRestaurantvm(SubServicesViewModel restaurantvm) {
		this.restaurantvm = restaurantvm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setAllRestaurants(List<Restaurant> allRestaurants) {
		this.allRestaurants = allRestaurants;
	}

	public Restaurant getSelectedRestaurant() {
		return selectedRestaurant;
	}

	public void setSelectedRestaurant(Restaurant selectedRestaurant) {
		this.selectedRestaurant = selectedRestaurant;
	}

	public Part getPictureRestaurant() {
		return pictureRestaurant;
	}

	public void setPictureRestaurant(Part pictureRestaurant) {
		this.pictureRestaurant = pictureRestaurant;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Restaurant getLastRestaurantAdded() {
		return lastRestaurantAdded;
	}

	public void setLastRestaurantAdded(Restaurant lastRestaurantAdded) {
		this.lastRestaurantAdded = lastRestaurantAdded;
	}

	public String getSelectedCurrency() {
		return selectedCurrency;
	}

	public void setSelectedCurrency(String selectedCurrency) {
		this.selectedCurrency = selectedCurrency;
	}

	public boolean isDeletionSuccessful() {
		return deletionSuccessful;
	}

	public void setDeletionSuccessful(boolean deletionSuccessful) {
		this.deletionSuccessful = deletionSuccessful;
	}

	public Long getSelectedRestaurantIdToAdd() {
		return selectedRestaurantIdToAdd;
	}

	public void setSelectedRestaurantIdToAdd(Long selectedRestaurantIdToAdd) {
		this.selectedRestaurantIdToAdd = selectedRestaurantIdToAdd;
	}

}
