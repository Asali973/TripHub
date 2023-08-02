package triphub.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import triphub.entity.user.User;

@Stateless
public class UserDAO {
	@PersistenceContext
	private EntityManager em;

	public UserDAO(EntityManager em) {
		this.em = em;
	}
	
	public UserDAO() {
	}

	public User createUser(User user) {
		em.persist(user);
		return user;
	}

	public User readUser(Long id) {
		return em.find(User.class, id);
	}

	public User findByEmailUser(String email) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
		query.setParameter("email", email);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
