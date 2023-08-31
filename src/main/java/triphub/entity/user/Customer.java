package triphub.entity.user;

import javax.persistence.*;

import triphub.entity.util.Picture;
import triphub.viewModel.UserViewModel;
/**
 * Represents a customer entity in the system.
 * This entity contains details specific to a customer, along with references to related entities like User and Picture.
 */

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Picture picture;
	
	/**
	 * Updates the properties of the Customer based on the provided UserViewModel.
	 * The method updates the user, address, finance information, and picture associated with the customer.
	 * 
	 * @param form The UserViewModel containing updated data for the customer.
	 */
	public void updateCustomerFromViewModel(UserViewModel form) {
	    this.setId(form.getCustomerId());
	    this.getUser().updateUserFromViewModel(form);
	    this.getUser().getAddress().updateAddressFromViewModel(form);
	    this.getUser().getFinance().updateFinanceInfoFromViewModel(form);
	    this.getPicture().updatePictureFromViewModel(form);
	}
	
	/**
	 * Converts the properties of the Customer entity into a UserViewModel.
	 * This method extracts details from associated entities like User, Address, Finance Info, and Picture.
	 * 
	 * @return userViewModel The UserViewModel containing the current data of the customer.
	 */
	public UserViewModel initCustomerViewModel() {
	    UserViewModel userViewModel = new UserViewModel();
	    this.getUser().initUserViewModel(userViewModel);
	    this.getUser().getAddress().initAddressViewModel(userViewModel);
	    this.getUser().getFinance().initFinanceInfoViewModel(userViewModel);
	    Picture picture = this.getPicture();
	    if (picture != null) {
	        picture.initPictureViewModel(userViewModel);
	    }  
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
