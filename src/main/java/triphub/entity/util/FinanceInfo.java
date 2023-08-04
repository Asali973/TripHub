package triphub.entity.util;

import javax.persistence.*;
import java.util.Date;

import triphub.entity.user.User;
import triphub.viewModel.UserViewModel;

@Entity
public class FinanceInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private User accountOwner;

	private String CCNumber;

	private Date expirationDate;
	
	public static FinanceInfo createFinanceInfoFromViewModel(UserViewModel form) {
	    FinanceInfo finance = new FinanceInfo();
	    finance.setCCNumber(form.getCCNumber());
	    finance.setExpirationDate(form.getExpirationDate());
	    return finance;
	}
	
	public void updateFinanceInfoFromViewModel(UserViewModel form) {
	    this.setCCNumber(form.getCCNumber());
	    this.setExpirationDate(form.getExpirationDate());
	}

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
