package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

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

	public RestaurantBean(RestaurantService restaurantService, SubServicesViewModel restaurantvm) {
		super();
		this.restaurantService = restaurantService;
		this.restaurantvm = restaurantvm;
	}

	public RestaurantBean() {
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

	
	public List<Restaurant> getAllRestaurants() {
		return restaurantService.getAllRestaurants();
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
