package triphub.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import triphub.entity.user.Provider;

public class ProviderDAO {
	private EntityManager em;

	public ProviderDAO(EntityManager em) {
		this.em = em;
	}

	public Provider create(Provider provider) {
		em.persist(provider);
		return provider;
	}

	public Provider read(Long id) {
		return em.find(Provider.class, id);
	}

	public Provider findByEmail(String email) {
		TypedQuery<Provider> query = em.createQuery("SELECT c FROM Provider c WHERE c.user.email = :email",
				Provider.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
