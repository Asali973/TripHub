package triphub.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import triphub.dao.service.AccommodationDAO;
import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.accommodation.AccommodationType;
import triphub.viewModel.SubServicesViewModel;
@Stateless
public class AccommodationService {
	
	@Inject
	private AccommodationDAO accommodationDao;
	
	

	public AccommodationService() {
		
	}

	public AccommodationService(AccommodationDAO accommodationDao) {
		this.accommodationDao= accommodationDao;
		
	}
	
	public Accommodation create (SubServicesViewModel accommodationVm) {
		return accommodationDao.createAccommodation(accommodationVm);
		
	}
	
	public void  updateAccommodation(Accommodation accommodation) {
		accommodationDao.updateAccommodation(accommodation);
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
