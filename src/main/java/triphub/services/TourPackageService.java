//package triphub.services;
//
//import java.util.List;
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import triphub.dao.product.TourPackageDAO;
//import triphub.entity.product.TourPackage;
//
//@Stateless
//public class TourPackageService {
//    @Inject
//    private TourPackageDAO tourPackageDAO;
//
//    public List<TourPackage> getAllTourPackages() {
//        return tourPackageDAO.getAllTourPackages();
//    }
//
//    public TourPackage getTourPackageById(Long id) {
//        return tourPackageDAO.read(id);
//    }
//
//    public void createTourPackage(TourPackage tourPackage) {
//        tourPackageDAO.createOrUpdate(tourPackage);
//    }
//
//    public void updateTourPackage(TourPackage tourPackage) {
//        tourPackageDAO.createOrUpdate(tourPackage);
//    }
//
//    public void deleteTourPackage(TourPackage tourPackage) {
//        tourPackageDAO.delete(tourPackage);
//    }
//}
