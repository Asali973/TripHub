package triphub.entity.util;
import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String num;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}