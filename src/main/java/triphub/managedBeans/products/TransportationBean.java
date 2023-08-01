package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import triphub.dao.service.TransportationDAO;
import triphub.entity.product.service.transportation.Transportation;
import triphub.viewModel.TransportationViewModel;

public class TransportationBean implements Serializable {
	
	private TransportationDAO transportationDAO;
	private EntityManager em;
	private static final long serialVersionUID = 1L;
	private Transportation transportation;
	private List<Transportation> transportations;

	private TransportationViewModel transportationvm;
	
	
	
	

}
