//package triphub.main;
//
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import triphub.dao.user.*;
//import triphub.dao.product.DestinationDAO;
//import triphub.dao.product.PriceDAO;
//import triphub.dao.product.ThemeDAO;
//import triphub.dao.product.TourPackageDAO;
//import triphub.dao.service.RestaurantDAO;
//import triphub.entity.product.Destination;
//import triphub.entity.product.Price;
//import triphub.entity.product.Theme;
//import triphub.entity.product.TourPackage;
//import triphub.entity.user.Customer;
//import triphub.entity.product.service.restaurant.Restaurant;
//import triphub.entity.util.Address;
//
//public class DataSeeder {
//	 private final EntityManager em;
//
//	    public DataSeeder(EntityManager em) {
//	        this.em = em;
//	    }
//
//
//	UserDAO userDAO = new UserDAO();
//
//	Customer customer1 = new Customer();
//	DestinationDAO desDao = new DestinationDAO();
//	ThemeDAO themeDao = new ThemeDAO();
//	PriceDAO priceDao = new PriceDAO();
//	TourPackageDAO tpDao = new TourPackageDAO();	
//
//	RestaurantDAO rDAO = new RestaurantDAO(em);
////	AddressDAO addressDAO = new AddressDAO(em);
//
//	RestaurantDAO rDAO = new RestaurantDAO();
//	AddressDAO addressDAO = new AddressDAO();
//
//
//
//	public void seedData() {
//        List<Theme> themes = createSampleThemes();
//        persistEntities(themes);
//
//        List<Destination> destinations = createSampleDestinations();
//        persistEntities(destinations);
//
//        List<Price> prices = createSamplePrices();
//        persistEntities(prices);
//
//        List<TourPackage> tourPackages = createSampleTourPackages(themes, destinations, prices);
//        persistEntities(tourPackages);    
//        
//        List<Address> addresses = createSampleAddresses();
//        persistEntities(addresses);
//        
//        List<Restaurant> restaurants = createSampleRestaurants(addresses);
//        persistEntities(restaurants);
//    }
//
//
//	private void persistEntities(List<?> entities) {
//
//        em.getTransaction().begin();
//        for (Object entity : entities) {
//            em.persist(entity);
//        }
//        em.getTransaction().commit();
//    }
//
//	private List<Theme> createSampleThemes() {
//		// Define sample Theme objects here using collection initialization
//		return Arrays.asList(new Theme("Adventure"), new Theme("Cultural"), new Theme("InstaSpots"),
//				new Theme("Nature"), new Theme("Heritage"));
//	}
//
//	private List<Destination> createSampleDestinations() {
//		return Arrays.asList(new Destination("Giethoorn", "Steenwijkerland", "The Netherlands"),
//				new Destination("Paris", "Ile de France", "France"),
//				new Destination("London", "south-east England", "The UK"),
//				new Destination("Istanbul", "Marmara", "Turkey"), new Destination("Ghent", "East Flanders", "Belgium"));
//	}
//
//	private List<Price> createSamplePrices() {
//
//	    return Arrays.asList(
//	        new Price(new BigDecimal("1200"), "USD"),
//	        new Price(new BigDecimal("1140"), "Euro")
//	    );
//
//	}
//		
//    private List<TourPackage> createSampleTourPackages(List<Theme> themes, List<Destination> destinations, List<Price> prices) {
//        // Define sample TourPackage objects here using the themes, destinations, and prices
//        List<TourPackage> tourPackages = new ArrayList<>();
//
//        TourPackage package1 = new TourPackage();
//        package1.setName("Tour Package 1");
//        package1.setTheme(themes.get(0));
//        package1.setDestination(destinations.get(0));
//        package1.setPrice(prices.get(0));
//        tourPackages.add(package1);
//
//        TourPackage package2 = new TourPackage();
//        package2.setName("Tour Package 2");
//        package2.setTheme(themes.get(1));
//        package2.setDestination(destinations.get(1));
//        package2.setPrice(prices.get(1));
//        tourPackages.add(package2);       
//
//        return tourPackages;
//    }
//    
//    private List<Address> createSampleAddresses(){
//
//    	List<Address> addresses = new ArrayList<>();
//
//		Address address1 = new Address();
//		address1.setNum("12");
//		address1.setStreet("rue de la gare");
//		address1.setCity("Amiens");
//		address1.setState("Haut de France");
//		address1.setCountry("France");
//		addresses.add(address1);
//		
//		Address address2 = new Address();
//		address2.setNum("24");
//		address2.setStreet("rue de la gare");
//		address2.setCity("Lille");
//		address2.setState("Haut de France");
//		address2.setCountry("France");
//		addresses.add(address2);
//		
//		Address address3 = new Address();
//		address3.setNum("2");
//		address3.setStreet("Piazza Napoleone");
//		address3.setCity("Florence");
//		address3.setState("Toscane");
//		address3.setCountry("Italie");
//		addresses.add(address3);
//		
//		Address address4 = new Address();
//		address4.setNum("1");
//		address4.setStreet("Garibaldi");
//		address4.setCity("Milan");
//		address4.setState("Lombardie");
//		address4.setCountry("Italie");
//		addresses.add(address4);
//		
//		return addresses;
//    }
//    
//    
//    private List<Restaurant> createSampleRestaurants(List<Address> addresses) {
//    	
//    	List<Restaurant> Restaurants = new ArrayList<>();
//    	
//    	Restaurant restaurant1 = new Restaurant();
//    	restaurant1.setNameRestaurant("restaut 1");
//		restaurant1.setAddressRestaurant(addresses.get(0));
//		restaurant1.setDescription("c'est un très joli restaurant");
//		Restaurants.add(restaurant1);
//		
//    	Restaurant restaurant2 = new Restaurant();
//    	restaurant2.setNameRestaurant("restaut 2");
//		restaurant2.setAddressRestaurant(addresses.get(1));
//		restaurant2.setDescription("voici un restau sympa !");
//		Restaurants.add(restaurant2);
//
//    	Restaurant restaurant3 = new Restaurant();
//    	restaurant3.setNameRestaurant("restaut 3");
//		restaurant3.setAddressRestaurant(addresses.get(2));
//		restaurant3.setDescription("ce restau est vraiment nul");
//		Restaurants.add(restaurant3);
//    	
//		return Restaurants;
//	}    	
//
//}