package triphub.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.user.Customer;
import triphub.entity.user.User;
import triphub.helpers.RegistrationException;

@Stateless
public class CustomerDAO {

	@PersistenceContext
	private EntityManager em;

	
	public CustomerDAO(EntityManager em) {
		this.em = em;
	}

	public Customer create(Customer customer) {
		em.persist(customer);
		return customer;
	}

	public Customer read(Long id) {
		return em.find(Customer.class, id);
	}

    public Customer findByEmail(String email) throws RegistrationException {
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.user.email = :email", Customer.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new RegistrationException("Customer with email " + email + " not found.");
        }
    }
	
	public Customer findByUser(User user) {
	    try {
	        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.user = :user", Customer.class);
	        query.setParameter("user", user);
	        return query.getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}

	
	
}
