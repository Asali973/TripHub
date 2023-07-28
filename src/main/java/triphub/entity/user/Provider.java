package triphub.entity.user;

import javax.persistence.*;

import triphub.entity.product.Product;
import triphub.entity.util.Administration;

import triphub.entity.util.CompanyInfo;

@Entity
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private CompanyInfo companyInfo;

    @OneToOne(cascade = CascadeType.ALL)
    private Product product;

    @OneToOne(cascade = CascadeType.ALL)
    private Administration administration;
}
