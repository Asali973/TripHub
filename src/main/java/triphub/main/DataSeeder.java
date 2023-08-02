package triphub.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import triphub.dao.product.DestinationDAO;
import triphub.dao.product.PriceDAO;
import triphub.dao.product.ThemeDAO;
import triphub.dao.product.TourPackageDAO;
import triphub.dao.user.UserDAO;
import triphub.entity.product.Destination;
import triphub.entity.product.Price;
import triphub.entity.product.Theme;
import triphub.entity.product.TourPackage;
import triphub.entity.user.Customer;

public class DataSeeder {

	private EntityManager em;

	public DataSeeder() {
		em = JPAUtil.getEntityManager();
	}

	UserDAO userDAO = new UserDAO(em);
	Customer customer1 = new Customer();
	DestinationDAO desDao = new DestinationDAO(em);
	ThemeDAO themeDao = new ThemeDAO(em);
	PriceDAO priceDao = new PriceDAO(em);
	TourPackageDAO tpDao = new TourPackageDAO(em);

	public void seedData() {
        List<Theme> themes = createSampleThemes();
        persistEntities(themes);

        List<Destination> destinations = createSampleDestinations();
        persistEntities(destinations);

        List<Price> prices = createSamplePrices();
        persistEntities(prices);

        List<TourPackage> tourPackages = createSampleTourPackages(themes, destinations, prices);
        persistEntities(tourPackages);
    }

    private void persistEntities(List<?> entities) {
        em.getTransaction().begin();
        for (Object entity : entities) {
            em.persist(entity);
        }
        em.getTransaction().commit();
    }

	private List<Theme> createSampleThemes() {
		// Define sample Theme objects here using collection initialization
		return Arrays.asList(new Theme("Adventure"), new Theme("Cultural"), new Theme("InstaSpots"),
				new Theme("Nature"), new Theme("Heritage"));
	}

	private List<Destination> createSampleDestinations() {
		return Arrays.asList(new Destination("Giethoorn", "Steenwijkerland", "The Netherlands"),
				new Destination("Paris", "Ile de France", "France"),
				new Destination("London", "south-east England", "The UK"),
				new Destination("Istanbul", "Marmara", "Turkey"), new Destination("Ghent", "East Flanders", "Belgium"));
	}

	private List<Price> createSamplePrices() {
		return Arrays.asList(new Price(1200, "USD"), new Price(1140, "Euro"));
	}
		
    private List<TourPackage> createSampleTourPackages(List<Theme> themes, List<Destination> destinations, List<Price> prices) {
        // Define sample TourPackage objects here using the themes, destinations, and prices
        List<TourPackage> tourPackages = new ArrayList<>();

        TourPackage package1 = new TourPackage();
        package1.setName("Tour Package 1");
        package1.setTheme(themes.get(0));
        package1.setDestination(destinations.get(0));
        package1.setPrice(prices.get(0));
        tourPackages.add(package1);

        TourPackage package2 = new TourPackage();
        package2.setName("Tour Package 2");
        package2.setTheme(themes.get(1));
        package2.setDestination(destinations.get(1));
        package2.setPrice(prices.get(1));
        tourPackages.add(package2);       

        return tourPackages;
    }

	
}