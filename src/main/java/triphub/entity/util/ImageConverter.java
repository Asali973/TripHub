package triphub.entity.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import triphub.dao.ImageDAO;
import triphub.entity.product.Image;

@FacesConverter("imageConverter")
public class ImageConverter implements Converter {

    @Inject
    private ImageDAO imageDAO;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            Long imageId = Long.parseLong(value);
            return imageDAO.read(imageId);
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof Image) {
            Image image = (Image) value;
            return String.valueOf(image.getId());
        } else {
            return null;
        }
    }
}
