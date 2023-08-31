package triphub.services;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import triphub.dao.user.*;
import triphub.entity.product.TourPackage;
import triphub.entity.subscription.Customization;
import triphub.entity.subscription.Layout;
import triphub.entity.subscription.Subscription;
import triphub.entity.user.*;
import triphub.helpers.RegistrationException;
import triphub.viewModel.UserViewModel;
/**
 * This class represents a service responsible for managing different types of users, including Customers, SuperAdmins,
 * Providers, and Organizers. It provides methods for creating, reading, updating, and deleting user entities.
 * Additionally, it offers methods to perform various operations specific to each user type, such as managing Customers,
 * SuperAdmins, Providers, and Organizers.
 * <p>
 * The class is stateless and is intended to be used within a Java EE context.
 * </p>
 * <p>
 * This class assumes that it is injected with instances of various DAOs (Data Access Objects) for interacting with
 * different user-related entities.
 * </p>
 * <p>
 * Note: Due to the extensive nature of the class, only method summaries are provided here. Refer to individual method
 * documentation for more details.
 * </p>
 * 
 */
@Stateless
public class UserService implements Serializable {
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

	 /**
     * Creates a new User entity.
     *
     * @param user The User entity to create.
     * @return The created User entity.
     */	
	public User createUser(User user) {
		return userDAO.createUser(user);
	}

	/**
     * Reads a User entity by its ID.
     *
     * @param id The ID of the User entity.
     * @return The retrieved User entity, or null if not found.
     */
	public User readUser(Long id) {
		return userDAO.readUser(id);
	}

	/**
     * Finds and returns a User entity by its email.
     *
     * @param email The email associated with the user.
     * @return The retrieved User entity, or null if not found.
     */
	public User findByEmailUser(String email) {
		return userDAO.findByEmailUser(email);
	}

	/**
     * Initializes and returns a ViewModel for a specific User entity.
     *
     * @param userId The ID of the user for which to create the ViewModel.
     * @return The initialized ViewModel, or null if the user entity is not found.
     */
	public UserViewModel initUser(Long userId) {
		return userDAO.initUser(userId);
	}

	/**
     * Finds and returns a User entity by its ID.
     *
     * @param userId The ID of the User entity.
     * @return The retrieved User entity, or null if not found.
     */
	public User findByUserId(Long userId) {
		return userDAO.findByUserId(userId);
	}

	 /**
     * Performs an advanced search for User entities based on specified search criteria.
     *
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param city The city of the user's address.
     * @param street The street of the user's address.
     * @param zipCode The ZIP code of the user's address.
     * @param country The country of the user's address.
     * @param email The email of the user.
     * @param phoneNum The phone number of the user.
     * @param CCNumber The credit card number of the user.
     * @return A list of User entities matching the specified search criteria.
     */
	public List<User> advancedSearch(String firstName, String lastName, String city, String street,
			String zipCode, String country, String email, String phoneNum, String CCNumber) {
		return userDAO.advancedSearch(firstName, lastName, city, street, zipCode, country, email, phoneNum,
				CCNumber);
	}

	
	 /**
     * Creates a new Customer entity based on the provided ViewModel.
     *
     * @param customerForm The ViewModel containing customer details.
     * @return The created Customer entity.
     * @throws RegistrationException If registration fails due to duplicate email.
     */
	public Customer createCustomer(UserViewModel customerForm) throws RegistrationException {

		Customer email = customerDAO.findByEmailCustomer(customerForm.getEmail());

		if (email != null) {
			throw new RegistrationException("This email is already used");
		}

		return customerDAO.createCustomer(customerForm);
	}
	
	  /**
     * Reads a Customer entity by its ID.
     *
     * @param id The ID of the Customer entity.
     * @return The retrieved Customer entity, or null if not found.
     */
	public Customer readCustomer(Long id) {
		return customerDAO.readCustomer(id);
	}

	/**
     * Deletes a Customer entity by its ID.
     *
     * @param id The ID of the Customer entity to delete.
     */
	public void deleteCustomer(Long id) {
		customerDAO.deleteCustomer(id);
	}

	/**
     * Updates the details of a Customer entity based on the provided ViewModel.
     *
     * @param form The ViewModel containing updated customer details.
     * @return The ViewModel containing the updated customer details.
     */
	public UserViewModel updateCustomer(UserViewModel form) {
		return customerDAO.updateCustomer(form);
	}

	/**
     * Updates the details of a Customer entity including an image, based on the provided ViewModel.
     *
     * @param userViewModel The ViewModel containing updated customer details and image.
     * @return The ViewModel containing the updated customer details.
     * @throws RegistrationException If the update fails.
     */

	public UserViewModel updateCustomerWithImage(UserViewModel userViewModel) throws RegistrationException {

		UserViewModel updatedUserViewModel = customerDAO.updateCustomer(userViewModel);

		if (updatedUserViewModel != null) {
			return updatedUserViewModel;
		} else {
			throw new RegistrationException("Failed to update customer with id " + userViewModel.getCustomerId());
		}
	}

	 /**
     * Finds and returns a Customer entity by its email.
     *
     * @param email The email associated with the customer.
     * @return The retrieved Customer entity, or null if not found.
     * @throws RegistrationException If the email lookup fails.
     */
	public Customer findByEmailCustomer(String email) throws RegistrationException {
		return customerDAO.findByEmailCustomer(email);
	}

	/**
     * Finds and returns a Customer entity associated with the given User entity.
     *
     * @param user The User entity associated with the customer.
     * @return The retrieved Customer entity, or null if not found.
     */
    
	public Customer findByUserCustomer(User user) {
		return customerDAO.findByUserCustomer(user);
	}

	/**
     * Initializes and returns a ViewModel for a specific Customer entity.
     *
     * @param customerId The ID of the customer for which to create the ViewModel.
     * @return The initialized ViewModel, or null if the customer entity is not found.
     */

	public UserViewModel initCustomer(Long customerId) {
		return customerDAO.initCustomer(customerId);
	}

	/**
     * Retrieves a list of all Customer entities.
     *
     * @return A list containing all Customer entities.
     */
	public List<Customer> getAllCustomers() {
		return customerDAO.findAllCustomers();
	}


    /**
     * Creates a new SuperAdmin entity based on the provided ViewModel.
     *
     * @param superAdminForm The ViewModel containing super admin details.
     * @return The created SuperAdmin entity.
     * @throws RegistrationException If registration fails due to duplicate email.
     */
	public SuperAdmin createSuperAdmin(UserViewModel superAdminForm) throws RegistrationException {

		SuperAdmin email = superAdminDAO.findByEmailSuperAdmin(superAdminForm.getEmail());

		if (email != null) {
			throw new RegistrationException("This email is already used");
		}

		return superAdminDAO.createSuperAdmin(superAdminForm);
	}

	/**
     * Reads a SuperAdmin entity by its ID.
     *
     * @param id The ID of the SuperAdmin entity.
     * @return The retrieved SuperAdmin entity, or null if not found.
     */
	public SuperAdmin readSuperAdmin(Long id) {
		return superAdminDAO.readSuperAdmin(id);
	}

	 /**
     * Deletes a SuperAdmin entity by its ID.
     *
     * @param id The ID of the SuperAdmin entity to delete.
     */
	public void deleteSuperAdmin(Long id) {
		superAdminDAO.deleteSuperAdmin(id);
	}

	   /**
     * Updates the details of a SuperAdmin entity based on the provided ViewModel.
     *
     * @param form The ViewModel containing updated super admin details.
     * @return The ViewModel containing the updated super admin details.
     */
	public UserViewModel updateSuperAdmin(UserViewModel form) {
		return superAdminDAO.updateSuperAdmin(form);
	}

	/**
     * Updates the details of a SuperAdmin entity including an image, based on the provided ViewModel.
     *
     * @param userViewModel The ViewModel containing updated super admin details and image.
     * @return The ViewModel containing the updated super admin details.
     * @throws RegistrationException If the update fails.
     */
	public UserViewModel updateSuperAdminWithImage(UserViewModel userViewModel) throws RegistrationException {

		UserViewModel updatedUserViewModel = superAdminDAO.updateSuperAdmin(userViewModel);

		if (updatedUserViewModel != null) {
			return updatedUserViewModel;
		} else {
			throw new RegistrationException("Failed to update superAdmin with id " + userViewModel.getSuperAdminId());
		}
	}

	/**
     * Finds and returns a SuperAdmin entity by its email.
     *
     * @param email The email associated with the super admin.
     * @return The retrieved SuperAdmin entity, or null if not found.
     * @throws RegistrationException If the email lookup fails.
     */
	public SuperAdmin findByEmailSuperAdmin(String email) throws RegistrationException {
		return superAdminDAO.findByEmailSuperAdmin(email);
	}

	/**
     * Finds and returns a SuperAdmin entity associated with the given User entity.
     *
     * @param user The User entity associated with the super admin.
     * @return The retrieved SuperAdmin entity, or null if not found.
     */
	public SuperAdmin findByUserSuperAdmin(User user) {
		return superAdminDAO.findByUserSuperAdmin(user);
	}

	/**
     * Initializes and returns a ViewModel for a specific SuperAdmin entity.
     *
     * @param superAdminId The ID of the super admin for which to create the ViewModel.
     * @return The initialized ViewModel, or null if the super admin entity is not found.
     */
	public UserViewModel initSuperAdmin(Long superAdminId) {
		return superAdminDAO.initSuperAdmin(superAdminId);
	}

	/**
     * Retrieves a list of all SuperAdmin entities.
     *
     * @return A list containing all SuperAdmin entities.
     */
	public List<SuperAdmin> getAllSuperAdmins() {
		return superAdminDAO.findAllSuperAdmins();
	}

	 /**
     * Creates a new Provider entity based on the provided ViewModel.
     *
     * @param providerForm The ViewModel containing provider details.
     * @return The created Provider entity.
     * @throws RegistrationException If registration fails due to duplicate email.
     */
	public Provider createProvider(UserViewModel providerForm) throws RegistrationException {
		Provider provider = providerDAO.findByEmailProvider(providerForm.getEmail());

		if (provider != null) {
			throw new RegistrationException("This email is already used");
		}

		return providerDAO.createProvider(providerForm);
	}

	/**
     * Updates the details of a Provider entity based on the provided ViewModel.
     *
     * @param form The ViewModel containing updated provider details.
     * @return The ViewModel containing the updated provider details.
     */
	public UserViewModel updateProvider(UserViewModel form) {
		return providerDAO.updateProvider(form);
	}

	/**
     * Updates the details of a Provider entity including an image, based on the provided ViewModel.
     *
     * @param userViewModel The ViewModel containing updated provider details and image.
     * @return The ViewModel containing the updated provider details.
     * @throws RegistrationException If the update fails.
     */
	public UserViewModel updateProviderWithImage(UserViewModel userViewModel) throws RegistrationException {

		UserViewModel updatedUserViewModel = providerDAO.updateProvider(userViewModel);

		if (updatedUserViewModel != null) {
			return updatedUserViewModel;
		} else {
			throw new RegistrationException("Failed to update provider with id " + userViewModel.getProviderId());
		}
	}

	/**
     * Initializes and returns a ViewModel for a specific Provider entity.
     *
     * @param id The ID of the provider for which to create the ViewModel.
     * @return The initialized ViewModel, or null if the provider entity is not found.
     */
	public UserViewModel initProvider(Long id) {
		return providerDAO.initProvider(id);
	}

	/**
     * Reads a Provider entity by its ID.
     *
     * @param id The ID of the Provider entity.
     * @return The retrieved Provider entity, or null if not found.
     */
	public Provider readProvider(Long id) {
		return providerDAO.readProvider(id);
	}

	/**
     * Deletes a Provider entity by its ID.
     *
     * @param id The ID of the Provider entity to delete.
     */
	public void deleteProvider(Long id) {
		providerDAO.deleteProvider(id);
	}

	/**
     * Finds and returns a Provider entity by its email.
     *
     * @param email The email associated with the provider.
     * @return The retrieved Provider entity, or null if not found.
     */
	public Provider findByEmailProvider(String email) {
		return providerDAO.findByEmailProvider(email);
	}

	 /**
     * Finds and returns a Provider entity associated with the given User entity.
     *
     * @param user The User entity associated with the provider.
     * @return The retrieved Provider entity, or null if not found.
     */
	public Provider findByUserProvider(User user) {
		return providerDAO.findByUserProvider(user);
	}

	/**
     * Finds and returns a Provider entity associated with the given User ID.
     *
     * @param userId The ID of the User entity.
     * @return The retrieved Provider entity, or null if not found.
     */
	public Provider findProviderByUserId(Long userId) {
		return providerDAO.findProviderByUserId(userId);
	}
	 /**
     * Retrieves a list of all Provider entities.
     *
     * @return A list containing all Provider entities.
     */

	public List<Provider> getAllProviders() {
		return providerDAO.findAllProviders();
	}

	 /**
     * Creates a new Organizer entity based on the provided ViewModel.
     *
     * @param organizerForm The ViewModel containing organizer details.
     * @return The created Organizer entity.
     * @throws RegistrationException If registration fails due to duplicate email.
     */
	public Organizer createOrganizer(UserViewModel organizerForm) throws RegistrationException {
		Organizer organizer = organizerDAO.findByEmailOrganizer(organizerForm.getEmail());

		if (organizer != null) {
			throw new RegistrationException("This email is already used");
		}

		return organizerDAO.createOrganizer(organizerForm);
	}

	 /**
     * Updates the details of an Organizer entity based on the provided ViewModel.
     *
     * @param userViewModel The ViewModel containing updated organizer details.
     * @return The ViewModel containing the updated organizer details.
     */
	public UserViewModel updateOrganizer(UserViewModel userViewModel) {
		return organizerDAO.updateOrganizer(userViewModel);
	}

	 /**
     * Updates the details of an Organizer entity including an image, based on the provided ViewModel.
     *
     * @param userViewModel The ViewModel containing updated organizer details and image.
     * @return The ViewModel containing the updated organizer details.
     * @throws RegistrationException If the update fails.
     */
	public UserViewModel updateOrganizerWithImage(UserViewModel userViewModel) throws RegistrationException {

		UserViewModel updatedUserViewModel = organizerDAO.updateOrganizer(userViewModel);

		if (updatedUserViewModel != null) {
			return updatedUserViewModel;
		} else {
			throw new RegistrationException("Failed to update organizer with id " + userViewModel.getOrganizerId());
		}
	}

	/**
     * Initializes and returns a ViewModel for a specific Organizer entity.
     *
     * @param organizerId The ID of the organizer for which to create the ViewModel.
     * @return The initialized ViewModel, or null if the organizer entity is not found.
     */
	public UserViewModel initOrganizer(Long organizerId) {
		return organizerDAO.initOrganizer(organizerId);
	}

	/**
     * Reads an Organizer entity by its ID.
     *
     * @param id The ID of the Organizer entity.
     * @return The retrieved Organizer entity, or null if not found.
     */
	public Organizer readOrganizer(Long id) {
		return organizerDAO.readOrganizer(id);
	}

	/**
     * Deletes an Organizer entity by its ID.
     *
     * @param id The ID of the Organizer entity to delete.
     */
	public void deleteOrganizer(Long id) {
		organizerDAO.deleteOrganizer(id);
	}

	 /**
     * Finds and returns an Organizer entity by its email.
     *
     * @param email The email associated with the organizer.
     * @return The retrieved Organizer entity, or null if not found.
     * @throws RegistrationException If the email lookup fails.
     */
	public Organizer findByEmailOrganizer(String email) throws RegistrationException {
		return organizerDAO.findByEmailOrganizer(email);
	}

	/**
     * Finds and returns an Organizer entity associated with the given User entity.
     *
     * @param user The User entity associated with the organizer.
     * @return The retrieved Organizer entity, or null if not found.
     */
	public Organizer findByUserOrganizer(User user) {
		return organizerDAO.findByUserOrganizer(user);
	}

	 /**
     * Finds and returns an Organizer entity associated with the given User ID.
     *
     * @param userId The ID of the User entity.
     * @return The retrieved Organizer entity, or null if not found.
     */
	public Organizer findOrganizerByUserId(Long userId) {
		return organizerDAO.findOrganizerByUserId(userId);
	}

	/**
     * Retrieves a list of all Organizer entities.
     *
     * @return A list containing all Organizer entities.
     */
	public List<Organizer> getAllOrganizers() {
		return organizerDAO.findAllOrganizers();
	}

	 /**
     * Updates the subscription of an Organizer entity.
     *
     * @param organizerId The ID of the organizer for which to update the subscription.
     * @param subscription The new Subscription details.
     */
	public void updateSubscription(Long organizerId, Subscription subscription) {
		organizerDAO.updateSubscription(organizerId, subscription);
	}
	
	/**
     * Retrieves the subscription of an Organizer entity.
     *
     * @param organizerId The ID of the organizer for which to retrieve the subscription.
     * @return The retrieved Subscription entity.
     */
	public Subscription getSubscription(Long organizerId) {
		return organizerDAO.getSubscriptionForOrganizer(organizerId);
	}

	/**
     * Finds Organizer entities by company name or country.
     *
     * @param companyName The company name to search for.
     * @param country The country to search for.
     * @return A list of Organizer entities matching the search criteria.
     */
	public List<Organizer> findOrganizerByCompanyOrCountry(String companyName, String country) {
		return organizerDAO.findOrganizerByCompanyOrCountry(companyName, country);
	}


	/**
     * Retrieves the customization settings for an Organizer entity.
     *
     * @param organizerId The ID of the organizer for which to retrieve the customization settings.
     * @return The retrieved Customization entity.
     */
	public Customization getCustomizationForOrganizer(Long organizerId) {
		Organizer organizer = organizerDAO.readOrganizer(organizerId);
		if (organizer != null && organizer.getSubscription() != null) {
			return organizer.getSubscription().getCustomization();
		}
		return null;
	}
	

	/**
     * Retrieves the layout settings for an Organizer entity.
     *
     * @param organizerId The ID of the organizer for which to retrieve the layout settings.
     * @return The retrieved Layout entity.
     */
	public Layout getLayoutForOrganizer(Long organizerId) {
		Organizer organizer = organizerDAO.readOrganizer(organizerId);
		if (organizer != null && organizer.getSubscription() != null
				&& organizer.getSubscription().getCustomization() != null) {
			return organizer.getSubscription().getCustomization().getLayout();
		}
		return null;
	}

	 /**
     * Updates the graphic settings for an Organizer entity.
     *
     * @param userViewModel The ViewModel containing updated graphic settings.
     * @return The ViewModel containing the updated graphic settings.
     */
	public UserViewModel updateGraphicSettings(UserViewModel userViewModel) {
		return organizerDAO.updateGraphicSettings(userViewModel);
	}

}
