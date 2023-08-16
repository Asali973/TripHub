package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;



import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.accommodation.AccommodationType;
import triphub.entity.product.service.transportation.Transportation;
import triphub.entity.product.service.transportation.TransportationType;
import triphub.services.AccommodationService;

import triphub.viewModel.SubServicesViewModel;

@Named("accommodationBean")
@RequestScoped
public class AccommodationBean implements Serializable {

	@Inject
	private AccommodationService accommodationService;
	
	
	@Inject
	private SubServicesViewModel accommodationVm = new SubServicesViewModel();
	
	private static final long serialVersionUID = 1L;

	public AccommodationBean() {
		
	}
	
	
	public void create () {
		 accommodationService.create(accommodationVm);
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Accommodation added successfully !"));
		
	}
	
	public void  update(Accommodation accommodation) {
		accommodationService.update(accommodation);
	}
	
	public Accommodation findAccommodationByName(String nameAccommodation) {
		return accommodationService.findAccommodationByName(nameAccommodation);
	}
	
	public void deleteAccommodation(Long id) {
		accommodationService.delete(id);
	}

	public List<Accommodation> findByType(AccommodationType AccommodationType){
		return accommodationService.findByType(AccommodationType);
	}
	
	 public List<Accommodation> getAllAccommodation() {
		 return accommodationService.getAllAccommodation();
	 }


	public AccommodationService getAccommodationService() {
		return accommodationService;
	}


	public void setAccommodationService(AccommodationService accommodationService) {
		this.accommodationService = accommodationService;
	}


	public SubServicesViewModel getAccommodationVm() {
		return accommodationVm;
	}


	public void setAccommodationVm(SubServicesViewModel accommodationVm) {
		this.accommodationVm = accommodationVm;
	}
	
	public AccommodationType[] getAllAccommodationTypes() {
        return AccommodationType.values();
    }

	
	
	
	
}
