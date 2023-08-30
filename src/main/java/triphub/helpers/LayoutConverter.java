package triphub.helpers;

import javax.ejb.EJB;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.faces.convert.Converter;

import triphub.dao.customization.DatabaseLayoutDAO;
import triphub.entity.subscription.Layout;

/**
 * A converter class for the {@link Layout} entity. This converter allows JSF to
 * convert between {@link Layout} objects and their string representations
 * (typically IDs). It's primarily used in JSF dropdowns (or similar components)
 * where the value might be represented as a String.
 */
@FacesConverter(forClass = Layout.class)
public class LayoutConverter implements Converter {

	private DatabaseLayoutDAO layoutDAO;

	/**
	 * Converts a string value, which is the ID of a {@link Layout}, into its
	 * corresponding {@link Layout} object.
	 *
	 * @param context   The current FacesContext instance.
	 * @param component The UI component that this conversion is associated with.
	 * @param value     The string value to be converted (the ID of the
	 *                  {@link Layout}).
	 * @return The {@link Layout} object corresponding to the given ID, or null if
	 *         the value is empty.
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		return getLayoutDAO().find(Long.valueOf(value));
	}

	/**
	 * Converts a {@link Layout} object into its string representation, which is its
	 * ID.
	 *
	 * @param context   The current FacesContext instance.
	 * @param component The UI component that this conversion is associated with.
	 * @param value     The {@link Layout} object to be converted.
	 * @return The ID of the {@link Layout} as a String, or an empty string if the
	 *         value isn't a valid {@link Layout}.
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (!(value instanceof Layout) || ((Layout) value).getId() == null) {
			return "";
		}

		return String.valueOf(((Layout) value).getId());
	}

	/**
	 * Lazily loads an instance of {@link DatabaseLayoutDAO} using CDI.
	 *
	 * @return An instance of {@link DatabaseLayoutDAO}.
	 */
	private DatabaseLayoutDAO getLayoutDAO() {
		if (layoutDAO == null) {
			layoutDAO = CDI.current().select(DatabaseLayoutDAO.class).get();
		}
		return layoutDAO;
	}
}
