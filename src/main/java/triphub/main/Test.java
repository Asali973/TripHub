package triphub.main;

import java.util.ArrayList;
import java.util.List;

import triphub.dao.HibernateUtil;
import triphub.dao.UserDao;
import triphub.entity.Identification;
import triphub.entity.PaymentInfo;
import triphub.entity.User;

public class Test {

	public static void main(String[] args) {
		UserDao userDAO = null;
		  List<User> users = new ArrayList<>();

      try {
      	 System.out.println("Lancement et démarrage de l'entity manager ...");
          // Instantiate UserDAO with the EntityManager
          userDAO  = new UserDao();

          // Create a new User
          User newUser = new User();
          newUser.setFirstName("John");
          newUser.setLastName("Doe");
          newUser.setEmail("john.doe@example.com");
          newUser.setPhoneNum("123-456-7890");
          newUser.setAddress("123 Main St, City, Country");

          // Create an Identification for the user
          Identification identification = new Identification();
          identification.setLoginName("johndoe");
          identification.setPassword("password");

          // Create a PaymentInfo for the user
          PaymentInfo paymentInfo = new PaymentInfo();
          paymentInfo.setCcNumber("1234-5678-9012-3456");
          paymentInfo.setExpirationDate(null); 

          // Associate the Identification and PaymentInfo with the User
          newUser.setIdentification(identification);
          newUser.setPaymentInfo(paymentInfo);

          userDAO.createUser(newUser);
          System.out.println("User created: " + newUser);

          // Get the user by ID
          Long userId = newUser.getId();
          User retrievedUser = userDAO.getUserById(userId);
          System.out.println("Retrieved User: " + retrievedUser);

          // Get all users
          List<User> allUsers = userDAO.getAllUsers();
          System.out.println("All Users: " + allUsers);

          // Update the user
          retrievedUser.setFirstName("Jane");
          retrievedUser.setLastName("Smith");
          userDAO.updateUser(retrievedUser);
          System.out.println("User updated: " + retrievedUser);

          // Delete the user
          userDAO.deleteUser(userId);
          System.out.println("User deleted.");

      } catch (Exception e) {
          e.printStackTrace();
      }finally {        	
      	  if (userDAO != null) {
      		  userDAO.closeEntityManager();
      }
      
  }
}
			    
}
			    
			 
	

