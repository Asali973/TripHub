package triphub.entity.subscription;

import javax.persistence.*;
import java.util.List;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    private Price price;
//
//    private int duration;
//
//    @Enumerated(EnumType.STRING)
//    private SubscriptionType type;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    private Customization customization;
}
