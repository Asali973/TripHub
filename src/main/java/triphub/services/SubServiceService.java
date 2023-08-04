package triphub.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import triphub.dao.product.SubServiceDAO;
import triphub.entity.product.service.ServiceInterface;
import triphub.viewModel.SubServicesViewModel;

@ApplicationScoped
public class SubServiceService  implements ServiceInterface{

	@Inject 
	private SubServiceDAO subservDAO;

	public SubServiceService(SubServiceDAO subservDAO) {
		this.subservDAO = subservDAO;
	}

	public SubServiceService() {

	}
	
	@Override
	public void create(SubServicesViewModel subservicevm) {
		subservDAO.create(subservicevm);
	}
	
	@Override
	public void read(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override

	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override

	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	public SubServiceDAO getSubservDAO() {
		return subservDAO;
	}

	public void setSubservDAO(SubServiceDAO subservDAO) {
		this.subservDAO = subservDAO;
	}
	
}
