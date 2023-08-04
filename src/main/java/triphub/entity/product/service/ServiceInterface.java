package triphub.entity.product.service;

import triphub.viewModel.SubServicesViewModel;

public interface ServiceInterface {

	public void create(SubServicesViewModel subservicevm);
	public void read(Long id);
	public void update();
	public void delete(Long id);
	
}
