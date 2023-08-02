//package triphub.main;
//
//import java.util.Date;
//
//import javax.persistence.EntityManager;
//
//import triphub.dao.CustomerDAO;
//import triphub.dao.UserDAO;
//
//import triphub.dao.service.AccommodationDAO;
//import triphub.dao.service.AddressDAO;
//import triphub.entity.product.service.accommodation.Accommodation;
//import triphub.entity.product.service.accommodation.AccommodationType;
//
//import triphub.dao.product.DestinationDAO;
//import triphub.dao.product.PriceDAO;
//import triphub.dao.product.ThemeDAO;
//import triphub.dao.product.TourPackageDAO;
//import triphub.entity.product.Destination;
//import triphub.entity.product.Price;
//import triphub.entity.product.Theme;
//import triphub.entity.product.TourPackage;
//
//import triphub.entity.user.Customer;
//import triphub.entity.user.User;
//import triphub.entity.util.Address;
//import triphub.helpers.RegistrationException;
//import triphub.services.UserService;
//import triphub.viewModel.UserViewModel;
//
//public class main {
//
//	public static void main(String[] args) {
//
//	 EntityManager em = JPAUtil.getEntityManager();
//
//		UserDAO userDAO = new UserDAO(em);		
//		
//        // Créer un nouveau CustomerDAO
//        CustomerDAO customerDAO = new CustomerDAO(em);
//		
//		// Créer un nouveau UserViewModel
//        UserViewModel userViewModel = new UserViewModel();
//        userViewModel.setFirstName("Jean");
//        userViewModel.setLastName("Dupont");
//        userViewModel.setEmail("jean.dupont@mail2.com");
//        userViewModel.setPhoneNum("0123456789");
//        userViewModel.setPassword("password123");
//        userViewModel.setNum("123");
//        userViewModel.setStreet("Rue des Fleurs");
//        userViewModel.setCity("Paris");
//        userViewModel.setState("Île-de-France");
//        userViewModel.setCountry("France");
//        userViewModel.setZipCode("75001");
//        userViewModel.setCCNumber("1234567890123456");
//        userViewModel.setExpirationDate(new Date());
//
//        // Créer un nouveau UserService
//        UserService userService = new UserService(customerDAO);
//        
//        em.getTransaction().begin();
//        try {
//            // Essayer de créer un nouveau client
//            Customer customer = userService.createCustomer(userViewModel);
//            System.out.println("Customer created successfully!");
//
//            // Récupérer le client créé
//            Customer createdCustomer = userService.readCustomer(customer.getUser().getId());
//            System.out.println("Created customer: " + createdCustomer.getUser().getFirstName() + " " + createdCustomer.getUser().getLastName());
//
//        } catch (RegistrationException e) {
//            System.out.println("Failed to create customer: " + e.getMessage());
//        }
//        
//        em.getTransaction().commit();
//		
////		AddressDAO addressDAO = new AddressDAO(em);
////		Address address1 = new Address();
////		address1.setNum("12");
////		address1.setStreet("rue de la gare");
////		address1.setCity("Amiens");
////		address1.setState("Haut de France");
////		address1.setCountry("France");
////		
////
////
////		AccommodationDAO accommodationDao = new AccommodationDAO(em);
////		
////		
////		Accommodation accommodation1 = new Accommodation();
////		accommodation1.setNameAccommodation("Hotel Ibis");
////		accommodation1.setAddress(address1);
////		accommodation1.setAccommodation(AccommodationType.Hotel);
////		em.getTransaction().begin();
////		accommodationDao.create(accommodation1);
////		em.getTransaction().commit();
////		System.out.println("accommodation create "+ accommodation1.getNameAccommodation());
////		
////
////		
////		//test tourpackage
////		DestinationDAO desDao =new DestinationDAO(em);
////		ThemeDAO themeDao= new ThemeDAO(em);
////		PriceDAO priceDao= new PriceDAO(em);
////		TourPackageDAO tpDao= new TourPackageDAO(em);
////		
////		Destination destination = new Destination();
////		destination.setCityName("Paris");
////		destination.setState("IDF");
////		destination.setCountry("France");
////		
////		em.getTransaction().begin();
////		desDao.create(destination);
////		em.getTransaction().commit();
////		
////		Price price = new Price();
////		price.setAmount(1500.0);
////		price.setCurrency("USD");
////		
////		em.getTransaction().begin();
////		priceDao.create(price);
////		em.getTransaction().commit();
////		
////		Theme theme = new Theme("InstaSpots");
////	
////		
////		em.getTransaction().begin();
////		themeDao.create(theme);
////		em.getTransaction().commit();
////		
////		TourPackage tourPackage = new TourPackage();
////		tourPackage.setName("Summer Adventure");
////		
////		tourPackage.setDestination(destination);
////		tourPackage.setTheme(theme);
////		tourPackage.setPrice(price);
////
////		em.getTransaction().begin();
////		tpDao.createOrUpdate(tourPackage);
////		em.getTransaction().commit();
////		
////        System.out.println("Tourpackage:");
////        System.out.println("Name: " + tourPackage.getName());
////        System.out.println("Price: " + tourPackage.getPrice().getAmount() + " " + tourPackage.getPrice().getCurrency());
////        System.out.println("Destination: " + tourPackage.getDestination().getCityName() + ", " + tourPackage.getDestination().getState() + ", " + tourPackage.getDestination().getCountry());
////        System.out.println("Theme: " + tourPackage.getTheme().getName());
//        
//
//		JPAUtil.shutdown();
//	}
//}
