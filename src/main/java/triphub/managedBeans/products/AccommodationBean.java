package triphub.managedBeans.products;

import java.io.Serializable;

import javax.persistence.EntityManager;

import triphub.dao.service.AccommodationDAO;
import triphub.entity.product.service.accommodation.Accommodation;
import triphub.helpers.RegistrationException;
import triphub.services.AccommodationService;
import triphub.viewModel.AccommodationViewModel;

public class AccommodationBean implements Serializable {

	private AccommodationService accommodationService;
	
	private EntityManager em;

	public AccommodationBean() {
		
	}
	
	
	public Accommodation create (AccommodationViewModel accommodationVm) {
		return accommodationService.create(accommodationVm);
		
	}
	
	public Accommodation  updateAccommodation(Accommodation accommodation) {
		return accommodationService.updateAccommodation(accommodation);
	}
	
	public Accommodation findAccommodationByName(String nameAccommodation) throws RegistrationException {
		return accommodationService.findAccommodationByName(nameAccommodation);
	}
	
	public void deleteAccommodation(Long id) {
		accommodationService.deleteAccommodation(id);
	}
	
	
	
}
