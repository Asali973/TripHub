package triphub.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import triphub.entity.user.User;

public class UserDAO {
	private EntityManager em;

	public UserDAO(EntityManager em) {
		this.em = em;
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
