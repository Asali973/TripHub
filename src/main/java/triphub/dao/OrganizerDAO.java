package triphub.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import triphub.entity.user.Organizer;
import triphub.entity.user.Organizer;
import triphub.entity.user.User;
import triphub.helpers.RegistrationException;

public class OrganizerDAO {
    private EntityManager em;

    public OrganizerDAO(EntityManager em) {
        this.em = em;
    }

    public Organizer create(Organizer organizer) {
        em.persist(organizer);
        return organizer;
    }

    public Organizer read(Long id) {
        return em.find(Organizer.class, id);
    }
    
    public Organizer findByEmail(String email) throws RegistrationException {
        TypedQuery<Organizer> query = em.createQuery("SELECT c FROM Organizer c WHERE c.user.email = :email", Organizer.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new RegistrationException("Organizer with email " + email + " not found.");
        }
    }
	
	public Organizer findByUser(User user) {
	    try {
	        TypedQuery<Organizer> query = em.createQuery("SELECT c FROM Organizer c WHERE c.user = :user", Organizer.class);
	        query.setParameter("user", user);
	        return query.getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}
}
