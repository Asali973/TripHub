package triphub.entity.product;

import javax.persistence.*;


import triphub.entity.util.Picture;

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
//    @ManyToOne(cascade = CascadeType.ALL)
//    private TourPackage tourPackage;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    private Service service;   
//    
//    private String Description;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    private Picture picture;
//    
    
}
