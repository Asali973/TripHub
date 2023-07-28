package triphub.entity.util;

import javax.persistence.*;

import triphub.entity.user.User;

@Entity
public class FinanceInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User accountOwner;

    private String CCNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private Date expirationDate;
}
