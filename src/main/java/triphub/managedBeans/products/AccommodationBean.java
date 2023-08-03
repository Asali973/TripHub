package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;


import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.accommodation.AccommodationType;

import triphub.services.AccommodationService;

import triphub.viewModel.SubServicesViewModel;

@Named("accommmodationBean")
@RequestScoped
public class AccommodationBean implements Serializable {

	@Inject
	private AccommodationService accommodationService;
	
	private EntityManager em;
	
	private SubServicesViewModel accommodationVm = new SubServicesViewModel();
	
	

	public AccommodationBean() {
		
	}
	
	
	public Accommodation create (SubServicesViewModel accommodationVm) {
		return accommodationService.create(accommodationVm);
		
	}
	
	public void updateAccommodation(Accommodation accommodation) {
		accommodationService.updateAccommodation(accommodation);
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


	public SubServicesViewModel getAccommodationVm() {
		return accommodationVm;
	}


	public void setAccommodationVm(SubServicesViewModel accommodationVm) {
		this.accommodationVm = accommodationVm;
	}


	
	
	
	
}
