package triphub.helpers;

/**
 * Custom exception class to represent authentication-related errors.
 */
public class AuthenticationException extends Exception {
	
    /**
     * Constructs a new AuthenticationException with the specified detail message.
     * 
     * @param message the detail message.
     */
    public AuthenticationException(String message) {
        super(message);
    }
}

