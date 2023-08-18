package triphub.services;

import java.util.List;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


import triphub.dao.service.AccommodationDAO;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.accommodation.AccommodationType;

import triphub.viewModel.SubServicesViewModel;

@ApplicationScoped
public class AccommodationService implements ServiceInterface {
	
	@Inject
	private AccommodationDAO accommodationDao;
	
	

	public AccommodationService() {
		
	}

	public AccommodationService(AccommodationDAO accommodationDao) {
		this.accommodationDao= accommodationDao;
		
	}
	

	@Override
	public void create(SubServicesViewModel accommodationVm) {
		accommodationDao.create(accommodationVm);
		
	}

	@Override
	public void read(Long id) {
		accommodationDao.read(id);
		
	}

	@Override
	public void update (SubServicesViewModel accommodationvm) {
		
		
	}

	@Override
	public void delete(Long id) {
		accommodationDao.delete(id);
		
	}
	
	public void update(Accommodation accommodation) {
		accommodationDao.update(accommodation);
	}
	

	public Accommodation findAccommodationByName(String nameAccommodation) {
		return accommodationDao.findAccommodationByName(nameAccommodation);
	}
	
	public List<Accommodation> findByType(AccommodationType AccommodationType){
		return accommodationDao.findByType(AccommodationType);
	}
	
	public List<Accommodation> getAllAccommodation() {
		return accommodationDao.getAllAccommodation();
	}
	
 
}
