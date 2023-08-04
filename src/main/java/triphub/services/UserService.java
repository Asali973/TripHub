package triphub.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import triphub.dao.user.*;
import triphub.entity.user.*;
import triphub.helpers.RegistrationException;
import triphub.viewModel.UserViewModel;

@Stateless
public class UserService {

	@Inject
	private CustomerDAO customerDAO;

	@Inject
	private SuperAdminDAO superAdminDAO;

	@Inject
	private ProviderDAO providerDAO;

	@Inject
	private OrganizerDAO organizerDAO;

	@Inject
	private UserDAO userDAO;

//    public UserService(CustomerDAO customerDAO) {
//        this.customerDAO = customerDAO;
//    }

	public UserService() {
	}

	// Methods related to users
	public User createUser(User user) {
		return userDAO.createUser(user);
	}

	public User readUser(Long id) {
		return userDAO.readUser(id);
	}

	public User findByEmailUser(String email) {
		return userDAO.findByEmailUser(email);
	}

	public UserViewModel initUser(Long userId) {
		return userDAO.initUser(userId);
	}

	// Methods related to customers
	public Customer createCustomer(UserViewModel customerForm) throws RegistrationException {

		Customer email = customerDAO.findByEmailCustomer(customerForm.getEmail());

		if (email != null) {
			throw new RegistrationException("This email is already used");
		}

		return customerDAO.createCustomer(customerForm);
	}

	public Customer readCustomer(Long id) {
		return customerDAO.readCustomer(id);
	}

	public void deleteCustomer(Long id) {
		customerDAO.deleteCustomer(id);
	}

	public UserViewModel updateCustomerWithImage(UserViewModel userViewModel) throws RegistrationException {

		UserViewModel updatedUserViewModel = customerDAO.updateCustomer(userViewModel);

		if (updatedUserViewModel != null) {
			return updatedUserViewModel;
		} else {
			throw new RegistrationException("Failed to update customer with id " + userViewModel.getCustomerId());
		}
	}

	public Customer findByEmailCustomer(String email) throws RegistrationException {
		return customerDAO.findByEmailCustomer(email);
	}

	public Customer findByUserCustomer(User user) {
		return customerDAO.findByUserCustomer(user);
	}

	public UserViewModel initCustomer(Long customerId) {
		return customerDAO.initCustomer(customerId);
	}

	// Methods related to SuperAdmin
	public SuperAdmin createSuperAdmin(UserViewModel superAdminForm) throws RegistrationException {

		SuperAdmin email = superAdminDAO.findByEmailSuperAdmin(superAdminForm.getEmail());

		if (email != null) {
			throw new RegistrationException("This email is already used");
		}

		return superAdminDAO.createSuperAdmin(superAdminForm);
	}

	public SuperAdmin readSuperAdmin(Long id) {
		return superAdminDAO.readSuperAdmin(id);
	}

	public void deleteSuperAdmin(Long id) {
		superAdminDAO.deleteSuperAdmin(id);
	}

	public UserViewModel updateSuperAdmin(UserViewModel userViewModel) throws RegistrationException {

		UserViewModel updatedUserViewModel = superAdminDAO.updateSuperAdmin(userViewModel);

		if (updatedUserViewModel != null) {
			return updatedUserViewModel;
		} else {
			throw new RegistrationException("Failed to update SuperAdmin with id " + userViewModel.getSuperAdminId());
		}
	}

	public SuperAdmin findByEmailSuperAdmin(String email) throws RegistrationException {
		return superAdminDAO.findByEmailSuperAdmin(email);
	}

	public SuperAdmin findByUserSuperAdmin(User user) {
		return superAdminDAO.findByUserSuperAdmin(user);
	}

	public UserViewModel initSuperAdmin(Long superAdminId) {
		return superAdminDAO.initSuperAdmin(superAdminId);
	}

	// Methods related to Provider
	public Provider createProvider(UserViewModel providerForm) throws RegistrationException {
		Provider provider = providerDAO.findByEmailProvider(providerForm.getEmail());

		if (provider != null) {
			throw new RegistrationException("This email is already used");
		}

		return providerDAO.createProvider(providerForm);
	}

	public UserViewModel updateProvider(UserViewModel form) {
		return providerDAO.updateProvider(form);
	}

	public UserViewModel initProvider(Long id) {
		return providerDAO.initProvider(id);
	}

	public Provider readProvider(Long id) {
		return providerDAO.readProvider(id);
	}

	public void deleteProvider(Long id) {
		providerDAO.deleteProvider(id);
	}

	public Provider findByEmailProvider(String email) {
		return providerDAO.findByEmailProvider(email);
	}

	public Provider findByUserProvider(User user) {
		return providerDAO.findByUserProvider(user);
	}

	public Provider findProviderByUserId(Long userId) {
		return providerDAO.findProviderByUserId(userId);
	}

	// Methods related to Organizer
	public Organizer createOrganizer(UserViewModel organizerForm) throws RegistrationException {
		Organizer organizer = organizerDAO.findByEmailOrganizer(organizerForm.getEmail());

		if (organizer != null) {
			throw new RegistrationException("This email is already used");
		}

		return organizerDAO.createOrganizer(organizerForm);
	}

	public UserViewModel updateOrganizer(UserViewModel userViewModel) {
		return organizerDAO.updateOrganizer(userViewModel);
	}

	public UserViewModel initOrganizer(Long organizerId) {
		return organizerDAO.initOrganizer(organizerId);
	}

	public Organizer readOrganizer(Long id) {
		return organizerDAO.readOrganizer(id);
	}

	public void deleteOrganizer(Long id) {
		organizerDAO.deleteOrganizer(id);
	}

	public Organizer findByEmailOrganizer(String email) throws RegistrationException {
		return organizerDAO.findByEmailOrganizer(email);
	}

	public Organizer findByUserOrganizer(User user) {
		return organizerDAO.findByUserOrganizer(user);
	}

	public Organizer findOrganizerByUserId(Long userId) {
		return organizerDAO.findOrganizerByUserId(userId);
	}

}
