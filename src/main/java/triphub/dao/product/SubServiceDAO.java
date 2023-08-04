package triphub.dao.product;


import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import triphub.entity.product.service.ServiceInterface;
import triphub.entity.product.service.SubService;
import triphub.entity.util.Address;
import triphub.viewModel.SubServicesViewModel;

@Stateless
public class SubServiceDAO{

	@PersistenceContext
	private EntityManager em;

	
	public SubServiceDAO(EntityManager em) {
		this.em = em;
	}

	
	public SubServiceDAO() {
	}


	public void create(SubServicesViewModel subservicevm) {
		
		SubService subservice = new SubService();
		subservice.setName(subservicevm.getName());
		subservice.setDescription(subservicevm.getDescription());
		
		Address address = new Address();
		address.setNum(subservicevm.getNum());
		address.setStreet(subservicevm.getStreet());
		address.setCity(subservicevm.getCity());
		address.setState(subservicevm.getState());
		address.setCountry(subservicevm.getCountry());
		address.setZipCode(subservicevm.getZipCode());
		
		em.persist(subservice);
		em.persist(address);

	}

//	@Override
//	public void read(Long id) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void update() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void delete(Long id) {
//		// TODO Auto-generated method stub
//		
//	}



}
