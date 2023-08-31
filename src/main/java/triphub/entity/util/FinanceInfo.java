package triphub.entity.util;

import javax.persistence.*;
import java.util.Date;

import triphub.entity.user.User;
import triphub.viewModel.UserViewModel;
/**
 * Represents financial information associated with a user in the system.
 * Contains sensitive data like credit card number and its expiration date.
 */
@Entity
public class FinanceInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private User accountOwner;

	private String CCNumber;

	private Date expirationDate;
	
	 /**
     * Creates an instance of FinanceInfo from the provided UserViewModel.
     * This method helps in translating the ViewModel representation to the entity form.
     * 
     * @param form The UserViewModel that holds the data.
     * @return An initialized FinanceInfo instance.
     */
	public static FinanceInfo createFinanceInfoFromViewModel(UserViewModel form) {
	    FinanceInfo finance = new FinanceInfo();
	    finance.setCCNumber(form.getCCNumber());
	    finance.setExpirationDate(form.getExpirationDate());
	    return finance;
	}
	
	  /**
     * Updates the current instance using data from the provided UserViewModel.
     * 
     * @param form The UserViewModel that holds the data to update this instance.
     */	
	public void updateFinanceInfoFromViewModel(UserViewModel form) {
	    this.setCCNumber(form.getCCNumber());
	    this.setExpirationDate(form.getExpirationDate());
	}
	
	 /**
     * Initializes a UserViewModel instance with data from this FinanceInfo instance.
     * Useful for translating the entity representation back to a ViewModel form.
     * 
     * @param userViewModel The UserViewModel instance to be initialized.
     */
	public void initFinanceInfoViewModel(UserViewModel userViewModel) {
	    userViewModel.setCCNumber(this.getCCNumber());
	    userViewModel.setExpirationDate(this.getExpirationDate());
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(User accountOwner) {
		this.accountOwner = accountOwner;
	}

	public String getCCNumber() {
		return CCNumber;
	}

	public void setCCNumber(String cCNumber) {
		CCNumber = cCNumber;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}
