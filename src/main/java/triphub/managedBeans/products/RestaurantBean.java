package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import triphub.entity.product.service.restaurant.Restaurant;
import triphub.services.RestaurantService;
import triphub.viewModel.SubServicesViewModel;

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
