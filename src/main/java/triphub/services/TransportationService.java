package triphub.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import triphub.dao.service.TransportationDAO;
import triphub.entity.product.service.transportation.Transportation;
import triphub.entity.product.service.transportation.TransportationType;
import triphub.viewModel.TransportationViewModel;

@Stateless
public class TransportationService {

	@Inject
	private TransportationDAO transportationDAO;

	public TransportationService(TransportationDAO transportationDAO) {
		this.transportationDAO = transportationDAO;
		
	}
	
	public Transportation create (TransportationViewModel transportationvm) {
		return transportationDAO.create(transportationvm);
	}
	
	public Transportation read(Long id) {
		return transportationDAO.read(id);
	}
	
	public Transportation update(Transportation transportation) {
		return transportationDAO.update(transportation);
	}
	
	public void delete(Long id) {
		transportationDAO.delete(id);
	}
	
	public List<Transportation> findByType(TransportationType transportationType){
		return transportationDAO.findByType(transportationType);
	}
	
	 public List<Transportation> getAllTransportation() {
		 return transportationDAO.getAllTransportation();
	 }

}
