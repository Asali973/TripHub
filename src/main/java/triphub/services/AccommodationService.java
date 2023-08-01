package triphub.services;

import javax.inject.Inject;

import triphub.dao.service.AccommodationDAO;
import triphub.entity.product.service.accommodation.Accommodation;
import triphub.helpers.RegistrationException;
import triphub.viewModel.AccommodationViewModel;

public class AccommodationService {
	
	@Inject
	private AccommodationDAO accommodationDao;

	public AccommodationService() {
		
	}
	
	public Accommodation create (AccommodationViewModel accommodationVm) {
		return accommodationDao.create(accommodationVm);
		
	}
	
	public Accommodation  updateAccommodation(Accommodation accommodation) {
		return accommodationDao.updateAccommodation(accommodation);
	}
	
	public Accommodation findAccommodationByName(String nameAccommodation) throws RegistrationException {
		return accommodationDao.findAccommodationByName(nameAccommodation);
	}
	
	public void deleteAccommodation(Long id) {
		accommodationDao.deleteAccommodation(id);
	}
	
	
 
}
