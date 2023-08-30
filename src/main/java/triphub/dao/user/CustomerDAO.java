package triphub.dao.user;

import java.util.List;

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

/**
 * Data Access Object (DAO) for the Customer entity.
 */
@Stateless
public class CustomerDAO {

	@PersistenceContext
	private EntityManager em;

	public CustomerDAO() {
	}

	/**
	 * Creates a new customer from the given UserViewModel.
	 *
	 * @param form The UserViewModel containing the customer's data.
	 * @return The created customer.
	 */
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

	/**
	 * Updates an existing customer from the given UserViewModel.
	 *
	 * @param userViewModel The UserViewModel containing the updated data.
	 * @return The updated UserViewModel.
	 */
	public UserViewModel updateCustomer(UserViewModel userViewModel) {
		Customer customer = em.find(Customer.class, userViewModel.getCustomerId());

		if (customer == null) {
			return null;
		}

		customer.updateCustomerFromViewModel(userViewModel);

		em.persist(customer);
		em.flush();

		return userViewModel;
	}

	/**
	 * Initializes a UserViewModel for a given customer ID.
	 *
	 * @param customerId The ID of the customer.
	 * @return The initialized UserViewModel or null if not found.
	 */
	public UserViewModel initCustomer(Long customerId) {
		Customer customer = em.find(Customer.class, customerId);
		if (customer == null) {
			return null;
		}

		return customer.initCustomerViewModel();
	}

	/**
	 * Retrieves a customer by ID.
	 *
	 * @param id The ID of the customer.
	 * @return The customer or null if not found.
	 */
	public Customer readCustomer(Long id) {
		return em.find(Customer.class, id);
	}

	/**
	 * Deletes a customer by ID.
	 *
	 * @param id The ID of the customer to delete.
	 */
	public void deleteCustomer(Long id) {
		Customer customer = em.find(Customer.class, id);
		if (customer != null) {
			em.remove(customer);
		}
	}

	/**
	 * Finds a customer by their email.
	 *
	 * @param email The email of the customer.
	 * @return The customer or null if not found.
	 */
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

	/**
	 * Finds a customer by their user entity.
	 *
	 * @param user The user entity associated with the customer.
	 * @return The customer or null if not found.
	 */
	public Customer findByUserCustomer(User user) {
		TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.user = :user", Customer.class);
		query.setParameter("user", user);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Finds a customer by their user ID.
	 *
	 * @param userId The ID of the user associated with the customer.
	 * @return The customer or null if not found.
	 */
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

	/**
	 * Retrieves all customers.
	 *
	 * @return A list of all customers.
	 */
	public List<Customer> findAllCustomers() {
		TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c", Customer.class);
		return query.getResultList();
	}

}
