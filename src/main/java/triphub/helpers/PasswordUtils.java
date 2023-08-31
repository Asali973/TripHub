package triphub.helpers;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password-related operations using the BCrypt hashing
 * algorithm. This class utilizes the Singleton pattern, ensuring that only one
 * instance of PasswordUtils is created. It provides methods to hash a password
 * and to check if a given password matches a hashed password.
 */
public class PasswordUtils {

	/** The singleton instance of the PasswordUtils class. */
	private static PasswordUtils instance;

	/**
	 * Private constructor to prevent instantiation from outside the class.
	 */
	private PasswordUtils() {
	}

	/**
	 * Returns the singleton instance of the PasswordUtils class. If the instance
	 * hasn't been initialized yet, it initializes it.
	 * 
	 * @return The singleton instance of PasswordUtils.
	 */
	public static synchronized PasswordUtils getInstance() {
		if (instance == null) {
			instance = new PasswordUtils();
		}
		return instance;
	}

	/**
	 * Checks whether the provided plaintext password matches the given hashed
	 * password.
	 * 
	 * @param plainPassword  The plaintext password to check.
	 * @param hashedPassword The hashed version of the password for comparison.
	 * @return {@code true} if the plaintext password matches the hashed password,
	 *         otherwise {@code false}.
	 */
	public boolean checkPassword(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}

	/**
	 * Hashes the provided plaintext password using BCrypt.
	 * 
	 * @param plainTextPassword The plaintext password to hash.
	 * @return The hashed version of the plaintext password.
	 */
	public String hashPassword(String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}
}
