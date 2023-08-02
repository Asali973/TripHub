package triphub.managedBeans.products;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import triphub.entity.product.TourPackage;
import triphub.services.TourPackageService;
import triphub.viewModel.TourPackageFormViewModel;

@Named
@RequestScoped
public class TourPackageBean implements Serializable {	    

    @Inject
    private TourPackageService tourPackageService;      
    private static final long serialVersionUID = 1L;
    
    private TourPackageFormViewModel tourPackageVm = new TourPackageFormViewModel();

    public TourPackageBean() {
    }


    public void createPackage() {
        tourPackageService.createTourPackage(tourPackageVm);
    }


    public TourPackageFormViewModel getTourPackageVm() {
        return tourPackageVm;
    }

    public void setTourPackageVm(TourPackageFormViewModel tourPackageVm) {
        this.tourPackageVm = tourPackageVm;
    }

}
