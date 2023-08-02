package triphub.services;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

import triphub.dao.product.TourPackageDAO;
import triphub.entity.product.TourPackage;
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

    public TourPackage  createTourPackage(TourPackageFormViewModel tourPackageVm) {
    	return   tourPackageDAO.create(tourPackageVm);
    }

    public void updateTourPackage(TourPackageFormViewModel tourPackageVm) {
        tourPackageDAO.createOrUpdate(tourPackageVm);
    }

    public void deleteTourPackage(TourPackage tourPackage) {
        tourPackageDAO.delete(tourPackage);
    }
}
