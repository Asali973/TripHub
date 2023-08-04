package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import triphub.entity.product.service.transportation.Transportation;
import triphub.entity.product.service.transportation.TransportationType;
import triphub.services.TransportationService;
import triphub.viewModel.SubServicesViewModel;

@Named("transportationBean")
@RequestScoped
public class TransportationBean implements Serializable {
	
	@Inject
	private TransportationService transportationService;
	
	private static final long serialVersionUID = 1L;
	
	private SubServicesViewModel transportationvm = new SubServicesViewModel();

	public TransportationBean() {
		
	}
	
	public void create() {
		transportationService.create(transportationvm);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transportation added successfully !"));
	}
	
	public Transportation read(Long id) {
		return transportationService.read(id);
	} 
	
	public void update(Transportation transportation) {
		transportationService.update(transportation);
	}
	
	public void delete(Long id) {
		transportationService.delete(id);
	}
	
	public List<Transportation> findByType(TransportationType transportationType){
		return transportationService.findByType(transportationType);
	}
	
	 public List<Transportation> getAllTransportation() {
		 return transportationService.getAllTransportation();
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
