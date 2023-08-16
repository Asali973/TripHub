package triphub.entity.product;

import javax.persistence.*;

import triphub.entity.product.service.Service;
import triphub.entity.util.Picture;

import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
   
    @OneToOne
    private Price price;

    @OneToOne
    @JoinColumn(name = "tour_package_id")
    private TourPackage tourPackage;

    @OneToOne
    @JoinColumn(name = "service_id")
    private Service service;     
   
       
}
