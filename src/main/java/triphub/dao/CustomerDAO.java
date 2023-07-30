package triphub.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import triphub.entity.user.Customer;

public class CustomerDAO {
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

	public Customer findByEmail(String email) {
		TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.user.email = :email",
				Customer.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
