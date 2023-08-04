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
import triphub.entity.util.Picture;
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

		User user = User.createUserFromViewModel(form);
		Address address = Address.createAddressFromViewModel(form);
		user.setAddress(address);

		FinanceInfo finance = FinanceInfo.createFinanceInfoFromViewModel(form);
		user.setFinance(finance);

		// Create profile picture
		Picture pictureProfile = new Picture();
		pictureProfile.setLink(form.getProfilePicture());

		// Create customer
		Customer customer = new Customer();
		customer.setId(form.getCustomerId());
		customer.setUser(user);
		customer.setPicture(pictureProfile);

		em.persist(pictureProfile);
		em.persist(finance);
		em.persist(address);
		em.persist(user);
		em.persist(customer);
		em.flush();
		return customer;
	}

	public UserViewModel updateCustomer(UserViewModel userViewModel) {
		// Find existing customer
		Customer customer = em.find(Customer.class, userViewModel.getCustomerId());

		if (customer == null) {
			return null;
		}

		// Update customer
		customer.updateCustomerFromViewModel(userViewModel);

		// Persist changes
		em.persist(customer);
		em.flush();

		return userViewModel;
	}

	public UserViewModel initCustomer(Long customerId) {
		Customer customer = em.find(Customer.class, customerId);
		if (customer == null) {
			return null;
		}

		return customer.initCustomerViewModel();
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

	public Customer findByEmailCustomer(String email) {
		TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.user.email = :email",
				Customer.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
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

	public Customer findCustomerByUserId(Long userId) {
		TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.user.id = :userId",
				Customer.class);
		query.setParameter("userId", userId);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
