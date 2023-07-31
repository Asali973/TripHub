package triphub.helpers;

import org.mindrot.jbcrypt.BCrypt;

public interface IPasswordManager {

	// Encrypt password
		public String hashPassword(String plainTextPassword) ;
		// Check if the password matches the encrypted password
		public boolean checkPassword(String plainPassword, String hashedPassword);
}
 