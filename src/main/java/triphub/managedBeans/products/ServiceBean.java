package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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
public class ServiceBean implements Serializable {

	@Inject
	private ServiceService serviceService;
	
	@Inject
	private SubServicesViewModel servicevm;
	
	private static final long serialVersionUID = 1L;
	
	private ServiceType selectedServiceType;
	
	private List<Service> allServices;

	public ServiceBean() {
	}

	public ServiceBean(ServiceService serviceService, SubServicesViewModel servicevm, List<Service> allServices) {
		super();
		this.serviceService = serviceService;
		this.servicevm = servicevm;
		this.setAllServices(allServices);
	}
	
//	@PostConstruct
//	public void init() {
//		setAllServices(serviceService.getAll());
//		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
//		if (id != null) {
//			Long serviceId = Long.parseLong(id);
//			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedServiceId",
//					serviceId);
//			servicevm = serviceService.initService(serviceId);
//			if (servicevm == null) {
//				FacesMessageUtil.addErrorMessage("Initialization failed: Service does not exist");
//			}
//		}
//	}
	
	@PostConstruct
    public void init() {
        // Initialize other properties
        selectedServiceType = null; // Initialize the selected service type
    }
    public List<Service> getFilteredServices() {
        List<Service> filteredServices = new ArrayList<>();
        if (selectedServiceType == null) {
            filteredServices = allServices;
        } else {
            for (Service service : allServices) {
                if (service.getType() == selectedServiceType) {
                    filteredServices.add(service);
                }
            }
        }
        return filteredServices;
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

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public ServiceType getSelectedServiceType() {
		return selectedServiceType;
	}

	public void setSelectedServiceType(ServiceType selectedServiceType) {
		this.selectedServiceType = selectedServiceType;
	}
	
}
