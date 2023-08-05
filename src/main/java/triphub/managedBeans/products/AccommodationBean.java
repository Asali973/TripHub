package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;


import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.accommodation.AccommodationType;
import triphub.helpers.RegistrationException;
import triphub.services.AccommodationService;
import triphub.viewModel.AccommodationViewModel;

@Named("accommodationBean")
@RequestScoped
public class AccommodationBean implements Serializable {

	@Inject
	private AccommodationService accommodationService;
	
	private EntityManager em;
	
	private AccommodationViewModel accommodationVm = new AccommodationViewModel();
	
	private static final long serialVersionUID = 1L;

	public AccommodationBean() {
		
	}
	
	
	public Accommodation create (AccommodationViewModel accommodationVm) {
		return accommodationService.create(accommodationVm);
		
	}
	
	public Accommodation  updateAccommodation(Accommodation accommodation) {
		return accommodationService.updateAccommodation(accommodation);
	}
	
	public Accommodation findAccommodationByName(String nameAccommodation) {
		return accommodationService.findAccommodationByName(nameAccommodation);
	}
	
	public void deleteAccommodation(Long id) {
		accommodationService.deleteAccommodation(id);
	}

	public List<Accommodation> findByType(AccommodationType AccommodationType){
		return accommodationService.findByType(AccommodationType);
	}
	
	

	public AccommodationService getAccommodationService() {
		return accommodationService;
	}


	public void setAccommodationService(AccommodationService accommodationService) {
		this.accommodationService = accommodationService;
	}


	public EntityManager getEm() {
		return em;
	}


	public void setEm(EntityManager em) {
		this.em = em;
	}


	public AccommodationViewModel getAccommodationVm() {
		return accommodationVm;
	}


	public void setAccommodationVm(AccommodationViewModel accommodationVm) {
		this.accommodationVm = accommodationVm;
	}
	
	
	
}
