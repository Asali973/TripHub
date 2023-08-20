//package triphub.entity.product;
//
//import javax.persistence.*;
//
//import triphub.entity.product.service.Service;
//import triphub.entity.util.Picture;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//
//public class Product {
//    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    private Long id;  
//  
//    @OneToOne(mappedBy = "product", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    private TourPackage tourPackage;
//    
//    @OneToOne(mappedBy = "product", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    private Service service;
//    
//    @OneToMany(mappedBy = "product")
//    private List<CartItem> cartItems = new ArrayList<>();
//    
//    
////    public String getName() {
////        if (tourPackage != null) {
////            return tourPackage.getName();
////        } else if (service != null) {
////            // Return the name based on the specific service type.
////        } else {
////            return "Unnamed Product"; // Fallback if no associated entity.
////        }
////    }
//    
////    public String getDescription() {
////        if (tourPackage != null) {
////            return tourPackage.getDescription();
////        } else if (service != null) {
////            // Return the description based on the specific service type.
////        } else {
////            return "No description available"; // Fallback if no associated entity.
////        }
////    }
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//	
//
//	public TourPackage getTourPackage() {
//		return tourPackage;
//	}
//
//	public void setTourPackage(TourPackage tourPackage) {
//		this.tourPackage = tourPackage;
//	}
//
//	public Service getService() {
//		return service;
//	}
//
//	public void setService(Service service) {
//		this.service = service;
//	}     
//   
//       
//}
