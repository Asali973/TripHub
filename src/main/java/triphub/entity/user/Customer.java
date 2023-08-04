package triphub.entity.user;

import javax.persistence.*;

import triphub.entity.util.Picture;
import triphub.viewModel.UserViewModel;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Picture picture;
	
	public void updateCustomerFromViewModel(UserViewModel form) {
	    this.setId(form.getCustomerId());
	    this.getUser().updateUserFromViewModel(form);
	    this.getUser().getAddress().updateAddressFromViewModel(form);
	    this.getUser().getFinance().updateFinanceInfoFromViewModel(form);
	    this.getPicture().updatePictureFromViewModel(form);
	}

	public UserViewModel initCustomerViewModel() {
	    UserViewModel userViewModel = new UserViewModel();
	    this.getUser().initUserViewModel(userViewModel);
	    this.getUser().getAddress().initAddressViewModel(userViewModel);
	    this.getUser().getFinance().initFinanceInfoViewModel(userViewModel);
	    this.getPicture().initPictureViewModel(userViewModel);
	    userViewModel.setCustomerId(this.getId());

	    return userViewModel;
	}



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

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

}
