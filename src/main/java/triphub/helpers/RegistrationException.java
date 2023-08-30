package triphub.helpers;

/**
 * Represents a custom exception that's thrown during user registration
 * processes in the system. This class is specifically used to indicate
 * registration-related issues.
 */
public class RegistrationException extends Exception {

	/**
	 * Constructs a new RegistrationException with the specified detail message.
	 * 
	 * @param message The detailed exception message, providing context on the
	 *                nature of the registration issue.
	 */
	public RegistrationException(String message) {
		super(message);
	}
}
