package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import triphub.entity.product.service.restaurant.Restaurant;
import triphub.entity.product.service.transportation.Transportation;
import triphub.entity.product.service.transportation.TransportationType;
import triphub.helpers.FacesMessageUtil;
import triphub.services.TransportationService;
import triphub.viewModel.SubServicesViewModel;

@Named("transportationBean")
@RequestScoped
public class TransportationBean implements Serializable {

	@Inject
	private TransportationService transportationService;

	private static final long serialVersionUID = 1L;

	@Inject
	private SubServicesViewModel transportationvm = new SubServicesViewModel();

	private List<Transportation> allTransportations;

	public TransportationBean() {

	}

	public TransportationBean(TransportationService transportationService, SubServicesViewModel transportationvm,
			List<Transportation> allTransportations) {
		super();
		this.transportationService = transportationService;
		this.transportationvm = transportationvm;
		this.allTransportations = allTransportations;
	}

	@PostConstruct
	public void init() {
		allTransportations = transportationService.getAll();
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if (id != null) {
			Long restaurantId = Long.parseLong(id);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedTransportationId",
					restaurantId);
			transportationvm = transportationService.initSubService(restaurantId);
			if (transportationvm == null) {
				FacesMessageUtil.addErrorMessage("Initialization failed: Transportation does not exist");
			}
		}
	}

	public void create() {
		transportationService.create(transportationvm);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transportation added successfully !"));
	}

//	public Transportation read(Long id) {
//		return transportationService.read(id);
//	} 
//	
	public String update(Transportation transportation) {
		try {
			transportationService.update(transportationvm);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Transportation updated successfully!"));

			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/transportationUpdate.xhtml?faces-redirect=true&id="
					+ transportationvm.getId();
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);

		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update Transportation: " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update Transportation. An unexpected error occurred.");
		}
		clear();
		return null;
	}

	public void clear() {
		transportationvm = new SubServicesViewModel();
	}
	
	public void delete() {
		Long selectedTransportationId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("selectedTransportationId");
		if ( selectedTransportationId == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Transportation ID not found in session.");
			return;
		}

		SubServicesViewModel existingTransportationsVm = transportationService.initSubService(selectedTransportationId);
		if (existingTransportationsVm == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Transportation does not exist.");
			return;
		}

		FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("confirmDelete();");
	}	

	public List<Transportation> findByType(TransportationType transportationType) {
		return transportationService.findByType(transportationType);
	}

	public List<Transportation> getAllTransportation() {
		return transportationService.getAll();
	}

	public SubServicesViewModel getTransportationvm() {
		return transportationvm;
	}

	public void setTransportationvm(SubServicesViewModel transportationvm) {
		this.transportationvm = transportationvm;
	}

	public TransportationType[] getAllTransportationTypes() {
		return TransportationType.values();
	}

}
