package triphub.entity.util;

import javax.persistence.*;

@Entity
public class Administration {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String siret;
    private String phone;
    private String sector;
    private String email;
}
