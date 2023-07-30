package triphub.entity.product;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @OneToOne(cascade = CascadeType.ALL)
//    private Price totalPrice;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    private Price price;
//
//    @ManyToOne
//    private TourPackage tourPackage;
//
//    @ManyToOne
//    private Service service;
//
//    @ManyToOne
//    private Destination destination;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    private Picture pictures;
}
