package triphub.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

import triphub.dao.product.TourPackageDAO;
import triphub.entity.product.TourPackage;
import triphub.helpers.FacesMessageUtil;
import triphub.viewModel.TourPackageFormViewModel;


@Stateless
public class TourPackageService implements Serializable{
	private static final long serialVersionUID = 1L;
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


    public TourPackageFormViewModel initTourPackage(Long id) {
        TourPackage tourPackage = tourPackageDAO.findTourPackageById(id);
        if (tourPackage == null) {
            return null;
        }
        return tourPackage.initTourPackageFormViewModel();
    }

    public void updateTourPackage(TourPackageFormViewModel tourPackageVm) {
        try {
            tourPackageDAO.updateTourPackage(tourPackageVm);
        } catch (IllegalArgumentException e) {
            // Handle the case when the tour package with the provided ID was not found in the DAO
            FacesMessageUtil.addErrorMessage("Failed to update tour package: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected exceptions that might occur during the update process
            FacesMessageUtil.addErrorMessage("Failed to update tour package. An unexpected error occurred.");
        }
    }

       
    public void deleteTourPackage(TourPackageFormViewModel tourPackageVm) {
        tourPackageDAO.delete(tourPackageVm);
        
    }
   
   
    public List<TourPackage> advancedSearch(String city, String state, String country, BigDecimal minPrice, BigDecimal maxPrice, String name, String themeName) {
        return tourPackageDAO.advancedSearch(city, state, country, minPrice, maxPrice, name, themeName);
    }
    
}

//public TourPackageFormViewModel updateTourPackageWithImage(TourPackageFormViewModel tourPackageVm) {
//
// return tourPackageDAO.updateTourPackage(tourPackageVm);
//}
//
//public TourPackageFormViewModel updateTourPackage(TourPackageFormViewModel tourPackageVm)  {
//	TourPackageFormViewModel updatedtourPackageVm = tourPackageDAO.updateTourPackage(tourPackageVm);
//  
// return updatedtourPackageVm;
//
//}
//public TourPackageFormViewModel initTourPackage(Long id) {
//	return tourPackageDAO.initTourPackage(id);
//}
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