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

@Named("restaurantBean")
@RequestScoped
public class RestaurantBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RestaurantService restaurantService;

	@Inject
	private SubServicesViewModel restaurantvm = new SubServicesViewModel();
	@Inject RestaurantDAO restaurantDao;
	
	

	private Restaurant selectedRestaurant;
	
	private Part pictureRestaurant;
	private String picName;
	private List<Restaurant> allRestaurants;

	private Restaurant lastRestaurantAdded;
	private boolean deletionSuccessful;

	public RestaurantBean(RestaurantService restaurantService, SubServicesViewModel restaurantvm, List<Restaurant> allRestaurants) {
		
		this.restaurantService = restaurantService;
		this.restaurantvm = restaurantvm;
		this.allRestaurants = allRestaurants;
	}

	public RestaurantBean() {
	}
	
	@PostConstruct
	public void init() {
	    allRestaurants = restaurantService.getAll();

	    String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

	    if (id != null && !id.isEmpty()) {
	        Long restaurantId = Long.parseLong(id);

	        
	        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedRestaurantId", restaurantId);
	        
	        
	        restaurantvm = restaurantService.initSubService(restaurantId);
	        if (restaurantvm == null) {
	            FacesMessageUtil.addErrorMessage("Initialization failed: Restaurant does not exist for the view model");
	        }
	        

	        selectedRestaurant = restaurantDao.findById(restaurantId);
	        if (selectedRestaurant == null) {
	            FacesMessageUtil.addErrorMessage("Initialization failed: Restaurant does not exist in the database");
	        }
	    }
	}

	public String loadAllRestaurants( ) {
		allRestaurants = restaurantService.getAll();
		return "restaurants";
	}
	
	public void create() {

	    String userType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userType");
	    
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

	public void clear() {
		restaurantvm = new SubServicesViewModel();
	}

	
	public String initFormUpdate() {
		try {
			

			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/RestaurantUpdate.xhtml?faces-redirect=true&id="
					+ restaurantvm.getId();
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);

		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update restaurant : " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update restaurant. An unexpected error occurred.");

		}

		return null; 
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
	
	public String performDelete() {
		Long selectedRestaurantId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("selectedRestaurantId");
		SubServicesViewModel existingRestaurantvm = restaurantService.initSubService(selectedRestaurantId);

		if (existingRestaurantvm == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Restaurant does not exist.");
			return "Restaurant does not exist";
		}

		restaurantService.delete(existingRestaurantvm);

		deletionSuccessful = true;

		return null;
	}
	
	public List<Restaurant> getCurrentUserRestaurants() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        String userType = (String) externalContext.getSessionMap().get("userType");

        if ("organizer".equals(userType)) {
            Long organizerId = (Long) externalContext.getSessionMap().get("organizerId");
            if (organizerId == null) {
                return new ArrayList<>();
            }
            return restaurantService.getRestaurantForOrganizer(organizerId);
        } 
        else if ("provider".equals(userType)) {
            Long providerId = (Long) externalContext.getSessionMap().get("providerId");
            if (providerId == null) {
                return new ArrayList<>();
            }
            return restaurantService.getRestaurantForProvider(providerId); 
        } 
        else {
 
            return new ArrayList<>();
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
	

	

	public Restaurant getSelectedRestaurant() {
		return selectedRestaurant;
	}

	public void setSelectedRestaurant(Restaurant selectedRestaurant) {
		this.selectedRestaurant = selectedRestaurant;
	}

	public RestaurantDAO getRestaurantDao() {
		return restaurantDao;
	}

	public void setRestaurantDao(RestaurantDAO restaurantDao) {
		this.restaurantDao = restaurantDao;
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
	
	public Restaurant getLastRestaurantAdded() {
		return lastRestaurantAdded;
	}

	public void setLastRestaurantAdded(Restaurant lastRestaurantAdded) {
		this.lastRestaurantAdded = lastRestaurantAdded;
	}

	public boolean isDeletionSuccessful() {
		return deletionSuccessful;
	}

	public void setDeletionSuccessful(boolean deletionSuccessful) {
		this.deletionSuccessful = deletionSuccessful;
	}

	public void setAllRestaurants(List<Restaurant> allRestaurants) {
		this.allRestaurants = allRestaurants;
	}

	

}
