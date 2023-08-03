package triphub.dao.user;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.user.Customer;
import triphub.entity.user.User;
import triphub.entity.util.Address;
import triphub.entity.util.FinanceInfo;
import triphub.helpers.PasswordUtils;
import triphub.helpers.RegistrationException;
import triphub.viewModel.UserViewModel;

@Stateless
public class CustomerDAO {

	@PersistenceContext
	private EntityManager em;

	public CustomerDAO() {
	}

	public Customer createCustomer(UserViewModel form) {
		// Create user
		User user = new User();
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setEmail(form.getEmail());
		user.setPhoneNum(form.getPhoneNum());
		user.setPassword(PasswordUtils.getInstance().hashPassword(form.getPassword()));
		// Create address
		Address address = new Address();
		address.setNum(form.getNum());
		address.setStreet(form.getStreet());
		address.setCity(form.getCity());
		address.setState(form.getState());
		address.setCountry(form.getCountry());
		address.setZipCode(form.getZipCode());
		user.setAddress(address);
		// Create finance info
		FinanceInfo finance = new FinanceInfo();
		finance.setCCNumber(form.getCCNumber());
		finance.setExpirationDate(form.getExpirationDate());
		user.setFinance(finance);
		// Create customer
		Customer customer = new Customer();
		customer.setUser(user);
		
		em.persist(finance);
		em.persist(address);
		em.persist(user);
		em.persist(customer);
		em.flush();
		return customer;
	}
	
	public Customer readCustomer(Long id) {
	    return em.find(Customer.class, id);
	}

	public void deleteCustomer(Long id) {
	    Customer customer = em.find(Customer.class, id);
	    if (customer != null) {
	        em.remove(customer);
	    }
	}

	public void updateCustomer(Long id, Customer updatedCustomer) {
	    Customer customer = em.find(Customer.class, id);
	    if (customer != null) {
	        // Here update the fields of the customer object
	        // For example:
	        // customer.setFieldName(updatedCustomer.getFieldName());
	        em.merge(customer);
	    }
	}

	public Customer findByEmailCustomer(String email) throws RegistrationException {
	    TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.user.email = :email", Customer.class);
	    query.setParameter("email", email);
	    try {
	    	return query.getSingleResult();
	    } catch (NoResultException e) {
	        throw new RegistrationException("Customer with email " + email + " not found.");
	    }
	}

	public Customer findByUserCustomer(User user) {
	    TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.user = :user", Customer.class);
	    query.setParameter("user", user);
	    try {
	    	return query.getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}
}
