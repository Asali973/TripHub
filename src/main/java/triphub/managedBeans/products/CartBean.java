package triphub.managedBeans.products;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import triphub.entity.product.CartItem;
import triphub.entity.product.TourPackage;
import triphub.entity.user.User;
import triphub.helpers.FacesMessageUtil;
import triphub.services.ICartService;
import triphub.services.TourPackageService;
import triphub.services.UserService;
import triphub.viewModel.UserViewModel;

@Named("cartBean")
@RequestScoped
public class CartBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private ICartService iCartService;

	private List<CartItem> cartItems;
	private TourPackage selectedTourPackage;
	@Inject
	private UserService userService;
	private UserViewModel userViewModel = new UserViewModel();
	private TourPackageBean tourPackageBean;
	@Inject
	private TourPackageService tourPackageService;
	private Long selectedPackageId;

	@PostConstruct
	public void init() {
		cartItems = iCartService.getCartItemsWithTourPackages();
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if (id != null) {
			Long tourPackageId = Long.parseLong(id);
			selectedTourPackage = tourPackageService.getTourPackageById(tourPackageId);
			if (selectedTourPackage == null) {
				// Handle case where tour package is not found
			}
		}

		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

		// Get the currently logged-in user from the session
		User user = (User) session.getAttribute("user");
		Long customerId = (Long) session.getAttribute("customerId");
		Long userId = (Long) session.getAttribute("userId");
		Long superAdminId = (Long) session.getAttribute("superAdminId");
		Long providerId = (Long) session.getAttribute("providerId");
		Long organizerId = (Long) session.getAttribute("organizerId");

		if (user != null) {
			initUserData(user.getId());
		}

	}

	public String addToCart() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String selectedPackageIdParam = params.get("selectedPackageId");

		if (selectedPackageIdParam != null) {
			Long selectedPackageId = Long.parseLong(selectedPackageIdParam);

			TourPackage selectedTourPackage = tourPackageService.getTourPackageById(selectedPackageId);

			if (selectedTourPackage != null) {
				User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

				iCartService.addToCart(selectedTourPackage, user);

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Added to Cart", selectedTourPackage.getName() + " added to your cart."));

				// Redirect to the Cart Page
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("cart.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Selected tour package not found."));
			}
		}
		return null; // Return null to stay on the same page
	}

	public void initUserData(Long userId) {
		UserViewModel temp = userService.initUser(userId);
		if (temp != null) {
			this.userViewModel = temp;
		} else {
			FacesMessageUtil.addErrorMessage("Initialization failed: User does not exist");
		}
	}

	

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public TourPackage getSelectedTourPackage() {
		return selectedTourPackage;
	}

	public void setSelectedTourPackage(TourPackage selectedTourPackage) {
		this.selectedTourPackage = selectedTourPackage;
	}

	public TourPackageBean getTourPackageBean() {
		return tourPackageBean;
	}

	public void setTourPackageBean(TourPackageBean tourPackageBean) {
		this.tourPackageBean = tourPackageBean;
	}

	public TourPackageService getTourPackageService() {
		return tourPackageService;
	}

	public void setTourPackageService(TourPackageService tourPackageService) {
		this.tourPackageService = tourPackageService;
	}

	public Long getSelectedPackageId() {
		return selectedPackageId;
	}

	public void setSelectedPackageId(Long selectedPackageId) {
		this.selectedPackageId = selectedPackageId;
	}

	public UserViewModel getUserViewModel() {
		return userViewModel;
	}

	public void setUserViewModel(UserViewModel userViewModel) {
		this.userViewModel = userViewModel;
	}

	public ICartService getiCartService() {
		return iCartService;
	}

	public void setiCartService(ICartService iCartService) {
		this.iCartService = iCartService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
