package triphub.helpers;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordManagerImpl implements IPasswordManager {

	private static PasswordManagerImpl instance = null;

	private PasswordManagerImpl() {
	}

	public static synchronized PasswordManagerImpl getInstance() {
		if (instance == null) {
			instance = new PasswordManagerImpl();
		}
		return instance;

	}

	// Encrypt password
	public String hashPassword(String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	// Check if the password matches the encrypted password
	public boolean checkPassword(String plainPassword, String hashedPassword) {
		if (BCrypt.checkpw(plainPassword, hashedPassword))
			return true;
		else
			return false;
	}

}
