package triphub.dao.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.user.*;
import triphub.entity.util.*;
import triphub.viewModel.UserViewModel;

/**
 * DAO class responsible for operations related to the Provider entity.
 */
@Stateless
public class ProviderDAO {

	@PersistenceContext
	private EntityManager em;

	public ProviderDAO() {
	}

	/**
	 * Creates a Provider entity based on the given view model.
	 *
	 * @param form the UserViewModel form.
	 * @return the created Provider entity.
	 */
	public Provider createProvider(UserViewModel form) {

		User user = User.createUserFromViewModel(form);
		Address address = Address.createAddressFromViewModel(form);
		user.setAddress(address);

		FinanceInfo finance = FinanceInfo.createFinanceInfoFromViewModel(form);
		user.setFinance(finance);

		CompanyInfo companyInfo = CompanyInfo.createCompanyInfoFromViewModel(form);
		Administration administration = Administration.createAdministrationFromViewModel(form);

		Provider provider = new Provider();
		provider.setUser(user);
		provider.setId(form.getProviderId());
		provider.setCompanyInfo(companyInfo);
		provider.setAdministration(administration);

		em.persist(companyInfo);
		em.persist(administration);
		em.persist(finance);
		em.persist(address);
		em.persist(user);
		em.persist(provider);
		em.flush();
		return provider;
	}

	/**
	 * Updates the Provider entity based on the given userViewModel.
	 *
	 * @param userViewModel the form for updating.
	 * @return the updated UserViewModel.
	 */
	public UserViewModel updateProvider(UserViewModel userViewModel) {
		Provider provider = em.find(Provider.class, userViewModel.getProviderId());

		if (provider == null) {
			return null;
		}

		provider.updateProviderFromViewModel(userViewModel);

		em.persist(provider);
		em.flush();

		return userViewModel;
	}

	/**
	 * Initializes a Provider from its ID.
	 *
	 * @param providerId the ID of the Provider.
	 * @return the initialized UserViewModel.
	 */
	public UserViewModel initProvider(Long providerId) {
		Provider provider = em.find(Provider.class, providerId);
		if (provider == null) {
			return null;
		}

		return provider.initProviderViewModel();
	}

	/**
	 * Retrieves a Provider entity by its ID.
	 *
	 * @param id the ID of the Provider.
	 * @return the Provider entity.
	 */
	public Provider readProvider(Long id) {
		return em.find(Provider.class, id);
	}

	/**
	 * Deletes a Provider entity by its ID.
	 *
	 * @param id the ID of the Provider.
	 */
	public void deleteProvider(Long id) {
		Provider provider = em.find(Provider.class, id);
		if (provider != null) {
			em.remove(provider);
		}
	}

	/**
	 * Finds a Provider based on its email.
	 *
	 * @param email the email of the Provider.
	 * @return the matching Provider, or null if not found.
	 */
	public Provider findByEmailProvider(String email) {
		TypedQuery<Provider> query = em.createQuery("SELECT c FROM Provider c WHERE c.user.email = :email",
				Provider.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Finds a Provider based on the associated User entity.
	 *
	 * @param user the associated User entity.
	 * @return the matching Provider, or null if not found.
	 */
	public Provider findByUserProvider(User user) {
		TypedQuery<Provider> query = em.createQuery("SELECT c FROM Provider c WHERE c.user = :user", Provider.class);
		query.setParameter("user", user);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Finds a Provider based on the ID of its associated User entity.
	 *
	 * @param userId the ID of the associated User entity.
	 * @return the matching Provider, or null if not found.
	 */
	public Provider findProviderByUserId(Long userId) {
		TypedQuery<Provider> query = em.createQuery("SELECT c FROM Provider c WHERE c.user.id = :userId",
				Provider.class);
		query.setParameter("userId", userId);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Retrieves all Providers.
	 *
	 * @return a list of all Providers.
	 */
	public List<Provider> findAllProviders() {
		TypedQuery<Provider> query = em.createQuery("SELECT c FROM Provider c", Provider.class);
		return query.getResultList();
	}

}
