package triphub.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import triphub.dao.service.TransportationDAO;
import triphub.entity.product.service.ServiceInterface;

import triphub.entity.service.Transportation;
import triphub.entity.service.TransportationType;

import triphub.helpers.FacesMessageUtil;

import triphub.viewModel.SubServicesViewModel;

@ApplicationScoped
public class TransportationService implements ServiceInterface{

	@Inject
	private TransportationDAO transportationDAO;

	public TransportationService() {
	}

	public TransportationService(TransportationDAO transportationDAO) {
		this.transportationDAO = transportationDAO;

	}

//	@Override
//	public void create(SubServicesViewModel transportationvm) {
//		transportationDAO.create(transportationvm);		
//	}
//
//	@Override
//	public void read(Long id) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void update(SubServicesViewModel transportationvm) {
//		// TODO Auto-generated method stub
//		
//	}
	
	public List<Transportation> findByType(TransportationType transportationType) {
		return transportationDAO.findByType(transportationType);
	}


	@Override
	public SubServicesViewModel create(SubServicesViewModel transportationvm) {
		try {
			transportationDAO.update(transportationvm);
		} catch (IllegalArgumentException e) {
			// Handle the case when the tour package with the provided ID was not found in
			// the DAO
			FacesMessageUtil.addErrorMessage("Failed to update restaurant: " + e.getMessage());
		} catch (Exception e) {
			// Handle any other unexpected exceptions that might occur during the update
			// process
			FacesMessageUtil.addErrorMessage("Failed to update restaurant. An unexpected error occurred.");
		}
		return transportationvm;	}

	@Override
	public Transportation read(Long id) {
		return transportationDAO.read(id);		
	}

	@Override
	public SubServicesViewModel update(SubServicesViewModel transportationvm) {
		try {
			transportationDAO.update(transportationvm);
		} catch (IllegalArgumentException e) {
			// Handle the case when the tour package with the provided ID was not found in
			// the DAO
			FacesMessageUtil.addErrorMessage("Failed to update restaurant: " + e.getMessage());
		} catch (Exception e) {
			// Handle any other unexpected exceptions that might occur during the update
			// process
			FacesMessageUtil.addErrorMessage("Failed to update restaurant. An unexpected error occurred.");
		}
		return transportationvm;
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



}
