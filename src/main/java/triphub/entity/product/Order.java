//package triphub.entity.product;
//
//import java.util.List;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//
//import triphub.entity.user.User;
//
//@Entity
//public class Order {
//	  @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    private Long id;
//
//	    @ManyToOne
//	    private User user;
//
//	    @OneToMany(mappedBy = "order")
//	    private List<CartItem> cartItems;
//}
