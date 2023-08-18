package triphub.entity.product.service;

import triphub.viewModel.SubServicesViewModel;

public interface ServiceInterface {

	void create(SubServicesViewModel subservicevm);
	void read(Long id);
	void update(SubServicesViewModel subservicevm);
	void delete(Long id);
	
}
