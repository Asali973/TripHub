
package triphub.main;

import java.util.List;
import javax.persistence.EntityManager;
import triphub.dao.product.DestinationDAO;
import triphub.dao.product.PriceDAO;
import triphub.dao.product.ThemeDAO;
import triphub.dao.product.TourPackageDAO;
import triphub.dao.service.RestaurantDAO;
import triphub.entity.product.Destination;
import triphub.entity.product.Price;
import triphub.entity.product.Theme;
import triphub.entity.product.TourPackage;
import triphub.entity.product.service.restaurant.Restaurant;


public class DataSeedTest {

	public static void main(String[] args) {

		
		EntityManager em = JPAUtil.getEntityManager();
		 try {
	            DataSeeder dataSeeder = new DataSeeder();
	            dataSeeder.seedData();

	            ThemeDAO themeDAO = new ThemeDAO(em);
	            List<Theme> themes = themeDAO.getAllThemes();
	            System.out.println("Themes:");
	            for (Theme theme : themes) {
	                System.out.println(theme.getThemeName());
	            }

	            DestinationDAO destinationDAO = new DestinationDAO(em);
	            List<Destination> destinations = destinationDAO.getAllDestinations();
	            System.out.println("Destinations:");
	            for (Destination destination : destinations) {
	                System.out.println(destination.getCityName() + ", " + destination.getState() + ", " + destination.getCountry());
	            }

	            PriceDAO priceDAO = new PriceDAO(em);
	            List<Price> prices = priceDAO.getAllPrices();
	            System.out.println("Prices:");
	            for (Price price : prices) {
	                System.out.println(price.getAmount() + " " + price.getCurrency());
	            }

	            TourPackageDAO tourPackageDAO = new TourPackageDAO();
	            List<TourPackage> tourPackages = tourPackageDAO.getAllTourPackages();
	            System.out.println("Tour Packages:");
	            for (TourPackage tourPackage : tourPackages) {
	                System.out.println("Name: " + tourPackage.getName());
	                System.out.println("Theme: " + tourPackage.getTheme().getThemeName());
	                System.out.println("Destination: " + tourPackage.getDestination().getCityName() + ", " +
	                        tourPackage.getDestination().getState() + ", " +
	                        tourPackage.getDestination().getCountry());
	                System.out.println("Price: " + tourPackage.getPrice().getAmount() + " " +
	                        tourPackage.getPrice().getCurrency());
	                System.out.println("Images: " + tourPackage.getImages()); // Add image retrieval logic if needed
	                System.out.println("-----------------------");
	            }

	        } finally {
	        	JPAUtil.shutdown();
	        }
	}

}

