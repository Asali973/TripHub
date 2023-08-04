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
		// Create user
		User user = new User();
		user.setId(form.getUserId());
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
	
	public Customer readCustomer(Long id) {
	    return em.find(Customer.class, id);
	}

	public void deleteCustomer(Long id) {
	    Customer customer = em.find(Customer.class, id);
	    if (customer != null) {
	        em.remove(customer);
	    }
	}

    public UserViewModel updateCustomer(UserViewModel userViewModel) {
        // Find existing user and customer
        User user = em.find(User.class, userViewModel.getUserId());
        Customer customer = em.find(Customer.class, userViewModel.getCustomerId());
        
        if (user == null || customer == null) {
            return null;  // Or throw an exception
        }
        
        // Update user and address
        user.setFirstName(userViewModel.getFirstName());
        user.setLastName(userViewModel.getLastName());
        user.setEmail(userViewModel.getEmail());
        user.setPhoneNum(userViewModel.getPhoneNum());

        Address address = user.getAddress();
        address.setNum(userViewModel.getNum());
        address.setStreet(userViewModel.getStreet());
        address.setCity(userViewModel.getCity());
        address.setState(userViewModel.getState());
        address.setCountry(userViewModel.getCountry());
        address.setZipCode(userViewModel.getZipCode());

        // Update finance
        FinanceInfo finance = user.getFinance();
        finance.setCCNumber(userViewModel.getCCNumber());
        finance.setExpirationDate(userViewModel.getExpirationDate());

        // Update profile picture
        Picture pictureProfile = customer.getPicture();
        pictureProfile.setLink(userViewModel.getProfilePicture());

        // Persist changes
        em.persist(user);
        em.persist(address);
        em.persist(finance);
        em.persist(pictureProfile);
        em.flush();

        return userViewModel;
    }
    
    
    public UserViewModel initCustomer(Long customerId) {
        Customer customer = em.find(Customer.class, customerId);
        if (customer == null) {
            return null;
        }

        User user = customer.getUser();
        if (user == null) {
            return null;
        }

        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setUserId(user.getId());
        userViewModel.setFirstName(user.getFirstName());
        userViewModel.setLastName(user.getLastName());
        userViewModel.setEmail(user.getEmail());
        userViewModel.setPhoneNum(user.getPhoneNum());

        Address address = user.getAddress();
        if (address != null) {
            userViewModel.setNum(address.getNum());
            userViewModel.setStreet(address.getStreet());
            userViewModel.setCity(address.getCity());
            userViewModel.setState(address.getState());
            userViewModel.setCountry(address.getCountry());
            userViewModel.setZipCode(address.getZipCode());
        }

        FinanceInfo finance = user.getFinance();
        if (finance != null) {
            userViewModel.setCCNumber(finance.getCCNumber());
            userViewModel.setExpirationDate(finance.getExpirationDate());
        }

        Picture pictureProfile = customer.getPicture();
        if (pictureProfile != null) {
            userViewModel.setProfilePicture(pictureProfile.getLink());
        }

        userViewModel.setCustomerId(customer.getId());

        return userViewModel;
    }




    public Customer findByEmailCustomer(String email) {
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.user.email = :email", Customer.class);
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
	
//	public UserViewModel initCustomer(Long userId, Long customerId) {
//  User user = em.find(User.class, userId);
//  if (user == null) {
//      return null;
//  }
//
//  UserViewModel userViewModel = new UserViewModel();
//  userViewModel.setUserId(user.getId());
//  userViewModel.setFirstName(user.getFirstName());
//  userViewModel.setLastName(user.getLastName());
//  userViewModel.setEmail(user.getEmail());
//  userViewModel.setPhoneNum(user.getPhoneNum());
//  // Password caché
//
//  Address address = user.getAddress();
//  userViewModel.setNum(address.getNum());
//  userViewModel.setStreet(address.getStreet());
//  userViewModel.setCity(address.getCity());
//  userViewModel.setState(address.getState());
//  userViewModel.setCountry(address.getCountry());
//  userViewModel.setZipCode(address.getZipCode());
//
//  FinanceInfo finance = user.getFinance();
//  userViewModel.setCCNumber(finance.getCCNumber());
//  userViewModel.setExpirationDate(finance.getExpirationDate());
//
//  Customer customer = em.find(Customer.class, customerId);
//  if (customer != null) {
//      Picture pictureProfile = customer.getPicture();
//      userViewModel.setProfilePicture(pictureProfile.getLink());
//      userViewModel.setCustomerId(customer.getId());
//  }
//
//  return userViewModel;
//}
}
