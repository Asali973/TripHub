package triphub.helpers;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    private static PasswordUtils instance;

    private PasswordUtils() {}

    public static synchronized PasswordUtils getInstance() {
        if (instance == null) {
            instance = new PasswordUtils();
        }
        return instance;
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}

