package triphub.entity.subservices;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import triphub.entity.subservices.AccommodationType;

@FacesConverter(forClass = AccommodationType.class)
public class AccommodationTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            return AccommodationType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ConverterException("Invalid value: " + value, e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof AccommodationType) {
            return ((AccommodationType) value).name();
        }
        return null;
    }
}
