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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public Administration getAdministration() {
		return administration;
	}

	public void setAdministration(Administration administration) {
		this.administration = administration;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	
	
}
