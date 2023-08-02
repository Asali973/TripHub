package triphub.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import triphub.dao.service.AccommodationDAO;
import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.accommodation.AccommodationType;
import triphub.helpers.RegistrationException;
import triphub.viewModel.AccommodationViewModel;
@Stateless
public class AccommodationService {
	
	@Inject
	private AccommodationDAO accommodationDao;
	
	

	public AccommodationService() {
		
	}

	public AccommodationService(AccommodationDAO accommodationDao) {
		this.accommodationDao= accommodationDao;
		
	}
	
	public Accommodation create (AccommodationViewModel accommodationVm) {
		return accommodationDao.create(accommodationVm);
		
	}
	
	public Accommodation  updateAccommodation(Accommodation accommodation) {
		return accommodationDao.updateAccommodation(accommodation);
	}
	
	public Accommodation findAccommodationByName(String nameAccommodation) {
		return accommodationDao.findAccommodationByName(nameAccommodation);
	}
	
	public void deleteAccommodation(Long id) {
		accommodationDao.deleteAccommodation(id);
	}
	
	public List<Accommodation> findByType(AccommodationType AccommodationType){
		return accommodationDao.findByType(AccommodationType);
	}
	
	
 
}
