package triphub.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import triphub.dao.service.TransportationDAO;
import triphub.entity.product.service.ServiceInterface;
import triphub.entity.product.service.transportation.Transportation;
import triphub.entity.product.service.transportation.TransportationType;
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

	@Override
	public void create(SubServicesViewModel transportationvm) {
		transportationDAO.create(transportationvm);		
	}

	@Override
	public void read(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public List<Transportation> findByType(TransportationType transportationType) {
		return transportationDAO.findByType(transportationType);
	}

	public List<Transportation> getAllTransportation() {
		return transportationDAO.getAllTransportation();
	}
	
	public void update(Transportation transportation) {
		transportationDAO.update(transportation);
	}

	public void delete(Long id) {
		transportationDAO.delete(id);
	}



}
