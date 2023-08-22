package triphub.managedBeans.products;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import triphub.entity.product.service.Service;
import triphub.entity.product.service.ServiceType;
import triphub.helpers.FacesMessageUtil;
import triphub.services.ServiceService;
import triphub.viewModel.SubServicesViewModel;

@Named("serviceBean")
@RequestScoped
public class ServiceBean {

	@Inject
	private ServiceService serviceService;
	
	@Inject
	private SubServicesViewModel servicevm;
	
	private static final long serialVersionUID = 1L;
	
	private List<Service> allServices;

	public ServiceBean() {
	}

	public ServiceBean(ServiceService serviceService, SubServicesViewModel servicevm, List<Service> allServices) {
		super();
		this.serviceService = serviceService;
		this.servicevm = servicevm;
		this.setAllServices(allServices);
	}
	
	@PostConstruct
	public void init() {
		setAllServices(serviceService.getAll());
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if (id != null) {
			Long serviceId = Long.parseLong(id);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedServiceId",
					serviceId);
			servicevm = serviceService.initService(serviceId);
			if (servicevm == null) {
				FacesMessageUtil.addErrorMessage("Initialization failed: Service does not exist");
			}
		}
	}

	public List<Service> getAllServices() {
		return allServices;
	}

	public void setAllServices(List<Service> allServices) {
		this.allServices = allServices;
	}
	
	public ServiceType[] getAllServiceTypes() {
        return ServiceType.values();
    }

	public SubServicesViewModel getServicevm() {
		return servicevm;
	}

	public void setServicevm(SubServicesViewModel servicevm) {
		this.servicevm = servicevm;
	}
	
}
