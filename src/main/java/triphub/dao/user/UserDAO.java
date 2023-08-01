package triphub.dao.user;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import triphub.entity.user.User;

@Stateless
public class UserDAO {
	@PersistenceUnit
	private EntityManager em;

	public UserDAO(EntityManager em) {
		this.em = em;
	}

	public User create(User user) {
		em.persist(user);
		return user;
	}

	public User read(Long id) {
		return em.find(User.class, id);
	}
	
    public void delete(Long id) {
        User user = read(id);
        if (user != null) {
            em.remove(user);
        }
    }

	public User findByEmail(String email) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
		query.setParameter("email", email);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
