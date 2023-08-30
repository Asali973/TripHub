package triphub.dao.user;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.TourPackage;
import triphub.entity.subscription.Customization;
import triphub.entity.subscription.Subscription;
import triphub.entity.subscription.SubscriptionType;
import triphub.entity.user.*;
import triphub.entity.util.*;
import triphub.viewModel.UserViewModel;

/**
 * Data Access Object (DAO) for handling database operations related to the
 * Organizer entity.
 */
@Stateless
public class OrganizerDAO {

	@PersistenceContext
	private EntityManager em;

	public OrganizerDAO() {
	}

	/**
	 * Creates a new Organizer from the given UserViewModel.
	 *
	 * @param form the UserViewModel with the necessary information to create a new
	 *             Organizer.
	 * @return the created Organizer.
	 */
	public Organizer createOrganizer(UserViewModel form) {

		User user = User.createUserFromViewModel(form);
		Address address = Address.createAddressFromViewModel(form);
		user.setAddress(address);

		FinanceInfo finance = FinanceInfo.createFinanceInfoFromViewModel(form);
		user.setFinance(finance);

		CompanyInfo companyInfo = CompanyInfo.createCompanyInfoFromViewModel(form);
		Administration administration = Administration.createAdministrationFromViewModel(form);

		Subscription defaultSubscription = new Subscription();
		defaultSubscription.setType(SubscriptionType.STANDARD);
		em.persist(defaultSubscription);

		Organizer organizer = new Organizer();
		organizer.setUser(user);
		organizer.setId(form.getOrganizerId());
		organizer.setCompanyInfo(companyInfo);
		organizer.setAdministration(administration);
		organizer.setSubscription(defaultSubscription);

		em.persist(companyInfo);
		em.persist(administration);
		em.persist(finance);
		em.persist(address);
		em.persist(user);
		em.persist(organizer);
		em.flush();
		return organizer;
	}

	/**
	 * Updates the graphic settings for the Organizer identified by the
	 * UserViewModel.
	 *
	 * @param userViewModel the UserViewModel with the necessary information to
	 *                      update graphic settings.
	 * @return updated UserViewModel.
	 */
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

		if (userViewModel.getLayout() != null) {
			customization.setLayout(userViewModel.getLayout());
			userViewModel.setXhtmlFile(userViewModel.getLayout().getXhtmlFile());
			System.out.println("xhtmlFile value: " + userViewModel.getXhtmlFile());
		}

		if (customization.getId() != null) {
			em.merge(customization);
		}

		em.merge(subscription);
		em.merge(organizer);

		System.out.println("Final xhtmlFile value before returning: " + userViewModel.getXhtmlFile());

		return userViewModel;
	}

	/**
	 * Updates the Organizer details from the provided UserViewModel.
	 *
	 * @param userViewModel the UserViewModel with the necessary information to
	 *                      update the Organizer.
	 * @return updated UserViewModel.
	 */
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

	/**
	 * Initializes an Organizer ViewModel from the given Organizer ID.
	 *
	 * @param organizerId the ID of the Organizer.
	 * @return the initialized UserViewModel.
	 */
	public UserViewModel initOrganizer(Long organizerId) {
		Organizer organizer = em.find(Organizer.class, organizerId);
		if (organizer == null) {
			return null;
		}

		return organizer.initOrganizerViewModel();
	}

	/**
	 * Retrieves an Organizer from the database using the provided ID.
	 *
	 * @param id the ID of the Organizer to be retrieved.
	 * @return the Organizer found or null if not found.
	 */
	public Organizer readOrganizer(Long id) {
		return em.find(Organizer.class, id);
	}

	/**
	 * Deletes an Organizer from the database using the provided ID.
	 *
	 * @param id the ID of the Organizer to be deleted.
	 */
	public void deleteOrganizer(Long id) {
		Organizer organizer = em.find(Organizer.class, id);
		if (organizer != null) {
			em.remove(organizer);
		}
	}

	/**
	 * Finds an Organizer by its email.
	 *
	 * @param email Email of the Organizer to search for.
	 * @return Organizer with the provided email or null if not found.
	 */
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

	/**
	 * Finds an Organizer by its associated User entity.
	 *
	 * @param user User entity associated with the Organizer to search for.
	 * @return Organizer associated with the given User or null if not found.
	 */
	public Organizer findByUserOrganizer(User user) {
		TypedQuery<Organizer> query = em.createQuery("SELECT c FROM Organizer c WHERE c.user = :user", Organizer.class);
		query.setParameter("user", user);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Finds an Organizer by the ID of its associated User entity.
	 *
	 * @param userId ID of the User entity associated with the Organizer to search
	 *               for.
	 * @return Organizer associated with the given User ID or null if not found.
	 */
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

	/**
	 * Retrieves all Organizers.
	 *
	 * @return List of all Organizers.
	 */
	public List<Organizer> findAllOrganizers() {
		TypedQuery<Organizer> query = em.createQuery("SELECT c FROM Organizer c", Organizer.class);
		return query.getResultList();
	}

	/**
	 * Updates the Subscription of an Organizer.
	 *
	 * @param organizerId  ID of the Organizer to update.
	 * @param subscription New Subscription for the Organizer.
	 */
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

	/**
	 * Finds Organizers based on company name or country.
	 *
	 * @param companyName Name of the company.
	 * @param country     Country of the Organizer.
	 * @return List of Organizers matching the criteria.
	 */
	public List<Organizer> findOrganizerByCompanyOrCountry(String companyName, String country) {
		TypedQuery<Organizer> query = em.createQuery(
				"SELECT c FROM Organizer c WHERE c.companyInfo.name = :companyName OR c.user.address.country = :country",
				Organizer.class);

		query.setParameter("companyName", companyName);
		query.setParameter("country", country);

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<>();
		}
	}

	/**
	 * Retrieves the Subscription associated with a given Organizer ID.
	 *
	 * @param organizerId ID of the Organizer.
	 * @return Subscription associated with the Organizer or null if not found.
	 */
	public Subscription getSubscriptionForOrganizer(Long organizerId) {
		Organizer organizer = em.find(Organizer.class, organizerId);
		return organizer != null ? organizer.getSubscription() : null;
	}

	/**
	 * Retrieves the xhtml file (template) associated with the Organizer by its ID.
	 *
	 * @param organizerId the ID of the Organizer.
	 * @return the xhtml file path of the template or null if not found.
	 */
	public String getXhtmlFileByOrganizerId(Long organizerId) {
		Organizer organizer = em.find(Organizer.class, organizerId);
		if (organizer != null && organizer.getSubscription() != null
				&& organizer.getSubscription().getCustomization() != null
				&& organizer.getSubscription().getCustomization().getLayout() != null) {
			return organizer.getSubscription().getCustomization().getLayout().getXhtmlFile();
		}
		return null;
	}

}
