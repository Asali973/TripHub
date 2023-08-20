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

@FacesConverter(forClass = Layout.class)
public class LayoutConverter implements Converter {

    // Supprimez l'annotation @EJB
    private DatabaseLayoutDAO layoutDAO;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        return getLayoutDAO().find(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (!(value instanceof Layout) || ((Layout) value).getId() == null) {
            return "";
        }

        return String.valueOf(((Layout) value).getId());
    }


    private DatabaseLayoutDAO getLayoutDAO() {
        if (layoutDAO == null) {
            layoutDAO = CDI.current().select(DatabaseLayoutDAO.class).get();
        }
        return layoutDAO;
    }
}


