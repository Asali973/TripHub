package triphub.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;


import triphub.dao.service.AccommodationDAO;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.product.service.accommodation.Accommodation;

import triphub.helpers.FacesMessageUtil;
import triphub.viewModel.SubServicesViewModel;

@ApplicationScoped
public class AccommodationService implements ServiceInterface {
	
	@Inject
	@Default
	private AccommodationDAO accommodationDAO;
	
	

	public AccommodationService() {
		
	}

	public AccommodationService(AccommodationDAO accommodationDao) {
		this.accommodationDAO= accommodationDao;
		
	}
	
	public List<Accommodation> getAllAccommodation() {
		return accommodationDAO.getAll();
	}
	
	@Override
	public Accommodation create(SubServicesViewModel accommodationvm) {
	
		return accommodationDAO.create(accommodationvm);
	}
	
	@Override
	public Accommodation read(Long id) {
		return accommodationDAO.read(id) ;
	}
	
	@Override
	public SubServicesViewModel update(SubServicesViewModel accommodationvm) {
		try {
			accommodationDAO.update(accommodationvm);
		} catch (IllegalArgumentException e) {
			// Handle the case when the accommodation with the provided ID was not found in
			// the DAO
			FacesMessageUtil.addErrorMessage("Failed to update accommodation: " + e.getMessage());
		} catch (Exception e) {
			// Handle any other unexpected exceptions that might occur during the update
			// process
			FacesMessageUtil.addErrorMessage("Failed to update accommodation. An unexpected error occurred.");
		}
		return accommodationvm;
	}


	
	@Override
	public void delete(SubServicesViewModel accommodationvm) {
		accommodationDAO.delete(accommodationvm);	
		
	}

	@Override
	public SubServicesViewModel initSubService(Long id) {
		 Accommodation accommodation = accommodationDAO.findById(id);
	        if (accommodation == null) {
	            return null;
	        }
	        return accommodation.initAccommodationViewModel();
	}

	@Override
	public List<Accommodation> getAll() {
		return accommodationDAO.getAll();
	}

	@Override
	public Accommodation findByName(String name) {
		return accommodationDAO.findByName(name);
	}

	@Override
	public Accommodation findById(Long id) {
		return accommodationDAO.findById(id);
	}
	
 
}
