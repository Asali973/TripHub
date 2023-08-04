package triphub.managedBeans.products;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import triphub.services.SubServiceService;
import triphub.viewModel.SubServicesViewModel;

@Named("subServiceBean")
@RequestScoped
public class SubServiceBean implements Serializable{

	@Inject
	private SubServiceService subserviceService;
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private SubServicesViewModel subservicevm = new SubServicesViewModel();

	
	public SubServiceBean(SubServiceService subserviceService, SubServicesViewModel subservicevm) {
		this.subserviceService = subserviceService;
		this.subservicevm = subservicevm;
	}

	public SubServiceBean() {
		
	}

	public void create() {
		subserviceService.create(subservicevm);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("TransportationTest added successfully !"));		
	}


	public SubServiceService getSubserviceService() {
		return subserviceService;
	}

	public void setSubserviceService(SubServiceService subserviceService) {
		this.subserviceService = subserviceService;
	}

	public SubServicesViewModel getSubservicevm() {
		return subservicevm;
	}

	public void setSubservicevm(SubServicesViewModel subservicevm) {
		this.subservicevm = subservicevm;
	}


}
