package triphub.services;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import triphub.dao.*;
import triphub.dao.user.CustomerDAO;
import triphub.entity.user.*;
import triphub.helpers.RegistrationException;
import triphub.viewModel.UserViewModel;

@Stateless
public class UserService {

	@Inject
	private CustomerDAO customerDAO;
	
    public UserService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

	public UserService() {
	}

	public Customer create(UserViewModel customerForm) throws RegistrationException{

		Customer email = null;
		try {
			email = customerDAO.findByEmail(customerForm.getEmail());
		} catch (RegistrationException e) {
			System.out.println("Customer with email " + customerForm.getEmail() + " not found.");
		}

		if (email != null) {
			throw new RegistrationException("This email is already used");
		}

		return customerDAO.create(customerForm);
	}
	
	
	public Customer read(Long id) {
		return customerDAO.read(id);
	}

	public void delete(Long id) {
		customerDAO.delete(id);
	}

	public void update(Long id) {
		customerDAO.update(id);
	}

	public Customer findByEmail(String email) throws RegistrationException {
		return customerDAO.findByEmail(email);
	}

	public Customer findByUser(User user) {
		return customerDAO.findByUser(user);
	}

}
