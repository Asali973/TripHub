package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import triphub.dao.service.TransportationDAO;
import triphub.entity.product.service.transportation.Transportation;
import triphub.entity.product.service.transportation.TransportationType;
import triphub.services.TransportationService;
import triphub.viewModel.TransportationViewModel;

@Named("transportationBean")
@RequestScoped
public class TransportationBean implements Serializable {
	
	@Inject
	private TransportationService transportationService;
	
	private static final long serialVersionUID = 1L;
	
	private TransportationViewModel transportationvm = new TransportationViewModel();

	public TransportationBean() {
		
	}
	
	public Transportation create(TransportationViewModel transportationvm) {
		return transportationService.create(transportationvm);
	}
	
	public Transportation read(Long id) {
		return transportationService.read(id);
	} 
	
	public Transportation update(Transportation transportation) {
		return transportationService.update(transportation);
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
	

}
