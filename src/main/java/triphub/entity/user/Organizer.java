package triphub.entity.user;

import javax.persistence.*;

import triphub.entity.subscription.Subscription;
import triphub.entity.util.Administration;
import triphub.entity.util.CompanyInfo;

@Entity
public class Organizer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	private CompanyInfo companyInfo;

	@OneToOne(cascade = CascadeType.ALL)
	private Administration administration;

//    @OneToOne(cascade = CascadeType.ALL)
//    private Destination destination;

	@OneToOne(cascade = CascadeType.ALL)
	private Subscription subscription;
}
