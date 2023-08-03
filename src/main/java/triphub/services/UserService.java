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

	// Methods related to customers
	public Customer createCustomer(UserViewModel customerForm) throws RegistrationException {

		Customer email = null;
		try {
			email = customerDAO.findByEmailCustomer(customerForm.getEmail());
		} catch (RegistrationException e) {
			System.out.println("Customer with email " + customerForm.getEmail() + " not found.");
		}

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

//	public void updateCustomer(Long id) {
//		customerDAO.updateCustomer(id);
//	}

	public Customer findByEmailCustomer(String email) throws RegistrationException {
		return customerDAO.findByEmailCustomer(email);
	}

	public Customer findByUserCustomer(User user) {
		return customerDAO.findByUserCustomer(user);
	}

	// Methods related to SuperAdmin
	public SuperAdmin createSuperAdmin(UserViewModel superAdminForm) throws RegistrationException {

		SuperAdmin email = null;
		try {
			email = superAdminDAO.findByEmailSuperAdmin(superAdminForm.getEmail());
		} catch (RegistrationException e) {
			System.out.println("SuperAdmin with email " + superAdminForm.getEmail() + " not found.");
		}

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

	public void updateSuperAdmin(Long id) {
		superAdminDAO.updateSuperAdmin(id);
	}

	public SuperAdmin findByEmailSuperAdmin(String email) throws RegistrationException {
		return superAdminDAO.findByEmailSuperAdmin(email);
	}

	public SuperAdmin findByUserSuperAdmin(User user) {
		return superAdminDAO.findByUserSuperAdmin(user);
	}

	// Methods related to Provider
	public Provider createProvider(UserViewModel providerForm) throws RegistrationException {

		Provider email = null;
		try {
			email = providerDAO.findByEmailProvider(providerForm.getEmail());
		} catch (RegistrationException e) {
			System.out.println("Provider with email " + providerForm.getEmail() + " not found.");
		}

		if (email != null) {
			throw new RegistrationException("This email is already used");
		}

		return providerDAO.createProvider(providerForm);
	}

	public Provider readProvider(Long id) {
		return providerDAO.readProvider(id);
	}

	public void deleteProvider(Long id) {
		providerDAO.deleteProvider(id);
	}

	public void updateProvider(Long id) {
		providerDAO.updateProvider(id);
	}

	public Provider findByEmailProvider(String email) throws RegistrationException {
		return providerDAO.findByEmailProvider(email);
	}

	public Provider findByUserProvider(User user) {
		return providerDAO.findByUserProvider(user);
	}

	// Methods related to Organizer
	public Organizer createOrganizer(UserViewModel organizerForm) throws RegistrationException {

		Organizer email = null;
		try {
			email = organizerDAO.findByEmailOrganizer(organizerForm.getEmail());
		} catch (RegistrationException e) {
			System.out.println("Organizer with email " + organizerForm.getEmail() + " not found.");
		}

		if (email != null) {
			throw new RegistrationException("This email is already used");
		}

		return organizerDAO.createOrganizer(organizerForm);
	}

	public Organizer readOrganizer(Long id) {
		return organizerDAO.readOrganizer(id);
	}

	public void deleteOrganizer(Long id) {
		organizerDAO.deleteOrganizer(id);
	}

	public void updateOrganizer(Long id) {
		organizerDAO.updateOrganizer(id);
	}

	public Organizer findByEmailOrganizer(String email) throws RegistrationException {
		return organizerDAO.findByEmailOrganizer(email);
	}

	public Organizer findByUserOrganizer(User user) {
		return organizerDAO.findByUserOrganizer(user);
	}

}
