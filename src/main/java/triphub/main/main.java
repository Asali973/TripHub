//package triphub.main;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//
//import triphub.dao.CustomerDAO;
//import triphub.dao.UserDAO;
//
//import triphub.dao.service.AccommodationDAO;
//import triphub.dao.service.AddressDAO;
//
//import triphub.dao.service.TransportationDAO;
//import triphub.entity.product.service.accommodation.Accommodation;
//import triphub.entity.product.service.accommodation.AccommodationType;
//import triphub.entity.product.service.transportation.Transportation;
//import triphub.entity.product.service.transportation.TransportationType;
//import triphub.dao.service.RestaurantDAO;
//import triphub.entity.product.service.restaurant.Restaurant;
//import triphub.dao.product.DestinationDAO;
//import triphub.dao.product.PriceDAO;
//import triphub.dao.product.ThemeDAO;
//import triphub.dao.product.TourPackageDAO;
//import triphub.entity.product.Destination;
//import triphub.entity.product.Price;
//import triphub.entity.product.Theme;
//import triphub.entity.product.TourPackage;
//import triphub.entity.user.Customer;
//import triphub.entity.user.User;
//import triphub.entity.util.Address;
//import triphub.helpers.RegistrationException;
//import triphub.services.AccommodationService;
//import triphub.viewModel.AccommodationViewModel;
//import triphub.services.TransportationService;
//import triphub.viewModel.TransportationViewModel;
//import triphub.services.RestaurantService;
//import triphub.viewModel.RestaurantViewModel;
//
//
//public class main {
//
//	public static void main(String[] args) {
//
//	 EntityManager em = JPAUtil.getEntityManager();
//
//	 AddressDAO addressDao = new AddressDAO(em);
//	 Address address1 = new Address();
//		address1.setNum("12");
//		address1.setStreet("rue de la gare");
//		address1.setCity("Amiens");
//		address1.setState("Haut de France");
//		address1.setCountry("France");
//		em.getTransaction().begin();
//		addressDao.create(address1);
//		em.getTransaction().commit();
//		
//		Address address2 = new Address();
//		address2.setNum("24");
//		address2.setStreet("rue de la gare");
//		address2.setCity("Lille");
//		address2.setState("Haut de France");
//		address2.setCountry("France");
//		em.getTransaction().begin();
//		addressDao.create(address2);
//		em.getTransaction().commit();
//		
//		Address address3 = new Address();
//		address3.setNum("2");
//		address3.setStreet("Piazza Napoleone");
//		address3.setCity("Florence");
//		address3.setState("Toscane");
//		address3.setCountry("Italie");
//		em.getTransaction().begin();
//		addressDao.create(address3);
//		em.getTransaction().commit();
//		
//
//		AccommodationDAO accommodationDao= new AccommodationDAO(em);
//		
//		Accommodation accommodation1 = new Accommodation();
//		accommodation1.setNameAccommodation("Hotel Ibis");
//		accommodation1.setAddress(address1);
//		accommodation1.setAccommodation(AccommodationType.Hotel);
//		em.getTransaction().begin();
//		//accommodationDao.create(accommodation1);
//		em.getTransaction().commit();
//		System.out.println("accommodation create "+ accommodation1.getNameAccommodation());
//		
//		
//		//-------------------------------------- Restaurant ---------------------------------------------
//	
//		RestaurantDAO restaurantDao = new RestaurantDAO(em);
//		RestaurantService restaurantService = new RestaurantService(restaurantDao);
//		
//		RestaurantViewModel restaurantvm1 = new RestaurantViewModel();
//		restaurantvm1.setNameRestaurant("Restau 01");
//		restaurantvm1.setAddress(address1);
//		restaurantvm1.setDescription("Description 01");
//		
//		RestaurantViewModel restaurantvm2 = new RestaurantViewModel();
//		restaurantvm1.setNameRestaurant("Restau 02");
//		restaurantvm2.setAddress(address2);
//		restaurantvm2.setDescription("Description 02");
//		
//		RestaurantViewModel restaurantvm3 = new RestaurantViewModel();
//		restaurantvm1.setNameRestaurant("Restau 03");
//		restaurantvm2.setAddress(address3);
//		restaurantvm2.setDescription("Description 03");
//		
//		em.getTransaction().begin();		
//		
//		Restaurant restaurantTest1 = restaurantService.create(restaurantvm1);
//		Restaurant restaurantTest2 = restaurantService.create(restaurantvm2);
//		Restaurant restaurantTest3 = restaurantService.create(restaurantvm3);
//		
//		em.getTransaction().commit();	
//		
//	        
//		JPAUtil.shutdown();
//	}
//
//}