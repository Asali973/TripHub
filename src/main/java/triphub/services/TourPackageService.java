package triphub.services;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

import triphub.dao.product.TourPackageDAO;
import triphub.entity.product.TourPackage;
import triphub.helpers.FacesMessageUtil;
import triphub.viewModel.TourPackageFormViewModel;


@Stateless
public class TourPackageService {
    @Inject
    private TourPackageDAO tourPackageDAO;
    
    public TourPackageService() {}

    public List<TourPackage> getAllTourPackages() {
        return tourPackageDAO.getAllTourPackages();
    }

    public TourPackage getTourPackageById(Long id) {
        return tourPackageDAO.read(id);
    }
    
    public TourPackage getTourPackageByName(String packageName) {
    	return tourPackageDAO.findPackageByName(packageName);
    }

    public TourPackage  createTourPackage(TourPackageFormViewModel tourPackageVm) {
    	return   tourPackageDAO.create(tourPackageVm);
    }

    public TourPackageFormViewModel updateTourPackageWithImage(TourPackageFormViewModel tourPackageVm) {
     
        return tourPackageDAO.updateTourPackage(tourPackageVm);
    }
    
    public TourPackageFormViewModel initTourPackage(Long id) {
    	return tourPackageDAO.initTourPackage(id);
    }
    
    public void deleteTourPackage(Long id) {
        tourPackageDAO.deleteTourPackageById(id);
    }
    
    public void delete(TourPackage tourPackage) {
        tourPackageDAO.delete(tourPackage);
        
    }
    
    public void update(TourPackage tourPackage) {
        tourPackageDAO.update(tourPackage);
        
    }
    public List<TourPackage> advancedSearch(String city, String state, String country, BigDecimal minPrice, BigDecimal maxPrice, String name, String themeName) {
        return tourPackageDAO.advancedSearch(city, state, country, minPrice, maxPrice, name, themeName);
    }
    
}
//public void updatePackage() {
//
//       // Handle image processing if needed
//       // ... Image processing ...
//
//   	tourPackageVm = tourPackageService.updateTourPackageWithImage(tourPackageVm);
// 
//       FacesMessageUtil.addErrorMessage("Update failed: ");
//   }

//public void initFormData(Long tourPackageId) {
//	 TourPackageFormViewModel temp = tourPackageService.initTourPackage(tourPackageId);
//       if (temp == null) {
//       	this.tourPackageVm= temp;
//       }else {
//           FacesMessageUtil.addErrorMessage("Initialization failed: Tour package does not exist");
//       }
//   }