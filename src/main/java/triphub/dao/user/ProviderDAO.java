package triphub.dao.user;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.user.*;
import triphub.entity.util.*;
import triphub.viewModel.UserViewModel;

@Stateless
public class ProviderDAO {

	@PersistenceContext
	private EntityManager em;

	public ProviderDAO() {
	}

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

	public UserViewModel initProvider(Long providerId) {
		Provider provider = em.find(Provider.class, providerId);
		if (provider == null) {
			return null;
		}

		return provider.initProviderViewModel();
	}

	public Provider readProvider(Long id) {
		return em.find(Provider.class, id);
	}

	public void deleteProvider(Long id) {
		Provider provider = em.find(Provider.class, id);
		if (provider != null) {
			em.remove(provider);
		}
	}

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

	public Provider findByUserProvider(User user) {
		TypedQuery<Provider> query = em.createQuery("SELECT c FROM Provider c WHERE c.user = :user", Provider.class);
		query.setParameter("user", user);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

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

}
