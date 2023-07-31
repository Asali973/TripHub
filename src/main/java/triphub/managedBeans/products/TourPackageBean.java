//package triphub.managedBeans.products;
//
//import java.io.Serializable;
//import java.util.List;
//
//import javax.ejb.Stateless;
//import javax.enterprise.context.RequestScoped;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//import org.hibernate.type.ImageType;
//
//import triphub.dao.product.TourPackageDAO;
//import triphub.entity.product.Destination;
//import triphub.entity.product.Image;
//import triphub.entity.product.Price;
//import triphub.entity.product.Theme;
//import triphub.entity.product.TourPackage;
//
//@ManagedBean(name="tourPackageBean")
//@SessionScoped
//
//public class TourPackageBean implements Serializable {		
//	
//	
//	private static final long serialVersionUID = 1L;
//	
//	private TourPackage newTourPackage;
//	private List<TourPackage> tourPackages;
//			// TourPackage info
//			private String name;
//			private Price price;
//			private Destination destination;
//			private Theme theme;
//			private List<Image> images;
//
//			// Price Info
//			private double amount;
//			private String currency;
//
//			// Destination info
//			private String cityName;
//			
//			private String state;
//			private String country;
//
//			// Theme Info
//			private String themeName;
//
//			// Image Info
//			private int size;
//			private int weight;
//			private String link;
//			private ImageType imagetype;
//			private String altText;
//			
//			// EntityManager setup
//			private EntityManagerFactory emf;
//			private EntityManager em;
//			
//			public TourPackageBean() {
//				emf = Persistence.createEntityManagerFactory("triphub");
//				em = emf.createEntityManager();				
//				
//			}
//			// Constructor
//			 public List<TourPackage> getAllTourPackages() {
//		        return tourPackageDAO.getAllTourPackages();
//			 }
//
//		    // Method to create a new TourPackage
//		    public void createTourPackage() {
//		    	tourPackageDAO.createOrUpdate(newTourPackage);
//		        tourPackages = tourPackageDAO.getAllTourPackages(); // Refresh the list of TourPackages
//		        newTourPackage = new TourPackage(); // Clear the newTourPackage object for the next entry
//		    }
//
//		    // Method to update an existing TourPackage
//		    public void updateTourPackage(TourPackage tourPackage) {
//		    	tourPackageDAO.createOrUpdate(tourPackage);
//		        tourPackages = tourPackageDAO.getAllTourPackages(); // Refresh the list of TourPackages
//		    }
//
//		    // Method to delete an existing TourPackage
//		    public void deleteTourPackage(TourPackage tourPackage) {
//		    	tourPackageDAO.delete(tourPackage);
//		        tourPackages = tourPackageDAO.getAllTourPackages(); // Refresh the list of TourPackages
//		    }
//			
//			public String getName() {
//				return name;
//			}
//
//			public void setName(String name) {
//				this.name = name;
//			}
//
//			public Price getPrice() {
//				return price;
//			}
//
//			public void setPrice(Price price) {
//				this.price = price;
//			}
//
//			public Destination getDestination() {
//				return destination;
//			}
//
//			public void setDestination(Destination destination) {
//				this.destination = destination;
//			}
//
//			public Theme getTheme() {
//				return theme;
//			}
//
//			public void setTheme(Theme theme) {
//				this.theme = theme;
//			}
//
//			public List<Image> getImages() {
//				return images;
//			}
//
//			public void setImages(List<Image> images) {
//				this.images = images;
//			}
//
//			public double getAmount() {
//				return amount;
//			}
//
//			public void setAmount(double amount) {
//				this.amount = amount;
//			}
//
//			public String getCurrency() {
//				return currency;
//			}
//
//			public void setCurrency(String currency) {
//				this.currency = currency;
//			}
//
//			public String getCityName() {
//				return cityName;
//			}
//
//			public void setCityName(String cityName) {
//				this.cityName = cityName;
//			}
//
//			public String getState() {
//				return state;
//			}
//
//			public void setState(String state) {
//				this.state = state;
//			}
//
//			public String getCountry() {
//				return country;
//			}
//
//			public void setCountry(String country) {
//				this.country = country;
//			}
//
//			public String getThemeName() {
//				return themeName;
//			}
//
//			public void setThemeName(String themeName) {
//				this.themeName = themeName;
//			}
//
//			public int getSize() {
//				return size;
//			}
//
//			public void setSize(int size) {
//				this.size = size;
//			}
//
//			public int getWeight() {
//				return weight;
//			}
//
//			public void setWeight(int weight) {
//				this.weight = weight;
//			}
//
//			public String getLink() {
//				return link;
//			}
//
//			public void setLink(String link) {
//				this.link = link;
//			}
//
//			public ImageType getImagetype() {
//				return imagetype;
//			}
//
//			public void setImagetype(ImageType imagetype) {
//				this.imagetype = imagetype;
//			}
//
//			public String getAltText() {
//				return altText;
//			}
//
//			public void setAltText(String altText) {
//				this.altText = altText;
//			}
//			public TourPackage getNewTourPackage() {
//				return newTourPackage;
//			}
//
//			public void setNewTourPackage(TourPackage newTourPackage) {
//				this.newTourPackage = newTourPackage;
//			}
//
//			public List<TourPackage> getTourPackages() {
//				return tourPackages;
//			}
//
//			public void setTourPackages(List<TourPackage> tourPackages) {
//				this.tourPackages = tourPackages;
//			}		
//		
//}
