package triphub.services;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import triphub.dao.user.*;
import triphub.entity.subscription.Customization;
import triphub.entity.subscription.Layout;
import triphub.entity.subscription.Subscription;
import triphub.entity.user.*;
import triphub.helpers.RegistrationException;
import triphub.viewModel.UserViewModel;

@Stateless
public class UserService implements Serializable{
	private static final long serialVersionUID = 1L;
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

	public User findByUserId(Long userId) {
		return userDAO.findByUserId(userId);
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

	public UserViewModel updateCustomer(UserViewModel form) {
		return customerDAO.updateCustomer(form);
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

	public List<Customer> getAllCustomers() {
		return customerDAO.findAllCustomers();
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

	public UserViewModel updateSuperAdmin(UserViewModel form) {
		return superAdminDAO.updateSuperAdmin(form);
	}

	public UserViewModel updateSuperAdminWithImage(UserViewModel userViewModel) throws RegistrationException {

		UserViewModel updatedUserViewModel = superAdminDAO.updateSuperAdmin(userViewModel);

		if (updatedUserViewModel != null) {
			return updatedUserViewModel;
		} else {
			throw new RegistrationException("Failed to update superAdmin with id " + userViewModel.getSuperAdminId());
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

	public List<SuperAdmin> getAllSuperAdmins() {
		return superAdminDAO.findAllSuperAdmins();
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

	public UserViewModel updateProviderWithImage(UserViewModel userViewModel) throws RegistrationException {

		UserViewModel updatedUserViewModel = providerDAO.updateProvider(userViewModel);

		if (updatedUserViewModel != null) {
			return updatedUserViewModel;
		} else {
			throw new RegistrationException("Failed to update provider with id " + userViewModel.getProviderId());
		}
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

	public List<Provider> getAllProviders() {
		return providerDAO.findAllProviders();
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

	public UserViewModel updateOrganizerWithImage(UserViewModel userViewModel) throws RegistrationException {

		UserViewModel updatedUserViewModel = organizerDAO.updateOrganizer(userViewModel);

		if (updatedUserViewModel != null) {
			return updatedUserViewModel;
		} else {
			throw new RegistrationException("Failed to update organizer with id " + userViewModel.getOrganizerId());
		}
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

	public List<Organizer> getAllOrganizers() {
		return organizerDAO.findAllOrganizers();
	}

	// Methods related to Organizer subscription
	public void updateSubscription(Long organizerId, Subscription subscription) {
		organizerDAO.updateSubscription(organizerId, subscription);
	}

	public Subscription getSubscription(Long organizerId) {
		return organizerDAO.getSubscriptionForOrganizer(organizerId);
	}

	// Methods related to Organizer customization
	public Customization getCustomizationForOrganizer(Long organizerId) {
		Organizer organizer = organizerDAO.readOrganizer(organizerId);
		if (organizer != null && organizer.getSubscription() != null) {
			return organizer.getSubscription().getCustomization();
		}
		return null;
	}

	public Layout getLayoutForOrganizer(Long organizerId) {
		Organizer organizer = organizerDAO.readOrganizer(organizerId);
		if (organizer != null && organizer.getSubscription() != null
				&& organizer.getSubscription().getCustomization() != null) {
			return organizer.getSubscription().getCustomization().getLayout();
		}
		return null;
	}

	public UserViewModel updateGraphicSettings(UserViewModel userViewModel) {
		return organizerDAO.updateGraphicSettings(userViewModel);
	}

}
