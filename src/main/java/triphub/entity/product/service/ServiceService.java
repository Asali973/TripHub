package triphub.entity.product.service;

import java.util.Date;
import java.util.List;

import triphub.entity.subservices.Accommodation;
import triphub.entity.subservices.Restaurant;
import triphub.entity.subservices.Transportation;

public interface ServiceService {

	List<Accommodation> advancedSearchAccommodations(Date availableFrom, Date availableTill, ServiceType accommodation);

	List<Transportation> advancedSearchTransportations(Date availableFrom, Date availableTill,
			ServiceType transportation);

	List<Restaurant> advancedSearchRestaurants(Date availableFrom, Date availableTill, ServiceType restaurant);

}
