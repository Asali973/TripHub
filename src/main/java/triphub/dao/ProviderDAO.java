package triphub.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import triphub.entity.user.Customer;
import triphub.entity.user.Provider;
import triphub.entity.user.User;

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
	
	public Provider findByUser(User user) {
	    try {
	        TypedQuery<Provider> query = em.createQuery("SELECT c FROM Provider c WHERE c.user = :user", Provider.class);
	        query.setParameter("user", user);
	        return query.getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}
}
