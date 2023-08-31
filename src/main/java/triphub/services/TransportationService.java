package triphub.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.Transactional;

import triphub.dao.services.TransportationDAO;
import triphub.entity.product.TourPackage;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;
import triphub.entity.subservices.TransportationType;
import triphub.helpers.FacesMessageUtil;

import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.TourPackageFormViewModel;

@ApplicationScoped
public class TransportationService implements ServiceInterface {

	@Inject
	@Default
	private TransportationDAO transportationDAO;

	public TransportationService() {
	}

	public TransportationService(TransportationDAO transportationDAO) {
		this.transportationDAO = transportationDAO;
	}

	public List<Transportation> findByType(TransportationType transportationType) {
		return transportationDAO.findByType(transportationType);
	}

	@Transactional
	@Override
	public Transportation create(SubServicesViewModel transportationvm, Long userId, String userType) {
		
		try {
            return transportationDAO.create(transportationvm, userId, userType); // Call create() method of DAO
        } catch (Exception e) {
            // Handle any unexpected exceptions that might occur during the create process
            FacesMessageUtil.addErrorMessage("Failed to create transportation. An unexpected error occurred.");
        }
		return null;		
	}
	


	@Override
	public Transportation read(Long id) {
		return transportationDAO.read(id);
	}

	
	public void update(SubServicesViewModel transportationvm) {
		try {
			transportationDAO.update(transportationvm);
		} catch (IllegalArgumentException e) {
			// Handle the case when the transportation with the provided ID was not found in the DAO		
			FacesMessageUtil.addErrorMessage("Failed to update transportation: " + e.getMessage());
		} catch (Exception e) {
			// Handle any other unexpected exceptions that might occur during the update process			
			FacesMessageUtil.addErrorMessage("Failed to update transportation. An unexpected error occurred.");
		}
		
	}

	@Override
	public void delete(SubServicesViewModel transportationvm) {
		transportationDAO.delete(transportationvm);

	}

	@Override
	public SubServicesViewModel initSubService(Long id) {
		Transportation transportation = transportationDAO.findById(id);
		if (transportation == null) {
			return null;
		}
		return transportation.initTransportationViewModel();
	}
	
	public Transportation getTransportationById(Long id) {
		return transportationDAO.read(id);
	}

	@Override
	public List<Transportation> getAll() {
		return transportationDAO.getAll();
	}

	@Override
	public Transportation findByName(String name) {
		return transportationDAO.findByName(name);
	}

	@Override
	public Transportation findById(Long id) {
		return transportationDAO.findById(id);
	}
	
	public List<Transportation> getTransportationForOrganizer(Long organizerId) {
		return transportationDAO.getTransportationForOrganizer(organizerId);
	}

	public List<Transportation> getTransportationForProvider(Long providerId) {
		return transportationDAO.getTransportationForProvider(providerId);
	}

}
