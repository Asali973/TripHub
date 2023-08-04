package triphub.entity.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import triphub.dao.PictureDAO;

@FacesConverter("imageConverter")
public class ImageConverter implements Converter {

	@Inject
	private PictureDAO pictureDAO;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			Long imageId = Long.parseLong(value);
			return pictureDAO.read(imageId);
		} else {
			return null;
		}
	}

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
