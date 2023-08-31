package triphub.viewModel;

/**
 * This class represents a view model for user login information within the TripHub application.
 * It contains fields to hold the user's email and password.
 */
public class LoginViewModel {
    /** The email associated with the user's account. */
    private String email;

    /** The password for the user's account. */
    private String password;


	// getters and setters

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
