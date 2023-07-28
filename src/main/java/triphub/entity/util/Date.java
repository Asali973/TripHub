package triphub.entity.util;

import javax.persistence.*;

@Entity
public class Date {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private java.util.Date date;

    private java.util.Date time;
}
