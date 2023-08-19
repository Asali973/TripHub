package triphub.dao.user;

import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.Part;

import triphub.entity.subscription.Customization;
import triphub.entity.subscription.Layout;
import triphub.entity.subscription.Subscription;
import triphub.entity.user.*;
import triphub.entity.util.*;
import triphub.viewModel.UserViewModel;

@Stateless
public class OrganizerDAO {

	@PersistenceContext
	private EntityManager em;

	public OrganizerDAO() {
	}

	public Organizer createOrganizer(UserViewModel form) {

		User user = User.createUserFromViewModel(form);
		Address address = Address.createAddressFromViewModel(form);
		user.setAddress(address);

		FinanceInfo finance = FinanceInfo.createFinanceInfoFromViewModel(form);
		user.setFinance(finance);

		CompanyInfo companyInfo = CompanyInfo.createCompanyInfoFromViewModel(form);
		Administration administration = Administration.createAdministrationFromViewModel(form);

		Organizer organizer = new Organizer();
		organizer.setUser(user);
		organizer.setId(form.getOrganizerId());
		organizer.setCompanyInfo(companyInfo);
		organizer.setAdministration(administration);

		em.persist(companyInfo);
		em.persist(administration);
		em.persist(finance);
		em.persist(address);
		em.persist(user);
		em.persist(organizer);
		em.flush();
		return organizer;
	}
	
	public UserViewModel updateGraphicSettings(UserViewModel userViewModel) {
	    Organizer organizer = em.find(Organizer.class, userViewModel.getOrganizerId());

	    if (organizer == null) {
	        return null;
	    }

	    Subscription subscription = organizer.getSubscription();
	    if (subscription == null) {
	        subscription = new Subscription();
	        organizer.setSubscription(subscription);
	        em.persist(subscription);
	    }

	    Customization customization = subscription.getCustomization();
	    if (customization == null) {
	        customization = new Customization();
	        subscription.setCustomization(customization);
	        em.persist(customization);
	    }

	    customization.setPrimaryColor(userViewModel.getPrimaryColor());
	    customization.setSecondaryColor(userViewModel.getSecondaryColor());
	    customization.setPrimaryFont(userViewModel.getPrimaryFont());
	    customization.setSecondaryFont(userViewModel.getSecondaryFont());
	    customization.setLogoUrl(userViewModel.getLogoUrl());
	    customization.setBackgroundUrl(userViewModel.getBackgroundUrl());
	    
	    // Set the Layout for Customization directly using userViewModel.layout
	    if (userViewModel.getLayout() != null) {
	        customization.setLayout(userViewModel.getLayout());
	        userViewModel.setXhtmlFile(userViewModel.getLayout().getXhtmlFile());
	        System.out.println("xhtmlFile value: " + userViewModel.getXhtmlFile());
	    }

//	    // Set the Layout for Customization
//	    if (userViewModel.getLayoutName() != null && !userViewModel.getLayoutName().isEmpty()) {
//	        try {
//	            Layout layout = em.createQuery("SELECT l FROM Layout l WHERE l.name = :layoutName", Layout.class)
//	                              .setParameter("layoutName", userViewModel.getLayoutName())
//	                              .getSingleResult();
//	            if (layout != null) {
//	                customization.setLayout(layout);
//	                userViewModel.setXhtmlFile(layout.getXhtmlFile()); // Set the xhtmlFile for the UserViewModel
//	            }
//	        } catch (NoResultException e) {
//	            // Handle the case where no layout is found with the provided name
//	            System.err.println("No layout found with name: " + userViewModel.getLayoutName());
//	        }
//	    }

	    if (customization.getId() != null) {
	        em.merge(customization);
	    }

	    em.merge(subscription);
	    em.merge(organizer);

	    return userViewModel;
	}


	
	public UserViewModel updateOrganizer(UserViewModel userViewModel) {
		Organizer organizer = em.find(Organizer.class, userViewModel.getOrganizerId());
		
		if (organizer == null) {
			return null;
		}

		organizer.updateOrganizerFromViewModel(userViewModel);

		em.persist(organizer);
		em.flush();

		return userViewModel;
	}

	public UserViewModel initOrganizer(Long organizerId) {
		Organizer organizer = em.find(Organizer.class, organizerId);
		if (organizer == null) {
			return null;
		}

		return organizer.initOrganizerViewModel();
	}

	public Organizer readOrganizer(Long id) {
		return em.find(Organizer.class, id);
	}

	public void deleteOrganizer(Long id) {
		Organizer organizer = em.find(Organizer.class, id);
		if (organizer != null) {
			em.remove(organizer);
		}
	}

	public Organizer findByEmailOrganizer(String email) {
		TypedQuery<Organizer> query = em.createQuery("SELECT c FROM Organizer c WHERE c.user.email = :email",
				Organizer.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Organizer findByUserOrganizer(User user) {
		TypedQuery<Organizer> query = em.createQuery("SELECT c FROM Organizer c WHERE c.user = :user", Organizer.class);
		query.setParameter("user", user);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Organizer findOrganizerByUserId(Long userId) {
		TypedQuery<Organizer> query = em.createQuery("SELECT c FROM Organizer c WHERE c.user.id = :userId",
				Organizer.class);
		query.setParameter("userId", userId);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Organizer> findAllOrganizers() {
		TypedQuery<Organizer> query = em.createQuery("SELECT c FROM Organizer c", Organizer.class);
		return query.getResultList();
	}
	
	public void updateSubscription(Long organizerId, Subscription subscription) {
	    Organizer organizer = em.find(Organizer.class, organizerId);
	    if (organizer != null) {
	        if (subscription.getId() == null) {
	            em.persist(subscription);
	        }
	        organizer.setSubscription(subscription);
	        em.persist(organizer);
	        em.flush();
	    }
	}
	
	public Subscription getSubscriptionForOrganizer(Long organizerId) {
	    Organizer organizer = em.find(Organizer.class, organizerId);
	    return organizer != null ? organizer.getSubscription() : null;
	}

}
