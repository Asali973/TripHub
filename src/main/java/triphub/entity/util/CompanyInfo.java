package triphub.entity.util;

import javax.persistence.*;

@Entity
public class CompanyInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

//    @OneToOne(cascade = CascadeType.ALL)
//    private Picture logo;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    private Picture picture;
}
