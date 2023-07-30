package triphub.main;

import javax.persistence.EntityManager;

import triphub.dao.CustomerDAO;
import triphub.dao.UserDAO;
import triphub.dao.service.AccommodationDAO;
import triphub.dao.service.AddressDAO;
import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.accommodation.AccommodationType;
import triphub.entity.user.Customer;
import triphub.entity.user.User;
import triphub.entity.util.Address;

public class main {

	public static void main(String[] args) {

		EntityManager em = JPAUtil.getEntityManager();

		UserDAO userDAO = new UserDAO(em);

		User user = new User();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setEmail("john.doe@example.com");
		user.setPhoneNum("1234567890");

		em.getTransaction().begin();
		userDAO.create(user);
		em.getTransaction().commit();

		CustomerDAO customerDAO = new CustomerDAO(em);

		Customer customer1 = new Customer();
		customer1.setUser(user);
		em.getTransaction().begin();
		customerDAO.create(customer1);
		em.getTransaction().commit();

		User foundUser = userDAO.read(user.getId());
		System.out.println("Found user: " + foundUser.getFirstName() + " " + foundUser.getLastName());
		
		AddressDAO addressDAO = new AddressDAO(em);
		Address address1 = new Address();
		address1.setNum("12");
		address1.setStreet("rue de la gare");
		address1.setCity("Amiens");
		address1.setState("Haut de France");
		address1.setCountry("France");
		

		AccommodationDAO accommodationDao = new AccommodationDAO(em);
		
		
		Accommodation accommodation1 = new Accommodation();
		accommodation1.setNameAccommodation("Hotel Ibis");
		accommodation1.setAddress(address1);
		accommodation1.setAccommodation(AccommodationType.Hotel);
		em.getTransaction().begin();
		accommodationDao.create(accommodation1);
		em.getTransaction().commit();
		System.out.println("accommodation create "+ accommodation1.getNameAccommodation());
		
		JPAUtil.shutdown();
	}
}
