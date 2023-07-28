

package triphub.dao;

import javax.persistence.EntityManager;
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
}

