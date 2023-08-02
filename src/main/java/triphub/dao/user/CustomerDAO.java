package triphub.dao.user;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
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

	@PersistenceUnit
	private EntityManager em;

	public CustomerDAO() {
	}

	public CustomerDAO(EntityManager em) {
		this.em = em;
	}

	public Customer create(UserViewModel form) {

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
		return customer;
	}

	public Customer read(Long id) {
		return em.find(Customer.class, id);
	}
	
    public void delete(Long id) {
        Customer customer = read(id);
        if (customer != null) {
            em.remove(customer);
        }
    }
    
    public void update(Long id) {
        Customer customer = read(id);
        if (customer != null) {
            em.merge(customer);
        }
    }

	public Customer findByEmail(String email) throws RegistrationException {
		TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.user.email = :email",
				Customer.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new RegistrationException("Customer with email " + email + " not found.");
		}
	}

	public Customer findByUser(User user) {
		try {
			TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.user = :user",
					Customer.class);
			query.setParameter("user", user);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	
}