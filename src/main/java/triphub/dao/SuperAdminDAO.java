package triphub.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import triphub.entity.user.Customer;
import triphub.entity.user.SuperAdmin;
import triphub.entity.user.User;
import triphub.helpers.RegistrationException;

public class SuperAdminDAO {
	private EntityManager em;

	public SuperAdminDAO(EntityManager em) {
		this.em = em;
	}

	public SuperAdmin create(SuperAdmin superAdmin) {
		em.persist(superAdmin);
		return superAdmin;
	}

	public SuperAdmin read(Long id) {
		return em.find(SuperAdmin.class, id);
	}

    public SuperAdmin findByEmail(String email) throws RegistrationException {
        TypedQuery<SuperAdmin> query = em.createQuery("SELECT c FROM SuperAdmin c WHERE c.user.email = :email", SuperAdmin.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new RegistrationException("SuperAdmin with email " + email + " not found.");
        }
    }
	
	public SuperAdmin findByUser(User user) {
	    try {
	        TypedQuery<SuperAdmin> query = em.createQuery("SELECT c FROM SuperAdmin c WHERE c.user = :user", SuperAdmin.class);
	        query.setParameter("user", user);
	        return query.getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}
}
