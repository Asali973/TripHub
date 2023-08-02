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

import triphub.services.TourPackageService;
import triphub.viewModel.TourPackageFormViewModel;

@Named
@RequestScoped
public class TourPackageBean implements Serializable {	
    private static final long serialVersionUID = 1L;

    @Inject
    private TourPackageService tourPackageService;   

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private TourPackageFormViewModel tourPackageVm = new TourPackageFormViewModel();

    public TourPackageBean() {
    }

    @Transactional
    public void createTourPackage() {
        EntityManager entityManager = null;
        try {
            // Get the EntityManager from the factory
            entityManager = entityManagerFactory.createEntityManager();

            // Begin the transaction
            entityManager.getTransaction().begin();

            // Call the service method to create the tour package
            tourPackageService.createTourPackage(tourPackageVm);

            // Commit the transaction
            entityManager.getTransaction().commit();

            // Optionally, you can update the tourPackages list after creating the new tour package

            // Reset the form
            tourPackageVm = new TourPackageFormViewModel();
        } catch (Exception e) {
            // If an exception occurs, rollback the transaction
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // Close the EntityManager to release resources
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public TourPackageFormViewModel getTourPackageVm() {
        return tourPackageVm;
    }

    public void setTourPackageVm(TourPackageFormViewModel tourPackageVm) {
        this.tourPackageVm = tourPackageVm;
    }

   
