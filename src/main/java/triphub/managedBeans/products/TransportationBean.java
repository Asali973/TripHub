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

import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.Transportation;
import triphub.entity.subservices.Transportation;
import triphub.entity.subservices.TransportationType;
import triphub.entity.util.CurrencyType;
import triphub.helpers.FacesMessageUtil;
import triphub.helpers.ImageHelper;
import triphub.services.TransportationService;
import triphub.viewModel.SubServicesViewModel;

@Named("transportationBean")
@RequestScoped
public class TransportationBean implements Serializable {

	@Inject
	private TransportationService transportationService;

	private static final long serialVersionUID = 1L;

	// @Inject
	private SubServicesViewModel transportationvm = new SubServicesViewModel();

	private List<Transportation> allTransportations;
	private Transportation selectedTransportation;
	private Transportation lastTransportationAdded;
	private String selectedCurrency;
	private boolean deletionSuccessful;

	private Part pictureTransport;
	private String picName;

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

		if (id != null && !id.isEmpty()) {
			Long transportationId = Long.parseLong(id);

			// Store the selected transportation id in the session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedTransportationId",
					transportationId);

			// Initialize a Transportation ViewModel or fetch specific data

			transportationvm = transportationService.initSubService(transportationId);
			if (transportationvm == null) {
				FacesMessageUtil.addErrorMessage("Initialization failed: Transportation ViewModel does not exist");
			}
			// Fetch the selected transportation using transportationService
			selectedTransportation = transportationService.getTransportationById(transportationId);
			if (selectedTransportation == null) {
				FacesMessageUtil
						.addErrorMessage("Initialization failed: Transportation does not exist in the database");

			}
		}
	}

	public String loadTransportations() {
		allTransportations = transportationService.getAll();

		return "tranportations";
	}

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
			picName = ImageHelper.processProfilePicture(pictureTransport);
		} catch (IOException e) {

			e.printStackTrace();
		}
		if (picName != null) {
			transportationvm.setLink(picName);
		}

		transportationService.create(transportationvm, userId, userType);

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transport added successfully !"));

		clear();
	}

	public String update() {
		try {
			transportationService.update(transportationvm);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Transportation updated successfully!"));

			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/TransportationForm.xhtml?faces-redirect=true";
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);

		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update Transportation: " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update Transportation. An unexpected error occurred.");
		}
		return null;
	}

	public String initFormUpdate() {
		try {

			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			String redirectUrl = contextPath + "/views/product/TransportationUpdate.xhtml?faces-redirect=true&id="
					+ transportationvm.getId();
			FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);

		} catch (IllegalArgumentException e) {
			FacesMessageUtil.addErrorMessage("Failed to update transportation : " + e.getMessage());
		} catch (Exception e) {
			FacesMessageUtil.addErrorMessage("Failed to update transportation. An unexpected error occurred.");

		}

		return null;

	}

	void clear() {
		transportationvm = new SubServicesViewModel();
	}

	public void delete() {
		Long selectedTransportationId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("selectedTransportationId");
		if (selectedTransportationId == null) {
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
	
	public String performDelete() {
		Long selectedTransportationId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("selectedTransportationId");
		SubServicesViewModel existingTransportationsVm = transportationService.initSubService(selectedTransportationId);

		if (existingTransportationsVm == null) {
			FacesMessageUtil.addErrorMessage("Invalid request: Transportation does not exist.");
			return "Transportation does not exist";
		}

		transportationService.delete(existingTransportationsVm);

		deletionSuccessful = true;

		return null;
	}

	public List<Transportation> getCurrentUserTransportations() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		String userType = (String) externalContext.getSessionMap().get("userType");

		if ("organizer".equals(userType)) {
			Long organizerId = (Long) externalContext.getSessionMap().get("organizerId");
			if (organizerId == null) {
				return new ArrayList<>();
			}
			return transportationService.getTransportationForOrganizer(organizerId);
		} else if ("provider".equals(userType)) {
			Long providerId = (Long) externalContext.getSessionMap().get("providerId");
			if (providerId == null) {
				return new ArrayList<>();
			}
			return transportationService.getTransportationForProvider(providerId);
		} else {

			return new ArrayList<>();
		}
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

	public TransportationService getTransportationService() {
		return transportationService;
	}

	public void setTransportationService(TransportationService transportationService) {
		this.transportationService = transportationService;
	}

	public List<Transportation> getAllTransportations() {
		return allTransportations;
	}

	public void setAllTransportations(List<Transportation> allTransportations) {
		this.allTransportations = allTransportations;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Transportation getSelectedTransportation() {
		return selectedTransportation;
	}

	public void setSelectedTransportation(Transportation selectedTransportation) {
		this.selectedTransportation = selectedTransportation;
	}

	public CurrencyType[] getAllCurrencyTypes() {
		return CurrencyType.values();
	}

	public Part getPictureTransport() {
		return pictureTransport;
	}

	public void setPictureTransport(Part pictureTransport) {
		this.pictureTransport = pictureTransport;
	}

	public Transportation getLastTransportationAdded() {
		return lastTransportationAdded;
	}

	public void setLastTransportationAdded(Transportation lastTransportationAdded) {
		this.lastTransportationAdded = lastTransportationAdded;
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

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

}
