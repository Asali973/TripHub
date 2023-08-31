package triphub.entity.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import triphub.dao.PictureDAO;
/**
 * A JSF converter that handles conversion between `Picture` entities 
 * and their String representation (based on their ID).
 */
@FacesConverter("imageConverter")
public class ImageConverter implements Converter {

	@Inject
	private PictureDAO pictureDAO;

	 /**
     * Converts the provided string representation of an image (its ID) 
     * to the corresponding `Picture` entity.
     * 
     * @param context The FacesContext for the current request.
     * @param component The UIComponent that is being processed.
     * @param value The string representation of the image's ID.
     * @return A `Picture` entity that corresponds to the provided ID or null if not found.
     */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			Long imageId = Long.parseLong(value);
			return pictureDAO.read(imageId);
		} else {
			return null;
		}
	}

	/**
     * Converts a `Picture` entity to its string representation (its ID).
     * 
     * @param context The FacesContext for the current request.
     * @param component The UIComponent that is being processed.
     * @param value The `Picture` entity to be converted.
     * @return The string representation (ID) of the provided `Picture` entity or null if not applicable.
     */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && value instanceof Picture) {
			Picture picture = (Picture) value;
			return String.valueOf(picture.getId());
		} else {
			return null;
		}
	}
}
