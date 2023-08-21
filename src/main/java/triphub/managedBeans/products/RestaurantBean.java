package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import triphub.entity.product.service.ServiceInterface;
import triphub.entity.product.service.restaurant.Restaurant;
import triphub.helpers.FacesMessageUtil;
import triphub.services.RestaurantService;
import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.TourPackageFormViewModel;

@Named("restaurantBean")
@RequestScoped
public class RestaurantBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RestaurantService restaurantService;

	@Inject
	private SubServicesViewModel restaurantvm = new SubServicesViewModel();
	
	private List<Restaurant> allRestaurants;

	public void setAllRestaurants(List<Restaurant> allRestaurants) {
		this.allRestaurants = allRestaurants;
	}

	public RestaurantBean(RestaurantService restaurantService, SubServicesViewModel restaurantvm) {
		super();
		this.restaurantService = restaurantService;
		this.restaurantvm = restaurantvm;
	}

	public RestaurantBean() {
	}
	
	@PostConstruct
	public void init() {
		allRestaurants = restaurantService.getAll();
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if (id != null) {
			Long restaurantId = Long.parseLong(id);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedRestaurantId",
					restaurantId);
			restaurantvm = restaurantService.initSubService(restaurantId);
			if (restaurantvm == null) {
				FacesMessageUtil.addErrorMessage("Initialization failed: Restaurant does not exist");
			}
		}
	}

	public void create() {
		restaurantService.create(restaurantvm);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Restaurant added successfully !"));
	}
	
	public String updateRestaurant() {
		try {
			restaurantService.update(restaurantvm);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Restaurant updated successfully!"));
			
			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/restaurantUpdate.xhtml?faces-redirect=true&id="
					+ restaurantvm.getId();
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);
			
		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update restaurant: " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update restaurant. An unexpected error occurred.");
		}clear();
		return null;		
	}

	public void clear() {
		restaurantvm = new SubServicesViewModel();
	}

	public void deleteRestaurant() {
		Long selectedRestaurantId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("selectedRestaurantId");
		if ( selectedRestaurantId == null) {
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
	
	public List<Restaurant> getAllRestaurants() {
		return restaurantService.getAll();
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

}
