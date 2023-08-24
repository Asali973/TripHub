package triphub.entity.product.service;

import java.util.List;

import triphub.entity.subservices.Transportation;
import triphub.viewModel.SubServicesViewModel;

public interface ServiceInterface<T> {

	T create(SubServicesViewModel transportationvm, Long userId, String userType);
	T read(Long id);
	
	//SubServicesViewModel update(SubServicesViewModel subservicevm);
	//void update(SubServicesViewModel subservicevm);
	void delete(SubServicesViewModel subservicevm);
	SubServicesViewModel initSubService(Long id);
	List<T> getAll();
	T findByName(String name);
	T findById(Long id);
	
}
