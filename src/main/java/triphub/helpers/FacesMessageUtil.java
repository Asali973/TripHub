package triphub.helpers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Utility class for easily sending success and error messages to the JSF UI.
 */
public class FacesMessageUtil {
	
    /**
     * Sends a success message to the JSF UI.
     * 
     * @param message the content of the success message.
     */
    public static void addSuccessMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", message));
    }

    /**
     * Sends an error message to the JSF UI.
     * 
     * @param message the content of the error message.
     */
    public static void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
    }
}

